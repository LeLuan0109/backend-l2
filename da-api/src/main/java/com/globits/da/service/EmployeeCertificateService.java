package com.globits.da.service;


import com.globits.da.dto.EmployeeCertificateDto;

import java.util.List;

public interface EmployeeCertificateService {
    EmployeeCertificateDto create(EmployeeCertificateDto dto);
    EmployeeCertificateDto updateById(EmployeeCertificateDto dto, Integer id);
    EmployeeCertificateDto getById(Integer id);
    List<EmployeeCertificateDto> getAll();
    boolean deleteById(Integer id);
}
