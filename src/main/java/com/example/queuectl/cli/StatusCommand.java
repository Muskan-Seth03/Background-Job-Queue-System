package com.example.queuectl.cli;

import org.springframework.beans.factory.annotation.Autowired;


import com.example.queuectl.dto.QueueStatus;
import com.example.queuectl.service.JobService;
import com.example.queuectl.service.WorkerService;
import com.example.queuectl.entity.WorkerInfo;
import picocli.CommandLine.Command;

@Command(
        name = "status",
        description = "Show queue status"
)
public class StatusCommand implements Runnable {

    @Autowired
    private JobService jobService;
   
    @Autowired
    private WorkerService workerService;

    @Override
    public void run() {

        QueueStatus status = jobService.getStatus();

        System.out.println("\n========= Queue Status =========");

        System.out.println("Pending     : "
                + status.getPendingJobs());

        System.out.println("Processing  : "
                + status.getProcessingJobs());

        System.out.println("Completed   : "
                + status.getCompletedJobs());

        System.out.println("Dead        : "
                + status.getDeadJobs());

        System.out.println();

        System.out.println("\nActive Workers");
        System.out.println("---------------------------------------");

        status.setActiveWorkers(workerService.getActiveWorkers());
        
        if (status.getActiveWorkers().isEmpty()) {
            System.out.println("No active workers.");
        } else {
            System.out.printf("%-12s %-10s%n", "Worker ID", "Status");

            for (WorkerInfo worker : status.getActiveWorkers()) {
                System.out.printf("%-12s %-10s%n",
                        worker.getId(),
                        worker.getStatus());
        }
    }
}}