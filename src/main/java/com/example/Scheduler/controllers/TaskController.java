package com.example.Scheduler.controllers;

import com.example.Scheduler.domains.Task;
import com.example.Scheduler.domains.User;
import com.example.Scheduler.services.TaskService;
import com.example.Scheduler.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks/")
@AllArgsConstructor
@Api("Контроллер для работы с задачами")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @ApiOperation("Получение списка всех задач")
    public ResponseEntity<List<Task>> getAll(){
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping("/getAllByUser")
    @ApiOperation("Получение списка всех задач определенного пользователя")
    public ResponseEntity<List<Task>> getAllTaskByUser(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(taskService.getAllTaskByUser(user));
    }

    @GetMapping("/today")
    @ApiOperation("Получение списка задач на сегодняшний день для определенного пользователя")
    public ResponseEntity<List<Task>> getTodayTasks(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(taskService.getTodayTasks(user));
    }

    @GetMapping("/date/{date}")
    @ApiOperation("Получение списка задач на конкретный день для определенного пользователя")
    public ResponseEntity<List<Task>> getTasksByDate(@PathVariable String date, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(taskService.getTasksByDate(date, user));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получение задачи по идентификатору")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    @ApiOperation("Создание новой задачи")
    public ResponseEntity createNewTask(@RequestBody Task task, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        task.setUser(user);
        taskService.save(task);
        return ResponseEntity.ok("New task was created");
    }

    @PutMapping
    @ApiOperation("Изменение определенный задачи")
    public ResponseEntity<Task> updateTask(@RequestBody Task task){
        return ResponseEntity.ok(taskService.update(task));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление задачи по идентификатору")
    public ResponseEntity deleteTaskById(@PathVariable Long id){
        taskService.deleteById(id);
        return ResponseEntity.ok("Task with id=" + id + " was deleted");
    }

}
