package com.multiClinic.springbootMultiClinic.doctor.controllers;

import com.multiClinic.springbootMultiClinic.doctor.Doctor;
import com.multiClinic.springbootMultiClinic.doctor.services.DoctorService;
import com.multiClinic.springbootMultiClinic.patient.Patient;
import com.multiClinic.springbootMultiClinic.patient.PatientRepository;
import com.multiClinic.springbootMultiClinic.patient.PatientService;
import com.multiClinic.springbootMultiClinic.patientHistory.PatientHistory;
import com.multiClinic.springbootMultiClinic.patientHistory.PatientHistoryService;
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


     @PostMapping("/addPatient/{doctorID}")
     public Patient addPatient(@RequestBody Patient patient, @PathVariable int doctorID) {
          return doctorService.addPatient(patient, doctorID);
     }

     @GetMapping("/allPatients/{id}")
     public List<Patient> getAllPatients(@PathVariable int id) {
          return doctorService.getAllPatients(id);
     }

     @GetMapping("/getPatientById/{doctorID}/{patientID}")
     public Patient getPatientById(@PathVariable int patientID, @PathVariable int doctorID) {
          return doctorService.getPatientById(patientID, doctorID);

     }

     @DeleteMapping("/deletePatient/{doctorID}/{patientID}")
        public void deletePatientById(@PathVariable int patientID, @PathVariable int doctorID) {
            doctorService.deletePatientById(patientID, doctorID);
        }

        @PostMapping("/addPatientHistory/{doctorID}/{patientID}")
        public PatientHistory addPatientHistory(@RequestBody PatientHistory patientHistory, @PathVariable int doctorID, @PathVariable int patientID) {
            return doctorService.addPatientHistory(patientHistory, doctorID, patientID);
        }

        @GetMapping("/getPatientHistoryById/{doctorID}/{patientID}")
        public List<PatientHistory> getPatientHistoryById(@PathVariable int doctorID, @PathVariable int patientID) {
            return doctorService.getPatientHistoryById(doctorID, patientID);
        }

        @GetMapping("/searchPatientsByName/{doctorID}/{name}")
        public List<Patient> searchPatientsByName(@PathVariable int doctorID, @PathVariable String name) {
            return doctorService.searchPatientsByName(doctorID, name);
        }


}
