package com.multiClinic.springboot_multi_clinic.patientHistory;

import com.multiClinic.springboot_multi_clinic.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientHistoryService {
    @Autowired PatientHistoryRepository patientHistoryRepository;


    public PatientHistory addPatientHistory(PatientHistory patientHistory, Patient patient) {
        patient.addPatientHistory(patientHistory);
        return patientHistoryRepository.save(patientHistory);
    }

    public List<PatientHistory> getPatientHistoryById(Patient patient) {
        return patient.getPatientHistory();
    }
}
