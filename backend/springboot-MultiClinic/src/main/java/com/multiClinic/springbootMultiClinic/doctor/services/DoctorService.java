package com.multiClinic.springbootMultiClinic.doctor.services;


import com.multiClinic.springbootMultiClinic.doctor.Doctor;
import com.multiClinic.springbootMultiClinic.doctor.repository.DoctorRepository;
import com.multiClinic.springbootMultiClinic.doctor.security.config.JwtService;
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
    @Autowired
    JwtService jwtService;

    public int extractIdFromToken(String token){
        String jwtToken = token.substring(7);
        int loggedInId = jwtService.extractID(jwtToken);

        return loggedInId;

    }

    public List<Patient> getAllPatients(String token){
        int id = extractIdFromToken(token);
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.get().getPatients();

    }

    public Patient addPatient(Patient patient, String token) {
        int doctorID = extractIdFromToken(token);
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        doctor.get().addPatient(patient);
        return patientService.addPatient(patient);


    }

    public Patient getPatientById(int patientID,String token) {
        Patient patient = patientService.getPatientById(patientID);
        int doctorID = extractIdFromToken(token);
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        if(doctor.get().getPatients().contains(patient)){
            return patient;
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view this patient");
        }

    }

    public String deletePatientById(int patientID, String token) {
        Patient patient = patientService.getPatientById(patientID);
        int doctorID = extractIdFromToken(token);
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        if(doctor.get().getPatients().contains(patient)){
            return patientService.deletePatient(patientID);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to delete this patient");
        }

    }

    public PatientHistory addPatientHistory(PatientHistory patientHistory,String token, int patientID) {
        Patient patient = patientService.getPatientById(patientID);
        int doctorID = extractIdFromToken(token);
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        patientHistory.setDate(new Date());
        if(doctor.get().getPatients().contains(patient)){
            return patientHistoryService.addPatientHistory(patientHistory,patient);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to add history for this patient");
        }

    }


    public List<PatientHistory> getPatientHistoryById(String token, int patientID) {
        Patient patient = patientService.getPatientById(patientID);
        int doctorID = extractIdFromToken(token);
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        if(doctor.get().getPatients().contains(patient)){
            return patientHistoryService.getPatientHistoryById(patient);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view history for this patient");
        }
    }

    public List<Patient> searchPatientsByName(String token, String name) {
        int doctorID = extractIdFromToken(token);
        Optional<Doctor> doctor = doctorRepository.findById(doctorID);
        return doctor.get().getPatientsByName(name);
    }
}
