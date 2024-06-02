package com.globits.da.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.globits.da.domain.EmployeeCertificate;
import org.springframework.util.ObjectUtils;
import java.time.LocalDate;


public class EmployeeCertificateDto {
    private Integer id;
    private Integer employeeId;
    private Integer certificateId;
    private Integer provinceId;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate expirationDate;

    public EmployeeCertificateDto() {
    }

    public EmployeeCertificateDto(EmployeeCertificate entity){
        if (!ObjectUtils.isEmpty(entity)){
            id = entity.getId();
            employeeId = entity.getEmployee().getId();
            certificateId = entity.getCertificate().getId();
            provinceId = entity.getProvince().getId();
            startDate = entity.getStartDate();
            expirationDate = entity.getExpirationDate();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
