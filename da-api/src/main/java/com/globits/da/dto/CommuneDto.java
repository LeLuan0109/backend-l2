package com.globits.da.dto;

import com.globits.da.domain.Commune;
import org.springframework.util.ObjectUtils;

public class CommuneDto {
    private Integer id;

    private String name;

    private Integer districtId;

    public CommuneDto() {
    }

    public CommuneDto(Commune entity) {
        if (!ObjectUtils.isEmpty(entity)) {
            id = entity.getId();
            name = entity.getName();
            districtId = entity.getDistrict().getId();
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

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }
}
