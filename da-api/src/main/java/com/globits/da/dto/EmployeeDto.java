package com.globits.da.dto;

import com.globits.da.Constants;
import com.globits.da.domain.Employee;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.*;

public class EmployeeDto {
    private Integer id;

    @NotNull(message = Constants.NULL_EMPLOYEE_CODE)
    @Pattern(regexp = Constants.EMPLOYEE_CODE_REGEX, message = Constants.EMPLOYEE_CODE_FORMAT_ERROR)
    private String code;

    @NotBlank(message = Constants.NULL_EMPLOYEE_NAME)
    private String name;

    @Pattern(regexp = Constants.EMPLOYEE_EMAIL_REGEX, message = Constants.EMPLOYEE_EMAIL_FORMAT_ERROR)
    @NotNull(message = Constants.NULL_EMPLOYEE_EMAIL)
    private String email;

    @Pattern(regexp = Constants.EMPLOYEE_PHONE_REGEX, message = Constants.EMPLOYEE_PHONE_FORMAT_ERROR)
    @NotNull(message = Constants.NULL_EMPLOYEE_PHONE)
    private String phone;

    @Range(min = Constants.MIN_EMPLOYEE_AGE, max = Constants.MAX_EMPLOYEE_AGE, message = Constants.INVALID_EMPLOYEE_AGE_ERROR)
    @NotNull(message = Constants.NULL_EMPLOYEE_AGE)
    private Integer age;

    @NotNull(message = Constants.NULL_EMPLOYEE_PROVINCEID)
    private Integer provinceId;

    @NotNull(message = Constants.NULL_EMPLOYEE_DISTRICTID)
    private Integer districtId;

    @NotNull(message = Constants.NULL_EMPLOYEE_COMMUNEID)
    private Integer communeId;

    public EmployeeDto() {
    }

    public EmployeeDto(Employee employee) {
        if (!ObjectUtils.isEmpty(employee)) {
            id = employee.getId();
            code = employee.getCode();
            name = employee.getName();
            email = employee.getEmail();
            phone = employee.getPhone();
            age = employee.getAge();
            provinceId = employee.getProvince().getId();
            districtId = employee.getDistrict().getId();
            communeId = employee.getCommune().getId();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getCommuneId() {
        return communeId;
    }

    public void setCommuneId(Integer communeId) {
        this.communeId = communeId;
    }
}
