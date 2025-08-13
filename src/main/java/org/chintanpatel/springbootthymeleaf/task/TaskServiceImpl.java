package org.chintanpatel.springbootthymeleaf.task;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void addTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTaskList() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public void deleteTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<Task> searchByTaskName(String taskName) {
        return taskRepository.findByTaskNameContainingIgnoreCase(taskName);
    }

    @Override
    public List<Task> searchByStatusType(String statusType) {
        return taskRepository.findByStatusTypeContainingIgnoreCase(statusType);
    }

    @Override
    public List<Task> searchByProjectName(String projectName) {
        return taskRepository.findByProjectNameContainingIgnoreCase(projectName);
    }
}
