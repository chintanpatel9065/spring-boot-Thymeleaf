package org.chintanpatel.springbootthymeleaf.task;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.chintanpatel.springbootthymeleaf.project.Project;
import org.chintanpatel.springbootthymeleaf.status.Status;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @NotEmpty(message = "Please Provide Task Name")
    @Column(name = "task_name", nullable = false)
    private String taskName;

    @NotEmpty(message = "Please Provide Task Description")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Please Provide Start Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "Please Provide End Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @NotNull(message = "Please Select Status")
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne
    @NotNull(message = "Please Select Project")
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Task() {
    }

    public Task(Long taskId, String taskName, String description, LocalDate startDate, LocalDate endDate, Status status, Project project) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.project = project;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
