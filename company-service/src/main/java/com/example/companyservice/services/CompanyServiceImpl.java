package com.example.companyservice.services;
import com.example.companyservice.clients.UserClient;
import com.example.companyservice.dto.CompanyResponse;
import com.example.companyservice.dto.UserDTO;
import com.example.companyservice.enteties.Company;
import com.example.companyservice.repositories.CompanyRepository;
import com.example.shared.exception.ConflictException;
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

        if (repo.existsByName(company.getName())){
            throw new ConflictException("Company with name '" + company.getName() + "'already exists");
        }

        Company saved = repo.save(company);
        log.info("Service: company created with id: {}", saved.getId());
        return saved;
    }

    @Override
    public Company update(Long id, Company updated) {
        log.info("Service: updating company {} with name: {}", id, updated.getName());

        getById(id);

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
    public Company getById(Long id) {
        log.info("Service: fetching company by id: {}", id);
        Company company = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found with id: " + id));
        log.info("Service: company {} found", id);
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

        Company company = getById(companyId);

        log.info("Service: calling user-service for users of company {}", companyId);
        List<UserDTO> users = userClient.getUsersByCompanyId(companyId);

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
