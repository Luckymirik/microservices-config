package com.example.userservice.services;

import com.example.userservice.clients.CompanyClient;
import com.example.userservice.dto.CompanyDTO;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entities.User;
import com.example.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.shared.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final CompanyClient companyClient;

    @Override
    public User create(User user) {
        log.info("Service: creating user with phone: {}", user.getPhone());

        User saved = repo.save(user);

        log.info("Service: user created with id: {}", saved.getId());
        return saved;
    }

    @Override
    public List<UserResponse> getAll() {
        log.info("Service: fetching all users");

        List<User> users = repo.findAll();

        log.info("Service: {} users found in DB", users.size());

        List<UserResponse> result = users.stream()
                .map(user -> getUserWithCompany(user.getId()))
                .toList();

        log.info("Service: users enriched with company data");
        return result;
    }

    @Override
    public User getById(Long id) {
        log.info("Service: fetching user by id: {}", id);

        User user = repo.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        log.info("Service: user {} found", id);

        return user;
    }

    @Override
    public void delete(Long id) {
        log.info("Service: deleting user with id: {}", id);

        repo.deleteById(id);

        log.info("Service: user {} deleted", id);
    }

    @Override
    public User update(Long id, User updated) {
        log.info("Service: updating user {} with phone: {}", id, updated.getPhone());

        updated.setId(id);
        User saved = repo.save(updated);

        log.info("Service: user {} updated successfully", id);
        return saved;
    }

    @Override
    public CompanyDTO getCompanyForUser(Long userId) {
        log.info("Service: fetching company for user {}", userId);

        User user = getById(userId);


        if (user.getCompanyId() == null) {
            log.info("Service: user {} has no company", userId);
            return null;
        }


        log.info("Service: calling - company-service for companyId: {}", user.getCompanyId());
        return companyClient.getCompanyById(user.getCompanyId());
    }

    @Override
    public UserResponse getUserWithCompany(Long id) {
        log.info("Service: fetching user with company for id: {}", id);

        User user = getById(id);


        CompanyDTO company = null;

        if (user.getCompanyId() != null) {
            log.info("Service: calling company-service for companyId: {}", user.getCompanyId());

            company = companyClient.getCompanyById(user.getCompanyId());

            log.info("Service: company data received for user {}", id);
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setCompany(company);

        log.info("Service: user {} mapped to UserResponse", id);
        return response;
    }

    @Override
    public List<UserResponse> getUsersByCompanyId(Long companyId) {
        log.info("Service: fetching users for company {}", companyId);
        List<User> users = repo.findByCompanyId(companyId);
        log.info("Service: {} users found for company {}", users.size(), companyId);
        return users.stream()
                .map(user -> getUserWithCompany(user.getId()))
                .toList();
    }


}
