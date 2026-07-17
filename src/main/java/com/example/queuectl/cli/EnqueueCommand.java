package com.example.queuectl.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.queuectl.service.JobService;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Command(name = "enqueue")
public class EnqueueCommand implements Runnable {

    @Parameters(index = "0")
    private String jobJson;

    @Autowired
    private JobService jobService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void run() {
        try {
            JsonNode node = mapper.readTree(jobJson);

            String command = node.get("command").asText();

            System.out.println("Command: " + command);

            jobService.enqueueJob(command);

        } catch (Exception e) {
            System.out.println("Invalid JSON: " + e.getMessage());
        }
    }
}