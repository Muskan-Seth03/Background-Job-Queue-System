package com.example.queuectl.service.impl;

import com.example.queuectl.dto.QueueStatus;
import com.example.queuectl.entity.AppConfig;
import com.example.queuectl.entity.Job;
import com.example.queuectl.repository.JobRepository;
import com.example.queuectl.service.ConfigService;
import com.example.queuectl.service.JobService;
import com.example.queuectl.service.WorkerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

//    @Autowired
//    private WorkerService workerService;
    
    @Autowired
    private ConfigService configService;
    
    @Override
    public Job enqueueJob(String command) {
    	AppConfig config = configService.getConfig();
        Job job = new Job();
        job.setCommand(command);
        job.setState("PENDING");
        job.setAttempts(0);
        job.setMaxRetries(config.getMaxRetries()); // default max retries
        job.setBackoffBase(config.getBackoffBase()); // default backoff base
        job.setCreatedAt(java.time.LocalDateTime.now());
        job.setUpdatedAt(java.time.LocalDateTime.now());
        return jobRepository.save(job);
    }

    @Override
    @Transactional
    public Job claimNextJob() {
        // Simplified example: find first pending job
        List<Job> pendingJobs = jobRepository.findPendingJobs();
        if (pendingJobs.isEmpty()) {
            return null;
        }
        Job job = pendingJobs.get(0);
        job.setState("PROCESSING");
        job.setUpdatedAt(java.time.LocalDateTime.now());
        return jobRepository.save(job);
    }


    @Override
    public Job getJobById(String jobId) {
        Long id = Long.valueOf(jobId);
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public List<Job> listJobsByState(String state) {
        return jobRepository.findAll().stream()
                .filter(job -> job.getState().equals(state))
                .toList();
    }

    @Override
    public void updateJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public void markCompleted(Long jobId) {
        Long id = Long.valueOf(jobId);
        jobRepository.updateState(id, "COMPLETED");
    }

	@Override
	public List<Job> getAllJobs() {
		return jobRepository.findAll();
	}
	
	public QueueStatus getStatus() {

	    QueueStatus status = new QueueStatus();

	    status.setPendingJobs(jobRepository.countByState("PENDING"));

	    status.setProcessingJobs(jobRepository.countByState("PROCESSING"));

	    status.setCompletedJobs(jobRepository.countByState("COMPLETED"));

	    status.setDeadJobs(jobRepository.countByState("DEAD"));

	    //status.setActiveWorkers(workerService.getActiveWorkers());

	    return status;
	}

	@Override
	public long countByState(String state) {
		// TODO Auto-generated method stub
		return 0;
	}
}
