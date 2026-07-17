package com.example.queuectl.service;

import com.example.queuectl.dto.QueueStatus;
import com.example.queuectl.entity.Job;

import java.util.List;

public interface JobService {

    Job enqueueJob(String command);

    Job claimNextJob();
    
    List<Job> getAllJobs();

    void markCompleted(Long jobId);

    Job getJobById(String jobId);

    List<Job> listJobsByState(String state); 
    
    long countByState(String state);
    
    void updateJob(Job job);
    
    QueueStatus getStatus();
}