package com.example.companyservice.controllers;

import com.example.companyservice.dto.CompanyResponse;
import com.example.companyservice.dto.UserDTO;
import com.example.companyservice.enteties.Company;
import com.example.companyservice.services.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;



    @GetMapping("/simple/{id}")
    public Company getSimpleById(@PathVariable Long id) {
        log.info("GET /companies/simple/{} - fetching company (simple)", id);

        Company company = companyService.getById(id);

        log.info("GET /companies/simple/{} - company fetched", id);
        return company;
    }

     @GetMapping
     public Page<CompanyResponse> getCompanies(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        log.info("GET /companies - page={}, size={}", page, size);
        return companyService.findAllWithUsers(PageRequest.of(page, size));
}
    @GetMapping("/{id}/users")
    public List<UserDTO> getCompanyUsers(@PathVariable Long id) {
        log.info("GET /companies/{}/users - fetching users for company", id);

        List<UserDTO> users = companyService.getUsersForCompany(id);

        log.info("GET /companies/{}/users - fetched {} users", id, users.size());
        return users;
    }

    @GetMapping("{id}")
    public ResponseEntity<CompanyResponse> getCompany(@PathVariable Long id) {
        log.info("GET /companies/{} - fetching company with users", id);

        CompanyResponse response = companyService.getCompanyWithUsers(id);
        if (response == null) {
            log.warn("GET /companies/{} - company not found", id);
            return ResponseEntity.notFound().build();
        }

        log.info("GET /companies/{} - company fetched successfully", id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable Long id, @RequestBody Company company) {
        log.info("PUT / companies/{} - updating company: {}", id, company);

        Company updated = companyService.update(id, company);

        log.info("PUT /companies/{} - company updated successfully", id);
        return updated;
    }

    @PostMapping
    public Company create(@RequestBody Company company) {
        log.info("POST /companies - creating company: {}", company);

        Company created = companyService.create(company);

        log.info("Post /companies - company created with id: {}", created.getId());
        return created;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("DELETE / companies/{} - deleting company", id);

        companyService.delete(id);

        log.info("DELETE /companies/{} - company deleted successfully", id);
    }

}
