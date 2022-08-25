package com.example.Scheduler.services;

import com.example.Scheduler.domains.Task;
import com.example.Scheduler.domains.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {

    List<Task> getAllTaskByUser(User user);

    Task getTaskById(Long id);

    void save(Task task);

    Task update(Task task);

    void deleteById(Long id);

    List<Task> getAll();

    List<Task> getTasksByDate(String date, User user);

    List<Task> getTodayTasks(User user);
}
