package com.multiClinic.springbootMultiClinic.patientHistory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientHistoryRepository extends JpaRepository<PatientHistory, Integer> {
}
