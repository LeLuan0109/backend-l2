package com.globits.da.validator;

import com.globits.da.dto.CertificateDto;
import com.globits.da.exception.DeleteNotAllowedException;
import com.globits.da.exception.DuplicateValueException;
import com.globits.da.exception.NotNullableException;
import com.globits.da.exception.NotFoundException;
import com.globits.da.repository.CertificateReposioty;
import com.globits.da.repository.EmployeeCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class CertificateValidator {
    private CertificateReposioty certificateReposioty;

    private EmployeeCertificateRepository employeeCertificateRepository;

    @Autowired
    public void setCertificateReposioty(CertificateReposioty certificateReposioty) {
        this.certificateReposioty = certificateReposioty;
    }

    @Autowired
    public void setEmployeeCertificateRepository(EmployeeCertificateRepository employeeCertificateRepository) {
        this.employeeCertificateRepository = employeeCertificateRepository;
    }

    public void checkDto(CertificateDto dto, Integer id){
        if (!ObjectUtils.isEmpty(id))
            checkExistence(id);
        String name = dto.getName();
        if (ObjectUtils.isEmpty(name))
            throw new NotNullableException(ValidationError.NULL_CERTIFICATE_NAME);
        name = dto.getName().trim();
        if (ObjectUtils.isEmpty(id) || !name.equalsIgnoreCase(certificateReposioty.getOne(id).getName()))
            checkDuplicateName(name);
    }

    public void checkExistence(Integer id) {
        if(ObjectUtils.isEmpty(id))
            throw new NotNullableException(ValidationError.NULL_CERTIFICATE_ID);
        if(!certificateReposioty.existsById(id))
            throw new NotFoundException(ValidationError.CERTIFICATE_NOT_FOUND);
    }

    public void checkForDelete(Integer id){
        checkExistence(id);
        if (employeeCertificateRepository.existsByCertificateId(id))
            throw new DeleteNotAllowedException(ValidationError.DELETE_CERTIFICATE_NOT_ALLOWER);
    }

    private void checkDuplicateName(String name){
        if (certificateReposioty.existsByName(name))
            throw new DuplicateValueException(ValidationError.DUPLICATE_CERTIFICATE_NAME);
    }


}
