package com.example.queuectl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.queuectl.entity.AppConfig;
import com.example.queuectl.repository.AppConfigRepository;
import com.example.queuectl.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private AppConfigRepository repository;
    
    @Override
    public AppConfig getConfig() {
        try {
            return repository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Configuration not found"));
        } catch (Exception e) {
            // Log the exception or handle it as needed
            throw new RuntimeException("Failed to get configuration", e);
        }
    }

    @Override
    public void updateMaxRetries(int retries) {
        try {
            AppConfig config = getConfig();
            config.setMaxRetries(retries);
            repository.save(config);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            throw new RuntimeException("Failed to update maxRetries", e);
        }
    }

    @Override
    public void updateBackoffBase(int base) {
        try {
            AppConfig config = getConfig();
            config.setBackoffBase(base);
            repository.save(config);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            throw new RuntimeException("Failed to update backoffBase", e);
        }
    }

}