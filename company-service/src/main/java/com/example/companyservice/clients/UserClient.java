package com.example.companyservice.clients;

import com.example.companyservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/by-company/{companyId}")
    List<UserDTO> getUsersByCompanyId(@PathVariable Long companyId);
}
