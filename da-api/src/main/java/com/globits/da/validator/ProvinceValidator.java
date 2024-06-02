package com.globits.da.validator;

import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.exception.DeleteNotAllowedException;
import com.globits.da.exception.DuplicateValueException;
import com.globits.da.exception.NotNullableException;
import com.globits.da.exception.NotFoundException;
import com.globits.da.repository.EmployeeCertificateRepository;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class ProvinceValidator {
    private ProvinceRepository provinceRepository;

    private DistrictValidator districtValidator;

    private EmployeeRepository employeeRepository;

    private EmployeeCertificateRepository employeeCertificateRepository;

    @Autowired
    public void setProvinceRepository(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Autowired
    public void setDistrictValidator(DistrictValidator districtValidator) {
        this.districtValidator = districtValidator;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setEmployeeCertificateRepository(EmployeeCertificateRepository employeeCertificateRepository) {
        this.employeeCertificateRepository = employeeCertificateRepository;
    }

    public void checkExistence(Integer id) {
        if (ObjectUtils.isEmpty(id))
            throw new NotNullableException(ValidationError.NULL_PROVINCE_ID);
        if (!provinceRepository.existsById(id))
            throw new NotFoundException(ValidationError.PROVINCE_NOT_FOUND);
    }

    public void checkDto(ProvinceDto dto, Integer provinceId) {
        if (!ObjectUtils.isEmpty(provinceId))
            checkExistence(provinceId);
        String provinceName = dto.getName();
        if (ObjectUtils.isEmpty(provinceName))
            throw new NotNullableException(ValidationError.NULL_PROVINCE_NAME);
        provinceName = provinceName.trim();
        if (ObjectUtils.isEmpty(provinceId) || !provinceName.equalsIgnoreCase(provinceRepository.getOne(provinceId).getName()))
            checkDuplicateName(provinceName);
        List<DistrictDto> districtDtos = dto.getDistricts();
        if (!CollectionUtils.isEmpty(districtDtos))
            districtValidator.checkDtos(districtDtos, provinceId);
    }

    public void checkForDelete(Integer id) {
        checkExistence(id);
        if (employeeRepository.existsByProvinceId(id))
            throw new DeleteNotAllowedException(ValidationError.DELETE_PROVINCE_NOT_ALLOWED);
        if (employeeCertificateRepository.existsByProvinceId(id))
            throw new DeleteNotAllowedException(ValidationError.DELETE_PROVINCE_NOT_ALLOWED);
    }

    private void checkDuplicateName(String name) {
        if (provinceRepository.existsByName(name))
            throw new DuplicateValueException(ValidationError.DUPLICATE_PROVINCE_NAME);
    }
}
