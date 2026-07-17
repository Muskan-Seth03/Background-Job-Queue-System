package com.example.queuectl.service;

import com.example.queuectl.entity.AppConfig;

public interface ConfigService {

    AppConfig getConfig();

    void updateMaxRetries(int retries);

    void updateBackoffBase(int base);

}