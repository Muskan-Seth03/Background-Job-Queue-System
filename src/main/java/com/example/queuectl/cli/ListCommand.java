package com.example.queuectl.cli;

import com.example.queuectl.entity.Job;

import com.example.queuectl.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;

@Command(
        name = "list",
        description = "List jobs by state"
)
public class ListCommand implements Runnable {

    @Option(
            names = "--state",
            required = true,
            description = "Job state (PENDING, PROCESSING, COMPLETED, DEAD)"
    )
    private String state;

    @Autowired
    private JobService jobService;

    @Override
    public void run() {

        List<Job> jobs = jobService.listJobsByState(state);

        if (jobs.isEmpty()) {
            System.out.println("No jobs found.");
            return;
        }

        System.out.println("\n========== JOBS ==========");

        for (Job job : jobs) {

            System.out.println("---------------------------");

            System.out.println("Id          : " + job.getId());

            System.out.println("Command     : " + job.getCommand());

            System.out.println("State       : " + job.getState());

            System.out.println("Attempts    : " + job.getAttempts());

            System.out.println("Created At  : " + job.getCreatedAt());

        }

    }
}
