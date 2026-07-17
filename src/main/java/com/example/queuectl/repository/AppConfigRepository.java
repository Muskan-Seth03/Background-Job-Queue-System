package com.example.queuectl.repository;

import com.example.queuectl.entity.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppConfigRepository
        extends JpaRepository<AppConfig, Long> {
}