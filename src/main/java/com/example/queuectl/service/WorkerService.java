package com.example.queuectl.service;

import java.util.List;

import com.example.queuectl.entity.Job;
import com.example.queuectl.entity.WorkerInfo;

public interface WorkerService {

    void startWorkers(int count);

    void stopWorkers();

   // void stopWorker(String workerId);

    void processJob(Job job, WorkerInfo worker);

    //void executeWorker();

    List<WorkerInfo> getActiveWorkers();

    WorkerInfo getWorker(String workerId);

}