package com.example.queuectl.cli;

import picocli.CommandLine.Command;

import org.springframework.stereotype.Component;


@Component
@Command(
        name = "help",
        description = "Display QueueCTL commands",
        mixinStandardHelpOptions = true)
public class HelpCommand implements Runnable {

    @Override
    public void run() {

        System.out.println();
        System.out.println("QueueCTL - Background Job Queue");
        System.out.println("==============================================================");
        System.out.printf("%-15s %-40s %-35s%n",
                "Category", "Command", "Description");
        System.out.println("-----------------------------------------------------------------------------------------------");

        System.out.printf("%-15s %-40s %-35s%n",
                "Enqueue",
                "queuectl enqueue '{\"command\":\"sleep 2\"}'",
                "Add a new job to the queue");

        System.out.printf("%-15s %-40s %-35s%n",
                "Workers",
                "queuectl worker start --count 3",
                "Start one or more workers");

        System.out.printf("%-15s %-40s %-35s%n",
                "",
                "queuectl worker stop",
                "Stop running workers gracefully");

        System.out.printf("%-15s %-40s %-35s%n",
                "Status",
                "queuectl status",
                "Show summary of jobs & workers");

        System.out.printf("%-15s %-40s %-35s%n",
                "List Jobs",
                "queuectl list --state pending",
                "List jobs by state");

        System.out.printf("%-15s %-40s %-35s%n",
                "DLQ",
                "queuectl dlq list",
                "View dead-letter jobs");

        System.out.printf("%-15s %-40s %-35s%n",
                "",
                "queuectl dlq retry job1",
                "Retry a dead-letter job");

        System.out.printf("%-15s %-40s %-35s%n",
                "Config",
                "queuectl config set max-retries 3",
                "Update queue configuration");

        System.out.println("-----------------------------------------------------------------------------------------------");
    }
}