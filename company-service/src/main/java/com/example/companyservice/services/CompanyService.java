package com.example.companyservice.services;

import com.example.companyservice.dto.CompanyResponse;
import com.example.companyservice.enteties.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    Company create(Company company);
    Company update(Long id, Company company);
    Company getById(Long id);
    void delete(Long id);
    CompanyResponse getCompanyWithUsers(Long companyId);
    Page<CompanyResponse> findAllWithUsers(Pageable pageable);
}
