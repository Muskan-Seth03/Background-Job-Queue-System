package com.example.queuectl.cli;

import com.example.queuectl.entity.Job;
import com.example.queuectl.service.DLQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.List;

@Component
@Command(
        name = "dlq",
        description = "Dead Letter Queue Commands",
        mixinStandardHelpOptions = true,
        subcommands = {
                DLQCommand.ListCommand.class,
                DLQCommand.RetryCommand.class,
                DLQCommand.RetryAllCommand.class
        }
)
public class DLQCommand {

    /**
     * queuectl dlq list
     */
    @Component
    @Command(
            name = "list",
            description = "List all dead jobs"
    )
    static class ListCommand implements Runnable {

        @Autowired
        private DLQService dlqService;

        @Override
        public void run() {

            List<Job> jobs = dlqService.getDeadJobs();

            if (jobs.isEmpty()) {
                System.out.println("No jobs found in Dead Letter Queue.");
                return;
            }

            System.out.println("\n========== DEAD LETTER QUEUE ==========");

            for (Job job : jobs) {

                System.out.println("-----------------------------------");
                System.out.println("Job Id      : " + job.getId());
                System.out.println("Command     : " + job.getCommand());
                System.out.println("State       : " + job.getState());
                System.out.println("Attempts    : " + job.getAttempts());
                System.out.println("Created At  : " + job.getCreatedAt());
            }
        }
    }

    /**
     * queuectl dlq retry job1
     */
    
    @Component
    @Command(
            name = "retry",
            description = "Retry a dead job"
    )
    static class RetryCommand implements Runnable {

        @Parameters(index = "0",
                description = "Job Id")
        private String jobId;

        @Autowired
        private DLQService dlqService;

        @Override
        public void run() {

            dlqService.retryDeadJob(jobId);

            System.out.println("Job " + jobId + " moved back to PENDING.");
        }
    }
    
    @Command(name = "retry-all",
            description = "Retry all jobs in the Dead Letter Queue")
   static class RetryAllCommand implements Runnable {

       @Autowired
       private DLQService dlqService;

       @Override
       public void run() {
           dlqService.retryAllDeadJobs();
           System.out.println("All dead jobs have been requeued.");
       }
   }
}