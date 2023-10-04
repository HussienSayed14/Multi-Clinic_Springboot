package com.multiClinic.springboot_multi_clinic.patientHistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.multiClinic.springboot_multi_clinic.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class PatientHistory {
    @Id
    @GeneratedValue
    private int id;
    private String disease;
    private String medicine;
    private Date date;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;
}
