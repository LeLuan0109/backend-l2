package com.globits.da.validator;

import com.globits.da.domain.District;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.exception.*;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DistrictValidator {
    private DistrictRepository districtRepository;

    private ProvinceValidator provinceValidator;

    private CommuneValidator communeValidator;

    private EmployeeRepository employeeRepository;

    private ProvinceRepository provinceRepository;

    @Autowired
    public void setDistrictRepository(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Autowired
    public void setProvinceValidator(ProvinceValidator provinceValidator) {
        this.provinceValidator = provinceValidator;
    }

    @Autowired
    public void setCommuneValidator(CommuneValidator communeValidator) {
        this.communeValidator = communeValidator;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setProvinceRepository(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public void checkExistence(Integer id) {
        if (ObjectUtils.isEmpty(id))
            throw new NotNullableException(ValidationError.NULL_DISTRICT_ID);
        if (!districtRepository.existsById(id))
            throw new NotFoundException(ValidationError.DISTRICT_NOT_FOUND);
    }

    public void checkDto(DistrictDto dto, Integer districtId, boolean checkProvince) {
        boolean isUpdate = !ObjectUtils.isEmpty(districtId);
        Integer provinceId = dto.getProvinceId();
        if (checkProvince)
            provinceValidator.checkExistence(provinceId);
        if (checkProvince && isUpdate)
            checkExistence(districtId);
        String districtName = dto.getName();
        if (ObjectUtils.isEmpty(districtName))
            throw new NotNullableException(ValidationError.NULL_DISTRICT_NAME);
        districtName = districtName.trim();
        if (!isUpdate)
            checkDuplicateName(districtName);
        if (isUpdate) {
            boolean nameChanged = !districtName.equalsIgnoreCase(districtRepository.getOne(districtId).getName());
            if (nameChanged)
                checkDuplicateName(districtName);
        }
        List<CommuneDto> communeDtos = dto.getCommunes();
        if (!CollectionUtils.isEmpty(communeDtos))
           communeValidator.checkDtos(communeDtos, districtId);
    }

    public void checkDtos(List<DistrictDto> districtDtos, Integer provinceId) {
        Set<String> districtNames = new HashSet<>();
        int sizeCount = 0;
        String districtName;
        boolean isUpdate = !ObjectUtils.isEmpty(provinceId);
        for (DistrictDto dto : districtDtos) {
            Integer districtId = null;
            if (isUpdate) {
                districtId = dto.getId();
                checkExistence(districtId);
                checkDistrictBelongToProvince(districtId, provinceId);
                dto.setProvinceId(provinceId);
            }
            checkDto(dto, districtId, false);
            districtName = dto.getName().trim().toLowerCase();
            districtNames.add(districtName);
            if (districtNames.size() != ++sizeCount)
                throw new DuplicateValueException(ValidationError.DUPLICATE_DISTRICT_NAMES_IN_LIST);
        }
    }

    public void checkDistrictBelongToProvince(int districtId, int provinceId) {
        District district = districtRepository.getOne(districtId);
        if (provinceId != district.getProvince().getId())
            throw new MismatchLocationException(ValidationError.DISTRICT_NOT_IN_PROVINCE);
    }

    public void checkForDelete(Integer id){
        checkExistence(id);
        if (employeeRepository.existsByDistrictId(id))
            throw new DeleteNotAllowedException(ValidationError.DELETE_DISTRICT_NOT_ALLOWED);
    }

    public void checkDuplicateName(String districtName) {
        if (districtRepository.existsByName(districtName))
            throw new DuplicateValueException(ValidationError.DUPLICATE_DISTRICT_NAME);
    }
}