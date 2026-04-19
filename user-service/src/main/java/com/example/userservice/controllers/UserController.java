package com.example.userservice.controllers;

import com.example.userservice.dto.CompanyDTO;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entities.User;
import com.example.userservice.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;


    @PostMapping
    public User create(@RequestBody User user) {
        log.info("Post /users - creating user: {}", user);

        User createdUser = userService.create(user);

        log.info("Post /users - user created with id: {}", createdUser.getId());
        return createdUser;
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        log.info("PUT /users/{} - updating user: {}", id, user);

        User updateUser = userService.update(id, user);

        log.info("PUT /users/{} - user updated successfully", id);
        return updateUser;
    }

    @GetMapping
    public List<UserResponse> getAll() {
        log.info("GET /users - fetching all users");

        List<UserResponse> users = userService.getAll();

        log.info("GET /users - fetched {} users", users.size());
        return users;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        log.info("GET /users/{} - request received", id);

        User user = userService.getById(id);

        log.info("GET /users/{} - user fetched successfully", id);
        return user;
    }

    @GetMapping("/{id}/company")
    public CompanyDTO getCompany(@PathVariable Long id) {
        log.info("GET /users/{}/company - fetching company for user", id);

        CompanyDTO company = userService.getCompanyForUser(id);

        log.info("GET /users/{}/company - company fetched successfully", id);
        return company;
    }

    @GetMapping("/{id}/full")
    public UserResponse getUserWithCompany(@PathVariable Long id) {
        log.info("GET /users/{}/full - fetching user with company", id);

        UserResponse response = userService.getUserWithCompany(id);

        log.info("GET /users/{}/full - user with company fetched", id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("DELETE /users/{} - deleting user", id);

        userService.delete(id);

        log.info("DELETE /users/{} - user deleted successfully", id);
    }


}
