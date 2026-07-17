package com.example.queuectl.service;

import java.util.List;

import com.example.queuectl.entity.Job;

public interface DLQService {

    void moveToDLQ(Job job);

    List<Job> getDeadJobs();

    void retryDeadJob(String jobId);
    
    void retryAllDeadJobs();


}