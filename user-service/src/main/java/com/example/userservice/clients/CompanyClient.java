package com.example.userservice.clients;

import com.example.userservice.dto.CompanyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service")

public interface CompanyClient {
    @GetMapping("/companies/simple/{id}")
    CompanyDTO getCompanyById(@PathVariable Long id);
}
