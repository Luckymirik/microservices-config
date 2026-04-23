package com.example.companyservice.services;
import com.example.companyservice.clients.UserClient;
import com.example.companyservice.dto.CompanyResponse;
import com.example.companyservice.dto.UserDTO;
import com.example.companyservice.enteties.Company;
import com.example.companyservice.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.shared.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repo;
    private final UserClient userClient;

    @Override
    public Company create(Company company) {
        log.info("Service: creating company with name: {}", company.getName());

        Company saved = repo.save(company);

        log.info("Service: company created with id: {}", saved.getId());
        return saved;
    }

    @Override
    public Company update(Long id, Company updated) {
        log.info("Service: updating company {} with name: {}", id, updated.getName());

        updated.setId(id);
        Company saved = repo.save(updated);

        log.info("Service: company {} updated successfully", id);
        return saved;
    }


    @Override
    public List<Company> findAll() {
        log.info("Service: fetching all companies");

        List<Company> companies = repo.findAll();

        log.info("Service: {} companies found", companies.size());
        return companies;
    }

    @Override
    public List<UserDTO> getUsersForCompany(Long companyId) {
        log.info("Service: fetching users for company{}", companyId);

        log.info("Service: calling user-service to get all users");
        List<UserDTO> users = userClient.getAllUsers();

        List<UserDTO> filtered = users.stream()
                .filter(user -> companyId.equals(user.getCompanyId()))
                .toList();

        log.info("Service: {} users belong to company {}", filtered.size(), companyId);

        if (filtered.isEmpty()) {
            log.warn("Service: {} no usersbelong to company {}", filtered.size(), companyId);
        }
        return filtered;
    }

    @Override
    public Company getById(Long id) {
        log.info("Service: fetching company by id: {}", id);

        Company company = repo.findById(id).orElseThrow(() -> new NotFoundException("Company not found with id: " + id));

        if (company == null) {
            log.warn("Service: company not found with id: {}", id);
        } else {
            log.info("Service: company {} found", id);
        }

        return company;
    }
    @Override
    public void delete(Long id) {
        log.info("Service: deleting company with id: {}", id);

        repo.deleteById(id);

        log.info("Service: company {} deleted", id);
    }

    @Override
    public CompanyResponse getCompanyWithUsers(Long companyId) {
        log.info("Service: fetching company with users for id: {}", companyId);

        Company company = repo.findById(companyId).orElse(null);


        if (company == null) {
            log.warn("Service: company {} not found", companyId);
            return null;
        }

        log.info("Service: calling to user-service for users of company {}", companyId);

        List<UserDTO> users = userClient.getAllUsers()
                .stream()
                .filter(u -> companyId.equals(u.getCompanyId()))
                .toList();

        log.info("Service: {} users fetched for company {}", users.size(), companyId);

        CompanyResponse response = new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getBudget(),
                users
        );

        log.info("Service: company {} mapped to CompanyResponse", companyId);
        return response;
    }

    @Override
    public Page<CompanyResponse> findAllWithUsers(Pageable pageable) {
        log.info("Service: fetching companies page {} size {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<Company> companies = repo.findAll(pageable);

        return companies.map(c -> getCompanyWithUsers(c.getId()));
    }
}
