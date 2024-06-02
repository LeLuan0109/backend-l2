package com.globits.da.repository;

import com.globits.da.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT " +
            "CASE WHEN COUNT(e) > 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Employee e WHERE e.code = :code")
    boolean existsByCode(@Param("code") String code);

    @Query("SELECT " +
            "CASE WHEN COUNT(e) > 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Employee e WHERE e.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT " +
            "CASE WHEN COUNT(e) > 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Employee e WHERE e.phone = :phone")
    boolean existsByPhone(@Param("phone") String phone);

    @Query("SELECT " +
            "CASE WHEN COUNT(e) > 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Employee e WHERE e.commune.id = :communeId")
    boolean existsByCommuneId(@Param("communeId") int communeId);

    @Query("SELECT " +
            "CASE WHEN COUNT(e) > 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Employee e WHERE e.district.id = :districtId")
    boolean existsByDistrictId(@Param("districtId") int districtId);

    @Query("SELECT " +
            "CASE WHEN COUNT(e) > 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Employee e WHERE e.province.id = :provinceId")
    boolean existsByProvinceId(@Param("provinceId") int provinceId);
}
