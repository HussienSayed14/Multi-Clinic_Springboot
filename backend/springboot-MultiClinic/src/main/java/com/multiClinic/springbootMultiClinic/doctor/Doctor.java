package com.multiClinic.springbootMultiClinic.doctor;

import com.multiClinic.springbootMultiClinic.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Doctor implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }



}
