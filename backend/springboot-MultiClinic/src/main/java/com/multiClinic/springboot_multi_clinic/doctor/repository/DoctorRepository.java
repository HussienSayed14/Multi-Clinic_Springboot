package com.multiClinic.springboot_multi_clinic.doctor.repository;


import com.multiClinic.springboot_multi_clinic.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByEmail(String email);
    boolean existsByEmail(String email);
}
