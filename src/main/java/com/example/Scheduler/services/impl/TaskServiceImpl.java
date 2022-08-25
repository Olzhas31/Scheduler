package com.example.Scheduler.services.impl;

import com.example.Scheduler.domains.Task;
import com.example.Scheduler.domains.User;
import com.example.Scheduler.exceptions.RestAPIException;
import com.example.Scheduler.repositories.TaskRepository;
import com.example.Scheduler.services.TaskService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final static String TASK_NOT_FOUND_MESSAGE = "Task with id=%s not found";

    private final TaskRepository taskRepository;

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getTasksByDate(String date, User user) {
        return taskRepository.getAllByUser(user)
                .stream()
                .filter(task -> task.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getTodayTasks(User user) {
        return taskRepository.getAllByUser(user)
                .stream()
                .filter(task -> task.getDate().equals(new SimpleDateFormat("dd-MM-yyyy").format(new Date())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getAllTaskByUser(User user) {
        return taskRepository.getAllByUser(user);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RestAPIException(String.format(TASK_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public void save(Task task) {
        if (task.getDate() == null) {
            task.setDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        }
        taskRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        Task item = taskRepository.findById(task.getId())
                .orElseThrow(() -> new RestAPIException(String.format(TASK_NOT_FOUND_MESSAGE, task.getId())));
        if (task.getContent() != null) {
            item.setContent(task.getContent());
        }
        if (task.getDate() != null) {
            item.setDate(task.getDate());
        }
        item.setDone(task.isDone());
        return taskRepository.saveAndFlush(item);
    }

    @Override
    public void deleteById(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RestAPIException(String.format(TASK_NOT_FOUND_MESSAGE, id));
        }
    }

}
