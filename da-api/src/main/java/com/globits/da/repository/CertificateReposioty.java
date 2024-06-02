package com.globits.da.repository;

import com.globits.da.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CertificateReposioty extends JpaRepository<Certificate, Integer> {
    @Query("SELECT " +
            "CASE WHEN COUNT(c)> 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Certificate c WHERE LOWER(c.name) LIKE LOWER(:name)")
    boolean existsByName(@Param("name") String name);
}
