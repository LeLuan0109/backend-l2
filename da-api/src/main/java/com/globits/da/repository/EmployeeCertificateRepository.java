package com.globits.da.repository;

import com.globits.da.domain.EmployeeCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeCertificateRepository extends JpaRepository<EmployeeCertificate, Integer> {
    @Query("SELECT ec FROM EmployeeCertificate ec WHERE ec.employee.id = :eId AND ec.certificate.id = :cId AND ec.expirationDate > :currentDate")
    List<EmployeeCertificate> getByEmployeeIdAndCertificateId(@Param("eId") Integer eId,
                                                              @Param("cId") Integer cId,
                                                              @Param("currentDate") LocalDate currentDate);

    @Query("SELECT " +
            "CASE WHEN COUNT(e)> 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM EmployeeCertificate e WHERE e.province.id = :id")
    boolean existsByProvinceId(@Param("id") int id);

    @Query("SELECT " +
            "CASE WHEN COUNT(e)> 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM EmployeeCertificate e WHERE e.employee.id = :id")
    boolean existsByEmployeeId(@Param("id") int id);

    @Query("SELECT " +
            "CASE WHEN COUNT(e)> 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM EmployeeCertificate e WHERE e.certificate.id = :id")
    boolean existsByCertificateId(@Param("id") int id);
}

