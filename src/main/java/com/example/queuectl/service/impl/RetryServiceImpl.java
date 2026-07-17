package com.example.queuectl.service.impl;

import com.example.queuectl.entity.Job;
import com.example.queuectl.repository.JobRepository;
import com.example.queuectl.service.DLQService;
import com.example.queuectl.service.RetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetryServiceImpl implements RetryService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private DLQService dlqService;
    
    @Override
    public void handleFailure(Job job) {
        incrementAttempts(job);
        if (!canRetry(job)) {
            dlqService.moveToDLQ(job);
        } else {
            scheduleRetry(job);
        }
    }

    @Override
    public void scheduleRetry(Job job) {
        job.setState("PENDING");
        long delay = calculateBackoffDelay(job);
        job.setNextRetryTime(java.time.LocalDateTime.now().plusSeconds(delay));
        jobRepository.save(job);
    }

    @Override
    public long calculateBackoffDelay(Job job) {
        // Exponential backoff: base delay 2^attempts * 1000 milliseconds
        
    	return (long) Math.pow(job.getBackoffBase(), job.getAttempts()) * 1000;
    }

    @Override
    public boolean canRetry(Job job) {
        return job.getAttempts() < job.getMaxRetries();
    }

    @Override
    public void incrementAttempts(Job job) {
        job.setAttempts(job.getAttempts() + 1);
    }
}
