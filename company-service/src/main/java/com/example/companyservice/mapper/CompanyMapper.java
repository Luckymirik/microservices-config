package com.example.companyservice.mapper;

import com.example.companyservice.dto.CompanyRequest;
import com.example.companyservice.dto.CompanyResponse;
import com.example.companyservice.dto.UserDTO;
import com.example.companyservice.enteties.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyMapper {

    CompanyResponse toCompanyResponse(Company company, List<UserDTO> users);

    Company toCompany(CompanyRequest request);
}
