package com.multiClinic.springboot_multi_clinic.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient getPatientById(int id) {
        return patientRepository.findById(id).get();
    }

    public String deletePatient(int patientID) {
        try{
            patientRepository.deleteById(patientID);
            return "Patient deleted successfully";
        } catch (Exception e) {
            return "Patient not found";
        }


    }
}
