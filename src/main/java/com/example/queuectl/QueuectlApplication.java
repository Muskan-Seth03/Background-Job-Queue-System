package com.example.queuectl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.queuectl.cli.QueueCtlCommand;

import picocli.CommandLine;
import picocli.spring.PicocliSpringFactory;

@SpringBootApplication
public class QueuectlApplication implements CommandLineRunner {

    @Autowired
    private QueueCtlCommand queueCtlCommand;

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(QueuectlApplication.class, args);
    }

    @Override
    public void run(String... args) {
        new CommandLine(queueCtlCommand,
                new PicocliSpringFactory(context))
                .execute(args);
    }

}