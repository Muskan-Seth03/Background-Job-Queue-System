package com.example.queuectl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppConfig {

    @Id
    private Long id;

    private Integer maxRetries;

    private Integer backoffBase;

   
}