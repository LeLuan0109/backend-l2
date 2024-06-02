package com.globits.da.service;

import com.globits.da.dto.CertificateDto;
import java.util.List;

public interface CertificateService {
    CertificateDto create(CertificateDto dto);
    CertificateDto getById(Integer id);
    CertificateDto updateById(Integer id, CertificateDto certificateDto);
    boolean deleteById(Integer id);
    List<CertificateDto> getAll();
}
