package com.multiClinic.springbootMultiClinic.doctor.services;


import com.multiClinic.springbootMultiClinic.doctor.Doctor;
import com.multiClinic.springbootMultiClinic.doctor.repository.DoctorRepository;
import com.multiClinic.springbootMultiClinic.patient.Patient;
import com.multiClinic.springbootMultiClinic.patient.PatientService;
import com.multiClinic.springbootMultiClinic.patientHistory.PatientHistory;
import com.multiClinic.springbootMultiClinic.patientHistory.PatientHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientService patientService;
    @Autowired
    PatientHistoryService patientHistoryService;


    public List<Patient> getAllPatients(int id){
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.get().getPatients();

    }

    public Patient addPatient(Patient patient, int doctorID) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        doctor.get().addPatient(patient);
        return patientService.addPatient(patient);


    }

    public Patient getPatientById(int patientID,int doctorID) {
        Patient patient = patientService.getPatientById(patientID);
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        if(doctor.get().getPatients().contains(patient)){
            return patient;
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view this patient");
        }

    }

    public String deletePatientById(int patientID, int doctorID) {
        Patient patient = patientService.getPatientById(patientID);
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        if(doctor.get().getPatients().contains(patient)){
            return patientService.deletePatient(patientID);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to delete this patient");
        }

    }

    public PatientHistory addPatientHistory(PatientHistory patientHistory, int doctorID, int patientID) {
        Patient patient = patientService.getPatientById(patientID);
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        patientHistory.setDate(new Date());
        if(doctor.get().getPatients().contains(patient)){
            return patientHistoryService.addPatientHistory(patientHistory,patient);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to add history for this patient");
        }

    }


    public List<PatientHistory> getPatientHistoryById(int doctorID, int patientID) {
        Patient patient = patientService.getPatientById(patientID);
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        if(doctor.get().getPatients().contains(patient)){
            return patientHistoryService.getPatientHistoryById(patient);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view history for this patient");
        }
    }

    public List<Patient> searchPatientsByName(int doctorID, String name) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        return doctor.get().getPatientsByName(name);
    }
}
