package com.example.Scheduler.services;

import com.example.Scheduler.dtos.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    void save(UserDTO userDTO);

    void update(UserDTO userDTO);

    void deleteUserById(Long id);
}
