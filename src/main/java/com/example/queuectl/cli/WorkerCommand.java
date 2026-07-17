package com.example.queuectl.cli;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.queuectl.service.WorkerService;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name="worker", subcommands = {WorkerStartCommand.class, WorkerStopCommand.class}, description = "Manage worker processes")
class WorkerCommand {

}
@Command(name="start", description = "Start worker processes")
class WorkerStartCommand implements Runnable {

    @Option(names="--count")
    int count;

    @Autowired
    WorkerService workerService;

    @Override
    public void run(){

        workerService.startWorkers(count);

    }
}

@Command(name="stop", description = "Stop worker processes")
class WorkerStopCommand implements Runnable {

    @Autowired
    WorkerService workerService;

    @Override
    public void run(){

        workerService.stopWorkers();

    }

}

