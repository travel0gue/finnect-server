package com.example.finnect.repository;

import com.example.finnect.entity.Project;
import com.example.finnect.entity.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUserId(Long userId);
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByUserIdAndStatus(Long userId, ProjectStatus status);
    Optional<Project> findFirstByUserIdAndStatusOrderByUpdatedAtDesc(Long userId, ProjectStatus status);
}