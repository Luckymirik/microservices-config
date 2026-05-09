package com.example.userservice.clients;

import com.example.userservice.dto.CompanyDTO;
import org.springframework.stereotype.Component;

@Component
public class CompanyFallback implements CompanyClient {
    @Override
    public CompanyDTO getCompanyById(Long id){
        return new CompanyDTO(id, "Service temporarily unavailable", 0L);
    }
}
