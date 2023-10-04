package com.multiClinic.springboot_multi_clinic.doctor.controllers;

import com.multiClinic.springboot_multi_clinic.doctor.security.config.JwtService;
import com.multiClinic.springboot_multi_clinic.doctor.services.DoctorService;
import com.multiClinic.springboot_multi_clinic.patient.Patient;
import com.multiClinic.springboot_multi_clinic.patient.PatientService;
import com.multiClinic.springboot_multi_clinic.patientHistory.PatientHistory;
import com.multiClinic.springboot_multi_clinic.patientHistory.PatientHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

     @Autowired
     DoctorService doctorService;
     @Autowired
     PatientService patientService;
     @Autowired
     PatientHistoryService patientHistoryService;
    @Autowired
    JwtService jwtService;



     @PostMapping("/addPatient")
     public Patient addPatient(@RequestBody Patient patient, @RequestHeader("Authorization") String token) {
          return doctorService.addPatient(patient, token);
     }

     @GetMapping("/allPatients")
     public List<Patient> getAllPatients(@RequestHeader("Authorization") String token) {
          return doctorService.getAllPatients(token);
     }

     @GetMapping("/getPatientById/{patientID}")
     public Patient getPatientById(@PathVariable int patientID,@RequestHeader("Authorization") String token) {
          return doctorService.getPatientById(patientID,token);

     }

     @DeleteMapping("/deletePatient/{patientID}")
        public void deletePatientById(@PathVariable int patientID,@RequestHeader("Authorization") String token) {
            doctorService.deletePatientById(patientID, token);
        }

        @PostMapping("/addPatientHistory/{patientID}")
        public PatientHistory addPatientHistory(@RequestBody PatientHistory patientHistory,@RequestHeader("Authorization") String token, @PathVariable int patientID) {
            return doctorService.addPatientHistory(patientHistory, token, patientID);
        }

        @GetMapping("/getPatientHistoryById/{patientID}")
        public List<PatientHistory> getPatientHistoryById(@RequestHeader("Authorization") String token, @PathVariable int patientID) {
            return doctorService.getPatientHistoryById(token, patientID);
        }

        @GetMapping("/searchPatientsByName/{name}")
        public List<Patient> searchPatientsByName(@RequestHeader("Authorization") String token, @PathVariable String name) {
            return doctorService.searchPatientsByName(token, name);
        }


}
