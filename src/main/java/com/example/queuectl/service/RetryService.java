package com.example.queuectl.service;

import com.example.queuectl.entity.Job;

public interface RetryService {

    void handleFailure(Job job);

    void scheduleRetry(Job job);

    long calculateBackoffDelay(Job job);

    boolean canRetry(Job job);

    void incrementAttempts(Job job);
}