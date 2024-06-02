package com.globits.da.dto;

import com.globits.da.domain.District;
import org.springframework.util.ObjectUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DistrictDto {
    private Integer id;

    private String name;

    private Integer provinceId;

    private List<CommuneDto> communes = new ArrayList<>();

    public DistrictDto() {
    }

    public DistrictDto(District entity) {
        if (!ObjectUtils.isEmpty(entity)) {
            id = entity.getId();
            name = entity.getName();
            provinceId = entity.getProvince().getId();
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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public List<CommuneDto> getCommunes() {
        return communes;
    }

    public void setCommunes(List<CommuneDto> communes) {
        this.communes = communes;
    }
}
