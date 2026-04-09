package com.example.companyservice.clients;

import com.example.companyservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service", url = "http://user-service:8081")
public interface UserClient {
    @GetMapping("/users")
    List<UserDTO> getAllUsers();
}
