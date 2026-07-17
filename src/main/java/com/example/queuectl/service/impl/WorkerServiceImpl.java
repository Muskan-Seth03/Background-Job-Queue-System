package com.example.queuectl.service.impl;

import com.example.queuectl.entity.Job;
import com.example.queuectl.entity.WorkerInfo;
import com.example.queuectl.repository.JobRepository;
import com.example.queuectl.service.JobService;
import com.example.queuectl.service.RetryService;
import com.example.queuectl.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class WorkerServiceImpl implements WorkerService {

//    @Autowired
//    private JobRepository jobRepository;

    @Autowired
    private JobService jobService;

    @Autowired
    private RetryService retryService;

    private final List<WorkerInfo> activeWorkers = new CopyOnWriteArrayList<>();

    private ExecutorService executorService;

    private volatile boolean running = false;

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void startWorkers(int count) {
        if (running) {
            return; // Already running
        }
        running = true;
        executorService = Executors.newFixedThreadPool(count);

        for (int i = 0; i < count; i++) {
            WorkerInfo worker = new WorkerInfo();
            worker.setId("worker-" + (activeWorkers.size() + 1));
            worker.setStatus("ACTIVE");
            activeWorkers.add(worker);

            executorService.submit(() -> {
            	System.out.println(worker.getId() + " started.");
                while (running) {
                    Job job = null;
                    try {
                        if (lock.tryLock()) {
                            try {
                                job = jobService.claimNextJob();
                            } finally {
                                lock.unlock();
                            }
                        }
                        if (job != null) {
                            processJob(job, worker);
                        } else {
                            try {
                                Thread.sleep(1000); // wait before retrying
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    } catch (Exception e) {
                        // Log or handle exception
						e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void stopWorkers() {
        running = false;
        if (executorService != null) {
            executorService.shutdown();
          //  System.out.println(worker.getId() + " stopped.");
            try {
                if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        
        for (WorkerInfo worker : activeWorkers) {
            worker.setStatus("STOPPED");
            System.out.println(worker.getId() + " stopped.");
        }
        
        activeWorkers.clear();
        System.out.println("All workers stopped successfully.");
    }
   
    
    @Override
    public void processJob(Job job, WorkerInfo worker) {
        try {
            ProcessBuilder builder;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                builder = new ProcessBuilder("cmd", "/c", job.getCommand());
            } else {
                builder = new ProcessBuilder("sh", "-c", job.getCommand());
            }
            
            System.out.println("================================");
            System.out.println(worker.getId() + "picked job " + job.getId());
            System.out.println("Command: " + job.getCommand());
            
            Process process = builder.start();
            
         // Read standard output
            BufferedReader output = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = output.readLine()) != null) {
                System.out.println(line);
            }
            
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                jobService.markCompleted(job.getId());
                System.out.println("Exit Code = " + exitCode);
            } else {
         	    System.out.println("Exit Code = " + exitCode);

         	    BufferedReader error = new BufferedReader(
         	            new InputStreamReader(process.getErrorStream()));

         	    error.lines().forEach(System.out::println);

                retryService.handleFailure(job);
            }
        } catch (Exception e) {
            retryService.handleFailure(job);
        }
    }

    @Override
    public List<WorkerInfo> getActiveWorkers() {
        return activeWorkers;
    }

    @Override
    public WorkerInfo getWorker(String workerId) {
        return activeWorkers.stream()
                .filter(worker -> worker.getId().equals(workerId))
                .findFirst()
                .orElse(null);
    }
}