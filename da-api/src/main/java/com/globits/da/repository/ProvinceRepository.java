package com.globits.da.repository;

import com.globits.da.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    @Query("SELECT " +
            "CASE WHEN COUNT(p)> 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Province p WHERE LOWER(p.name) LIKE LOWER(:name)")
    boolean existsByName(@Param("name") String name);
}
