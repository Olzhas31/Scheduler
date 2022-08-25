package com.example.Scheduler.services.impl;

import com.example.Scheduler.domains.Role;
import com.example.Scheduler.domains.User;
import com.example.Scheduler.dtos.UserDTO;
import com.example.Scheduler.exceptions.RestAPIException;
import com.example.Scheduler.repositories.UserRepository;
import com.example.Scheduler.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final static String USER_NOT_FOUND_MESSAGE = "User with id=%s not found";
    private final static String USERNAME_ALREADY_EXIST_MESSAGE = "Username=%s is already exists";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map((user) -> new UserDTO(user.getId(), user.getUsername(), user.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map((user) -> new UserDTO(user.getId(), user.getUsername(), user.getRole()))
                .orElseThrow(() -> new RestAPIException(String.format(USER_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public void save(UserDTO userDTO) {
        if (userRepository.existsUserByUsername(userDTO.getUsername())) {
            throw new RestAPIException(String.format(USERNAME_ALREADY_EXIST_MESSAGE, userDTO.getUsername()));
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.ROLE_USER);
//        if(userDTO.getRoles() != null){
//            user.setRoles(userDTO.getRoles().stream()
//                    .map(r -> roleService.getByName(r))
//                    .collect(Collectors.toSet()));
//        }
        userRepository.save(user);
    }

    @Override
    public void update(UserDTO userDTO) {
        User item = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RestAPIException(String.format(USER_NOT_FOUND_MESSAGE, userDTO.getId())));
        if (userDTO.getPassword() != null){
            item.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
//        if (userDTO.getRole() != null) {
//            item.setRoles(userDTO.getRoles().stream()
//                    .map(r -> roleService.getByName(r))
//                    .collect(Collectors.toSet()));
//        }
        userRepository.saveAndFlush(item);
    }

    @Override
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RestAPIException(String.format(USER_NOT_FOUND_MESSAGE, id));
        }
        userRepository.deleteById(id);
    }
}
