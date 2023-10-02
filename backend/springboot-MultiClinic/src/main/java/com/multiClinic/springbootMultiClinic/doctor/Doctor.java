package com.multiClinic.springbootMultiClinic.doctor;

import com.multiClinic.springbootMultiClinic.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Doctor {

    @Id
    @GeneratedValue
    private int id;
    private String fullName;
    @Column(unique=true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Patient> patients;


    public  void addPatient(Patient patient){
        patients.add(patient);
        patient.setDoctor(this);
    }

    public List<Patient> getPatientsByName(String name) {
        name = name.toLowerCase();
        List<Patient> searchedPatients = new ArrayList<>();
        for(Patient patient : this.patients){
            if(patient.getFullName().toLowerCase().contains(name)){
                searchedPatients.add(patient);
            }
        }
        return searchedPatients;
    }
}
