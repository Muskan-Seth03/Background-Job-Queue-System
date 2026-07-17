package com.example.queuectl.repository;

import com.example.queuectl.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    // Save is inherited from JpaRepository

    Optional<Job> findById(Long id);

    @Query("SELECT j FROM Job j WHERE j.state = 'DEAD'")
    List<Job> findDeadJobs();

    @Query("SELECT j FROM Job j WHERE j.state = 'PENDING'")
    List<Job> findPendingJobs();

    @Modifying
    @Transactional
    @Query("UPDATE Job j SET j.state = :state WHERE j.id = :id")
    int updateState(@Param("id") Long id, @Param("state") String state);

    long countByState(String state);
}