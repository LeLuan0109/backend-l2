package com.globits.da.dto;

import com.globits.da.domain.Certificate;
import org.springframework.util.ObjectUtils;

public class CertificateDto {
    private Integer id;

    private String name;

    public CertificateDto() {
    }

    public CertificateDto(Certificate certificate) {
        if (!ObjectUtils.isEmpty(certificate)) {
            id = certificate.getId();
            name = certificate.getName();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
