package com.example.queuectl.cli;

import org.springframework.stereotype.Component;

import picocli.CommandLine.Command;

@Component
@Command(
    name = "queuectl",
    mixinStandardHelpOptions = true,
    subcommands = {
        EnqueueCommand.class,
        ListCommand.class,
        ConfigCommand.class,
        DLQCommand.class,
        StatusCommand.class,
        WorkerCommand.class,
        HelpCommand.class
    }
)
public class QueueCtlCommand implements Runnable {

    @Override
    public void run() {
        System.out.println("Use a subcommand. Try 'queuectl --help'");
    }
}