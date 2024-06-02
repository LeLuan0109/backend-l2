package com.globits.da.validator;

import com.globits.da.domain.Commune;
import com.globits.da.dto.CommuneDto;
import com.globits.da.exception.*;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CommuneValidator {
    private CommuneRepository communeRepository;

    private EmployeeRepository employeeRepository;

    private DistrictValidator districtValidator;

    private DistrictRepository districtRepository;

    @Autowired
    public void setCommuneRepository(CommuneRepository communeRepository) {
        this.communeRepository = communeRepository;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setDistrictValidator(DistrictValidator districtValidator) {
        this.districtValidator = districtValidator;
    }

    @Autowired
    public void setDistrictRepository(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public void checkExistence(Integer id) {
        if (ObjectUtils.isEmpty(id))
            throw new NotNullableException(ValidationError.NULL_COMMUNE_ID);
        if (!communeRepository.existsById(id))
            throw new NotFoundException(ValidationError.COMMUNE_NOT_FOUND);
    }

    public void checkDto(CommuneDto dto, Integer communeId, boolean checkDistrict) {
        boolean isUpdate = !ObjectUtils.isEmpty(communeId);
        Integer districtId = dto.getDistrictId();
        if (checkDistrict)
            districtValidator.checkExistence(districtId);
        if (checkDistrict && isUpdate)
            checkExistence(communeId);
        String communeName = dto.getName();
        if (ObjectUtils.isEmpty(communeName))
            throw new NotNullableException(ValidationError.NULL_COMMUNE_NAME);
        communeName = communeName.trim();
        if (!isUpdate)
            checkDuplicateName(communeName);
        if (isUpdate) {
            boolean nameChanged = !communeName.equalsIgnoreCase(communeRepository.getOne(communeId).getName());
            if (nameChanged)
                checkDuplicateName(communeName);
        }

    }

    public void checkDtos(List<CommuneDto> communeDtos, Integer districtId) {
        Set<String> communeNames = new HashSet<>();
        String communeName;
        int sizeCount = 0;
        boolean isUpdate = !ObjectUtils.isEmpty(districtId);
        for (CommuneDto dto : communeDtos) {
            Integer communeId = null;
            if (isUpdate) {
                communeId = dto.getId();
                checkExistence(communeId);
                checkCommuneBelongToDistrict(communeId, districtId);
                dto.setDistrictId(districtId);
            }
            checkDto(dto, communeId, false);
            communeName = dto.getName().trim().toLowerCase();
            communeNames.add(communeName);
            if (communeNames.size() != ++sizeCount)
                throw new DuplicateValueException(ValidationError.DUPLICATE_COMMUNE_NAMES_IN_LIST);
        }
    }

    public void checkForDelete(Integer id){
       checkExistence(id);
       if (employeeRepository.existsByCommuneId(id))
           throw new DeleteNotAllowedException(ValidationError.DELETE_COMMUNE_NOT_ALLOWED);
    }

    public void checkCommuneBelongToDistrict(int communeId, int districtId) {
        Commune commune = communeRepository.getOne(communeId);
        if (districtId != commune.getDistrict().getId())
            throw new MismatchLocationException(ValidationError.COMMUNE_NOT_IN_DISTRICT);
    }

    private void checkDuplicateName(String communeName) {
        if (communeRepository.existsByName(communeName))
            throw new DuplicateValueException(ValidationError.DUPLICATE_COMMUNE_NAME);
    }
}
