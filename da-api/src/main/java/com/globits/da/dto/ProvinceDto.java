package com.globits.da.dto;

import com.globits.da.domain.Province;
import org.springframework.util.ObjectUtils;
import java.io.Serializable;
import java.util.List;

public class ProvinceDto {
    private Integer id;

    private String name;

    private List<DistrictDto> districts;

    public ProvinceDto() {
    }

    public ProvinceDto(Province entity){
        if (!ObjectUtils.isEmpty(entity)) {
            id = entity.getId();
            name = entity.getName();
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

    public List<DistrictDto> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictDto> districts) {
        this.districts = districts;
    }
}
