package com.example.userservice.services;

import com.example.userservice.dto.CompanyDTO;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entities.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User update(Long id, User user);
    List<UserResponse> getAll();
    User getById(Long id);
    void delete(Long id);
    CompanyDTO getCompanyForUser(Long userId);
    UserResponse getUserWithCompany(Long id);
    List<UserResponse> getUsersByCompanyId(Long companyId);
}
