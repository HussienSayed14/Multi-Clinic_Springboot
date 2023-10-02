package com.multiClinic.springbootMultiClinic.doctor.repository;


import com.multiClinic.springbootMultiClinic.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByEmail(String email);
}
