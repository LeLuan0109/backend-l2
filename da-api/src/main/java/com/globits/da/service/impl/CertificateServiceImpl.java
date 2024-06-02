package com.globits.da.service.impl;

import com.globits.da.domain.Certificate;
import com.globits.da.dto.CertificateDto;
import com.globits.da.repository.CertificateReposioty;
import com.globits.da.service.CertificateService;
import com.globits.da.validator.CertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {
    private CertificateReposioty certificateReposioty;

    private CertificateValidator validator;

    @Autowired
    public void setCertificateReposioty(CertificateReposioty certificateReposioty) {
        this.certificateReposioty = certificateReposioty;
    }

    @Autowired
    public void setValidator(CertificateValidator validator) {
        this.validator = validator;
    }

    @Override
    public CertificateDto create(CertificateDto dto) {
        validator.checkDto(dto, null);
        Certificate certificate = new Certificate();
        certificate.setName(dto.getName().trim());
        certificateReposioty.save(certificate);
        return new CertificateDto(certificate);
    }

    @Override
    public CertificateDto updateById(Integer id, CertificateDto dto) {
        validator.checkDto(dto, id);
        Certificate certificate = certificateReposioty.getOne(id);
        certificate.setName(dto.getName().trim());
        certificate = certificateReposioty.save(certificate);
        return new CertificateDto(certificate);
    }

    @Override
    public CertificateDto getById(Integer id) {
        validator.checkExistence(id);
        return new CertificateDto(certificateReposioty.getOne(id));
    }

    @Override
    public List<CertificateDto> getAll() {
        List<Certificate> certificates = certificateReposioty.findAll();
        return certificates.stream().map(CertificateDto::new).collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Integer id) {
        validator.checkForDelete(id);
        certificateReposioty.deleteById(id);
        return true;
    }
}
