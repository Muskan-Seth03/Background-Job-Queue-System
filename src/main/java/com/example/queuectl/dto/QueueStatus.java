package com.example.queuectl.dto;

import java.util.List;

import com.example.queuectl.entity.WorkerInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueStatus {

    private long pendingJobs;

    private long processingJobs;

    private long completedJobs;

    private long deadJobs;

    private List<WorkerInfo> activeWorkers;

}