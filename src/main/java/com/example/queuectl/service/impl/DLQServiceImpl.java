package com.example.queuectl.service.impl;

import com.example.queuectl.entity.Job;
import com.example.queuectl.repository.JobRepository;
import com.example.queuectl.service.DLQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DLQServiceImpl implements DLQService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public void moveToDLQ(Job job) {
        job.setState("DEAD");
        jobRepository.save(job);
    }

    @Override
    public List<Job> getDeadJobs() {
        return jobRepository.findDeadJobs();
    }

    @Override
    public void retryDeadJob(String jobId) {
        Long id = Long.valueOf(jobId);
        jobRepository.findById(id).ifPresent(job -> {
            if ("DEAD".equals(job.getState())) {
                job.setState("PENDING");
                job.setAttempts(0);
                jobRepository.save(job);
            }
        });
    }
    
    @Override
    public void retryAllDeadJobs() {
        List<Job> deadJobs = jobRepository.findDeadJobs();

        for (Job job : deadJobs) {
            retryDeadJob(job.getId().toString());
        }
    }
}
