package com.globits.da.service.impl;

import com.globits.da.domain.EmployeeCertificate;
import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.repository.EmployeeCertificateRepository;
import com.globits.da.service.EmployeeCertificateService;
import com.globits.da.utils.Converter;
import com.globits.da.validator.EmployeeCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeCertificateServiceImpl implements EmployeeCertificateService {
    EmployeeCertificateRepository repository;

    EmployeeCertificateValidator validator;

    Converter converter;

    @Autowired
    public void setRepositoty(EmployeeCertificateRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setValidator(EmployeeCertificateValidator validator) {
        this.validator = validator;
    }

    @Autowired
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    @Override
    public EmployeeCertificateDto create(EmployeeCertificateDto dto) {
        validator.checkDto(dto, null);
        EmployeeCertificate entity = new EmployeeCertificate();
        converter.setValueToEmployeeCertificate(entity, dto);
        repository.save(entity);
        return new EmployeeCertificateDto(entity);
    }

    @Override
    public EmployeeCertificateDto updateById(EmployeeCertificateDto dto, Integer id) {
        validator.checkDto(dto, id);
        EmployeeCertificate entity = repository.getOne(id);
        converter.setValueToEmployeeCertificate(entity, dto);
        entity = repository.save(entity);
        return new EmployeeCertificateDto(entity);
    }

    @Override
    public EmployeeCertificateDto getById(Integer id) {
        validator.checkExistence(id);
        return new EmployeeCertificateDto(repository.getOne(id));
    }

    @Override
    public List<EmployeeCertificateDto> getAll() {
        List<EmployeeCertificate> employeeCertificates = repository.findAll();
        return employeeCertificates.stream().map(EmployeeCertificateDto::new).collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Integer id) {
        validator.checkExistence(id);
        repository.deleteById(id);
        return true;
    }
}
