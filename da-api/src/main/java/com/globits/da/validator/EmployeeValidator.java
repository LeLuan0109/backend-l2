package com.globits.da.validator;

import com.globits.da.dto.EmployeeDto;
import com.globits.da.exception.DeleteNotAllowedException;
import com.globits.da.exception.DuplicateValueException;
import com.globits.da.exception.NotNullableException;
import com.globits.da.exception.NotFoundException;
import com.globits.da.repository.EmployeeCertificateRepository;
import com.globits.da.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@Validated
public class EmployeeValidator {
    private EmployeeRepository employeeRepository;

    private ProvinceValidator provinceValidator;

    private DistrictValidator districtValidator;

    private CommuneValidator communeValidator;

    private EmployeeCertificateRepository employeeCertificateRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setProvinceValidator(ProvinceValidator provinceValidator) {
        this.provinceValidator = provinceValidator;
    }

    @Autowired
    public void setDistrictValidator(DistrictValidator districtValidator) {
        this.districtValidator = districtValidator;
    }

    @Autowired
    public void setCommuneValidator(CommuneValidator communeValidator) {
        this.communeValidator = communeValidator;
    }

    @Autowired
    public void setEmployeeCertificateRepository(EmployeeCertificateRepository employeeCertificateRepository) {
        this.employeeCertificateRepository = employeeCertificateRepository;
    }

    public void checkDto(@Valid EmployeeDto dto, Integer id) {
        boolean isCreating = ObjectUtils.isEmpty(id);
        if (!isCreating)
            checkExistence(id);
        String code = dto.getCode();
        if (isCreating || !code.equals(employeeRepository.getOne(id).getCode()))
            checkDuplicateCode(code);
        String email = dto.getEmail();
        if (isCreating || !email.equalsIgnoreCase(employeeRepository.getOne(id).getEmail()))
            checkDuplicateEmail(email);
        String phone = dto.getPhone();
        if (isCreating || !phone.equals(employeeRepository.getOne(id).getPhone()))
            checkDuplicatePhone(phone);
        checkLocation(dto.getProvinceId(), dto.getDistrictId(), dto.getCommuneId());
    }

    public void checkExistence(Integer id){
        if (ObjectUtils.isEmpty(id))
            throw new NotNullableException(ValidationError.NULL_EMPLOYEE_ID);
        if (!employeeRepository.existsById(id))
            throw new NotFoundException(ValidationError.EMPLOYEE_NOT_FOUND);
    }

    private void checkLocation(Integer provinceId, Integer districtId, Integer communeId){
        provinceValidator.checkExistence(provinceId);
        districtValidator.checkExistence(districtId);
        communeValidator.checkExistence(communeId);
        districtValidator.checkDistrictBelongToProvince(districtId, provinceId);
        communeValidator.checkCommuneBelongToDistrict(communeId, districtId);
    }

    private void checkDuplicateCode(String code) {
        if (employeeRepository.existsByCode(code))
            throw new DuplicateValueException(ValidationError.DUPLICATE_EMPLOYEE_CODE);
    }

    private void checkDuplicateEmail(String email) {
        if (employeeRepository.existsByEmail(email))
            throw new DuplicateValueException(ValidationError.DUPLICATE_EMPLOYEE_EMAIL);
    }

    private void checkDuplicatePhone(String phone) {
        if (employeeRepository.existsByPhone(phone))
            throw new DuplicateValueException(ValidationError.DUPLICATE_EMPLOYEE_PHONE);
    }

    public void checkForDelete(Integer id){
        checkExistence(id);
        if (employeeCertificateRepository.existsByEmployeeId(id))
            throw new DeleteNotAllowedException(ValidationError.DELETE_EMPLOYEE_NOT_ALLOWED);
    }
}
