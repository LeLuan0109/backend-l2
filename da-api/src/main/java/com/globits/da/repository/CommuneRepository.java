package com.globits.da.repository;

import com.globits.da.domain.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, Integer> {
    @Query("SELECT c FROM Commune c WHERE c.district.id = :id")
    List<Commune> getAllByDistrictId(@Param("id") int id);

    @Query("SELECT " +
            "CASE WHEN COUNT(c)> 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Commune c WHERE LOWER(c.name) LIKE LOWER(:name)")
    boolean existsByName(@Param("name") String name);
}
