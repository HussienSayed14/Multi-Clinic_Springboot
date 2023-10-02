package com.multiClinic.springbootMultiClinic.doctor.controllers;

import com.multiClinic.springbootMultiClinic.doctor.Doctor;
import com.multiClinic.springbootMultiClinic.doctor.security.config.JwtService;
import com.multiClinic.springbootMultiClinic.doctor.services.DoctorService;
import com.multiClinic.springbootMultiClinic.patient.Patient;
import com.multiClinic.springbootMultiClinic.patient.PatientRepository;
import com.multiClinic.springbootMultiClinic.patient.PatientService;
import com.multiClinic.springbootMultiClinic.patientHistory.PatientHistory;
import com.multiClinic.springbootMultiClinic.patientHistory.PatientHistoryService;
import io.jsonwebtoken.Claims;
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

     @GetMapping("/hello")
     public int hello(@RequestHeader("Authorization") String token) {
         String jwtToken = token.substring(7);
         int loggedInId = jwtService.extractID(jwtToken);
         //System.out.println("Logged In ID is: "+loggedInId);
            return loggedInId;
        }


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
