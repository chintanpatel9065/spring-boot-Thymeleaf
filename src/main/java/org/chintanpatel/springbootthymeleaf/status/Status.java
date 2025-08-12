package org.chintanpatel.springbootthymeleaf.status;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.chintanpatel.springbootthymeleaf.task.Task;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id", nullable = false)
    private Long statusId;

    @NotEmpty(message = "Please Provide Status")
    @Column(name = "status_type", nullable = false)
    private String statusType;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    public Status() {
    }
    public Status(Long statusId, String statusType, Set<Task> tasks) {
        this.statusId = statusId;
        this.statusType = statusType;
        this.tasks = tasks;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
