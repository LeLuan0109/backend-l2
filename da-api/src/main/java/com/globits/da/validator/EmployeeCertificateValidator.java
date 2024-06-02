package com.globits.da.validator;

import com.globits.da.Constants;
import com.globits.da.domain.EmployeeCertificate;
import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.exception.NotNullableException;
import com.globits.da.exception.NotFoundException;
import com.globits.da.exception.NotValidCertificateException;
import com.globits.da.repository.EmployeeCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeCertificateValidator {
    private EmployeeCertificateRepository repository;

    private CertificateValidator certificateValidator;

    private EmployeeValidator employeeValidator;

    private ProvinceValidator provinceValidator;

    @Autowired
    public void setEmployeeCertificateRepository(EmployeeCertificateRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setCertificateValidator(CertificateValidator certificateValidator) {
        this.certificateValidator = certificateValidator;
    }

    @Autowired
    public void setEmployeeValidator(EmployeeValidator employeeValidator) {
        this.employeeValidator = employeeValidator;
    }

    @Autowired
    public void setProvinceValidator(ProvinceValidator provinceValidator) {
        this.provinceValidator = provinceValidator;
    }

    public void checkExistence(Integer id) {
        if (ObjectUtils.isEmpty(id))
            throw new NotNullableException(ValidationError.NULL_EMPLOYEE_CERTIFICATE_ID);
        if (!repository.existsById(id))
            throw new NotFoundException(ValidationError.EMPLOYEE_CERTIFICATE_NOT_FOUND);
    }

    public void checkDto(EmployeeCertificateDto dto, Integer id) {
        if (!ObjectUtils.isEmpty(id))
            checkExistence(id);
        provinceValidator.checkExistence(dto.getProvinceId());
        checkCertificate(dto);
        Integer employeeId = dto.getEmployeeId();
        employeeValidator.checkExistence(employeeId);
        List<EmployeeCertificate> validEmployeeCertificates = repository.getByEmployeeIdAndCertificateId(employeeId, dto.getCertificateId(), LocalDate.now());
        if (!CollectionUtils.isEmpty(validEmployeeCertificates)) {
            List<EmployeeCertificateDto> validEmployeeCertificateDtos = validEmployeeCertificates.stream().map(EmployeeCertificateDto::new).collect(Collectors.toList());
            checkCertificatesOfEmployee(validEmployeeCertificateDtos, dto, id);
        }
    }

    private void checkCertificate(EmployeeCertificateDto dto) {
        certificateValidator.checkExistence(dto.getCertificateId());
        LocalDate startDate = dto.getStartDate();
        if (ObjectUtils.isEmpty(startDate))
            throw new NotNullableException(ValidationError.NULL_CERTIFICATE_START_DATE);
        LocalDate expirationDate = dto.getExpirationDate();
        if (ObjectUtils.isEmpty(startDate))
            throw new NotNullableException(ValidationError.NULL_CERTIFICATE_EXPIRATION_DATE);
        if (expirationDate.isBefore(LocalDate.now()))
            throw new NotValidCertificateException(ValidationError.CERTIFICATE_EXPIRATION_DATE_BEFORE_NOW);
        if (startDate.isAfter(expirationDate))
            throw new NotValidCertificateException(ValidationError.CERTIFICATE_EXPIRATION_DATE_BEFORE_START_DATE);
    }

    private void checkCertificatesOfEmployee(List<EmployeeCertificateDto> validCertificatesOfEmployee, EmployeeCertificateDto newDto, Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            if (validCertificatesOfEmployee.size() == Constants.MAX_NUMBER_OF_CERTIFICATES)
                throw new NotValidCertificateException(ValidationError.INVALID_NUMBER_OF_CERTIFICATES);
            int newProvinceId = newDto.getProvinceId();
            for (EmployeeCertificateDto employeeCertificate : validCertificatesOfEmployee) {
                if (employeeCertificate.getProvinceId() == newProvinceId)
                    throw new NotValidCertificateException(ValidationError.INVALID_CERTIFICATE_AND_PROVINCE);
            }
            return;
        }
        EmployeeCertificateDto oldDto = new EmployeeCertificateDto(repository.getOne(id));
        if (!oldDto.getEmployeeId().equals(newDto.getEmployeeId()))
            checkCertificatesOfEmployee(validCertificatesOfEmployee, newDto, null);
        if (!oldDto.getCertificateId().equals(newDto.getCertificateId()))
            checkCertificatesOfEmployee(validCertificatesOfEmployee, newDto, null);
        validCertificatesOfEmployee.removeIf(dto -> dto.getId().equals(id));
        for (EmployeeCertificateDto employeeCertificate : validCertificatesOfEmployee) {
            int newProvinceId = newDto.getProvinceId();
            if (employeeCertificate.getProvinceId() == newProvinceId)
                throw new NotValidCertificateException(ValidationError.INVALID_CERTIFICATE_AND_PROVINCE);
        }
    }
}

