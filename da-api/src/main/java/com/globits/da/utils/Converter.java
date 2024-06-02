package com.globits.da.utils;

import com.globits.da.domain.*;
import com.globits.da.dto.*;
import com.globits.da.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {
    private CommuneRepository communeRepository;

    private DistrictRepository districtRepository;

    private ProvinceRepository provinceRepository;

    private CertificateReposioty certificateReposioty;

    private EmployeeRepository employeeRepository;

    @Autowired
    public void setCommuneRepository(CommuneRepository communeRepository) {
        this.communeRepository = communeRepository;
    }

    @Autowired
    public void setDistrictRepository(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Autowired
    public void setProvinceRepository(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Autowired
    public void setCertificateReposioty(CertificateReposioty certificateReposioty) {
        this.certificateReposioty = certificateReposioty;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<CommuneDto> communesToCommuneDtos(List<Commune> communes) {
        if (CollectionUtils.isEmpty(communes))
            return new ArrayList<>();
        return communes.stream().map(CommuneDto::new).collect(Collectors.toList());
    }

    public DistrictDto districtToDistrictDto(District district, boolean includeCommune) {
        DistrictDto districtDto = new DistrictDto(district);
        List<Commune> communes = district.getCommunes();
        List<CommuneDto> communeDtos = new ArrayList<>();
        if (includeCommune && !CollectionUtils.isEmpty(communes))
            communeDtos = communesToCommuneDtos(communes);
        districtDto.setCommunes(communeDtos);
        return districtDto;
    }

    public List<DistrictDto> districtsToDistrictDtos(List<District> districts, boolean includeCommunes) {
        if (CollectionUtils.isEmpty(districts))
            return new ArrayList<>();
        return districts.stream()
                .map(district -> districtToDistrictDto(district, includeCommunes))
                .collect(Collectors.toList());
    }

    public ProvinceDto provinceToProvinceDto(Province province, boolean includeDistricts, boolean includeCommunes) {
        ProvinceDto provinceDto = new ProvinceDto(province);
        List<District> districts = province.getDistricts();
        List<DistrictDto> districtDtos = new ArrayList<>();
        if (includeDistricts && !CollectionUtils.isEmpty(districts))
            districtDtos = districtsToDistrictDtos(districts, includeCommunes);
        provinceDto.setDistricts(districtDtos);
        return provinceDto;
    }

    public void setValueToCommunes(List<Commune> communes, List<CommuneDto> communeDtos, District district, boolean isUpdate) {
        for (CommuneDto communeDto : communeDtos) {
            Commune commune;
            if (isUpdate)
                commune = communeRepository.getOne(communeDto.getId());
            else
                commune = new Commune();
            commune.setDistrict(district);
            commune.setName(communeDto.getName().trim());
            communes.add(commune);
        }
    }

    public void setValueToDistrict(District district, DistrictDto districtDto, Province province, boolean isUpdate) {
        district.setName(districtDto.getName().trim());
        district.setProvince(province);
        List<CommuneDto> communeDtos = districtDto.getCommunes();
        List<Commune> communes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(communeDtos)) {
            setValueToCommunes(communes, communeDtos, district, isUpdate);
        }
        district.setCommunes(communes);
    }

    public void setValueToProvince(Province province, ProvinceDto provinceDto, boolean isUpdate) {
        province.setName(provinceDto.getName().trim());
        List<DistrictDto> districtDtos = provinceDto.getDistricts();
        List<District> districts = new ArrayList<>();
        if (!CollectionUtils.isEmpty(districtDtos)) {
            for (DistrictDto districtDto : districtDtos) {
                District district;
                if (isUpdate)
                    district = districtRepository.getOne(districtDto.getId());
                else
                    district = new District();
                setValueToDistrict(district, districtDto, province, isUpdate);
                districts.add(district);
            }
        }
        province.setDistricts(districts);
    }

    public void setValueToEmployee(Employee entity, EmployeeDto dto) {
        entity.setAge(dto.getAge());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setCommune(communeRepository.getOne(dto.getCommuneId()));
        entity.setDistrict(districtRepository.getOne(dto.getDistrictId()));
        entity.setProvince(provinceRepository.getOne(dto.getProvinceId()));
    }

    public void setValueToEmployeeCertificate(EmployeeCertificate entity, EmployeeCertificateDto dto) {
        entity.setStartDate(dto.getStartDate());
        entity.setExpirationDate(dto.getExpirationDate());
        entity.setCertificate(certificateReposioty.getOne(dto.getCertificateId()));
        entity.setProvince(provinceRepository.getOne(dto.getProvinceId()));
        entity.setEmployee(employeeRepository.getOne(dto.getEmployeeId()));
    }
}
