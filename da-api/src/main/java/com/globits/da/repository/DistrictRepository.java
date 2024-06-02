package com.globits.da.repository;

import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    @Query("SELECT d FROM District d WHERE d.province.id = ?1")
    List<District> getAllByProvinceId(int id);

    @Query("SELECT " +
            "CASE WHEN COUNT(d)> 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM District d WHERE LOWER(d.name) LIKE LOWER(:name)")
    boolean existsByName(@Param("name") String name);
}
