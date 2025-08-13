package org.chintanpatel.springbootthymeleaf.task;

import java.util.List;

public interface TaskService {

    void addTask(Task task);

    List<Task>getAllTaskList();

    Task getTaskById(Long taskId);

    void deleteTaskById(Long taskId);

    List<Task>searchByTaskName(String taskName);

    List<Task>searchByStatusType(String statusType);

    List<Task>searchByProjectName(String projectName);
}
