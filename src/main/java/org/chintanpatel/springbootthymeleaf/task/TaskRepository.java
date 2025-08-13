package org.chintanpatel.springbootthymeleaf.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByTaskNameContainingIgnoreCase(String taskName);

    @Query("select t from Task t where lower(t.status.statusType) like lower(concat('%',:statusType,'%'))")
    List<Task>findByStatusTypeContainingIgnoreCase(String statusType);

    @Query("select t from Task t where lower(t.project.projectName) like lower(concat('%',:projectName,'%'))")
    List<Task>findByProjectNameContainingIgnoreCase(String projectName);
}