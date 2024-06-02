package com.globits.da.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_certificate")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "certificate")
    private List<EmployeeCertificate> employeeCertificates;

    public Certificate() {
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

    public List<EmployeeCertificate> getEmployeeCertificates() {
        return employeeCertificates;
    }

    public void setEmployeeCertificates(List<EmployeeCertificate> employeeCertificates) {
        this.employeeCertificates = employeeCertificates;
    }
}
