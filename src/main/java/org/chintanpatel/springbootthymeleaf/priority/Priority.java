package org.chintanpatel.springbootthymeleaf.priority;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.chintanpatel.springbootthymeleaf.project.Project;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "priority")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority_id", nullable = false)
    private Long priorityId;

    @NotEmpty(message = "Please Provide Priority")
    @Column(name = "priority_type", nullable = false)
    private String priorityType;

    @OneToMany(mappedBy = "priority", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Project>projects = new HashSet<>();

    public Priority() {
    }

    public Priority(Long priorityId, String priorityType, Set<Project> projects) {
        this.priorityId = priorityId;
        this.priorityType = priorityType;
        this.projects = projects;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public String getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(String priorityType) {
        this.priorityType = priorityType;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
