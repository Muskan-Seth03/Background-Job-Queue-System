package com.example.queuectl.config;

import com.example.queuectl.entity.AppConfig;
import com.example.queuectl.repository.AppConfigRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfig {

    @Bean
    CommandLineRunner init(AppConfigRepository repository) {

        return args -> {

            if(repository.count()==0){

                AppConfig config =
                        new AppConfig(1L,3,2);

                repository.save(config);
            }

        };
    }
}