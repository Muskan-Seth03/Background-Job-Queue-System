package com.example.queuectl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerInfo {
    private String id;
    
    private String status;
    
    private String currentJobId;
}