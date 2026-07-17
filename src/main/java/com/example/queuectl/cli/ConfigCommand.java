package com.example.queuectl.cli;

import com.example.queuectl.entity.AppConfig;
import com.example.queuectl.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Component
@Command(
        name = "config",
        description = "Manage Queue Configuration",
        mixinStandardHelpOptions = true,
        subcommands = {
                ConfigCommand.SetCommand.class
        }
)
public class ConfigCommand {

    /**
     * queuectl config set max-retries 3
     * queuectl config set backoff-base 2
     */
    @Component
    @Command(
            name = "set",
            description = "Update queue configuration"
    )
    public static class SetCommand implements Runnable {

        @Autowired
        private ConfigService configService;

        @Parameters(index = "0",
                description = "Configuration key (max-retries/backoff-base)")
        private String key;

        @Parameters(index = "1",
                description = "Configuration value")
        private int value;

        @Override
        public void run() {

            switch (key.toLowerCase()) {

                case "max-retries":
                    configService.updateMaxRetries(value);
                    System.out.println("Max retries updated to " + value);
                    break;

                case "backoff-base":
                    configService.updateBackoffBase(value);
                    System.out.println("Backoff base updated to " + value);
                    break;

                default:
                    System.out.println("Invalid configuration.");
                    System.out.println("Supported Keys:");
                    System.out.println("  max-retries");
                    System.out.println("  backoff-base");
            }

            AppConfig config = configService.getConfig();

            System.out.println("\nCurrent Configuration");
            System.out.println("----------------------");
            System.out.println("Max Retries : " + config.getMaxRetries());
            System.out.println("Backoff Base: " + config.getBackoffBase());
        }
    }
}