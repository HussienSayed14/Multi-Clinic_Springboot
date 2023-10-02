package com.multiClinic.springbootMultiClinic.patient;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.multiClinic.springbootMultiClinic.doctor.Doctor;
import com.multiClinic.springbootMultiClinic.patientHistory.PatientHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Patient {

    @Id
    @GeneratedValue
    private int id;
    private String fullName;
    private String gender;
    private int age;
    private String moreInfo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PatientHistory> patientHistory;

    public void addPatientHistory(PatientHistory history){
        patientHistory.add(history);
        history.setPatient(this);
    }

}
