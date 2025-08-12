package org.chintanpatel.springbootthymeleaf.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("projectRepository")
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByProjectNameContainingIgnoreCase(String projectName);

    @Query("select pr from Project pr where lower(pr.priority.priorityType) like lower(concat('%',:priorityType,'%'))")
    List<Project>findByPriorityTypeContainingIgnoreCase(String priorityType);
}