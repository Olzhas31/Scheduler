package com.example.Scheduler.dtos;

import com.example.Scheduler.domains.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String role;

    public UserDTO(){}

    public UserDTO(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role.name();
    }
}
