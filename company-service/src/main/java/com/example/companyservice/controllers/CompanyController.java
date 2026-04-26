package com.example.companyservice.controllers;

import com.example.companyservice.dto.CompanyRequest;
import com.example.companyservice.dto.CompanyResponse;
import com.example.companyservice.enteties.Company;
import com.example.companyservice.mapper.CompanyMapper;
import com.example.companyservice.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @GetMapping("/simple/{id}")
    public Company getSimpleById(@PathVariable Long id) {
        log.info("GET /companies/simple/{} - fetching company (simple)", id);
        Company company = companyService.getById(id);
        log.info("GET /companies/simple/{} - company fetched", id);
        return company;
    }

    @GetMapping
    public Page<CompanyResponse> getCompanies(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /companies - page={}, size={}", page, size);
        return companyService.findAllWithUsers(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompany(@PathVariable Long id) {
        log.info("GET /companies/{} - fetching company with users", id);
        CompanyResponse response = companyService.getCompanyWithUsers(id);
        log.info("GET /companies/{} - company fetched successfully", id);
        return response;
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable Long id, @Valid @RequestBody CompanyRequest request) {
        log.info("PUT /companies/{} - updating company", id);
        Company company = companyMapper.toCompany(request);
        Company updated = companyService.update(id, company);
        log.info("PUT /companies/{} - company updated successfully", id);
        return updated;
    }

    @PostMapping
    public Company create(@Valid @RequestBody CompanyRequest request) {
        log.info("POST /companies - creating company: {}", request.getName());
        Company company = companyMapper.toCompany(request);
        Company created = companyService.create(company);
        log.info("Post /companies - company created with id: {}", created.getId());
        return created;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("DELETE /companies/{} - deleting company", id);
        companyService.delete(id);
        log.info("DELETE /companies/{} - company deleted successfully", id);
    }

}
