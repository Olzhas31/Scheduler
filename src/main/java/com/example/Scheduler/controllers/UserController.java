package com.example.Scheduler.controllers;

import com.example.Scheduler.dtos.UserDTO;
import com.example.Scheduler.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
@AllArgsConstructor
@Api("Контроллер для работы с пользователями")
public class UserController {

    private final UserService userService;

    @GetMapping
    @ApiOperation("Получение списка всех пользователей")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @ApiOperation("Получение пользователя по идентификатору")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    @ApiOperation("Создание нового пользователя")
    public ResponseEntity createUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity.ok("user was created");
    }

    @PutMapping
    @ApiOperation("Обновление данных пользователя")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO){
        userService.update(userDTO);
        return ResponseEntity.ok("user with id=" + userDTO.getId() + " was updated");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление пользователя по идентификатору")
    public ResponseEntity deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("user with id=" + id + " was deleted");
    }

}
