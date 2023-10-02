package com.multiClinic.springbootMultiClinic.doctor.controllers;


import com.multiClinic.springbootMultiClinic.doctor.Doctor;
import com.multiClinic.springbootMultiClinic.doctor.services.DoctorAuthService;
import com.multiClinic.springbootMultiClinic.doctor.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    DoctorService doctorService;
    @Autowired
    DoctorAuthService doctorAuthService;

    @PostMapping("/register")
    public Doctor registerDoctor(@RequestBody Doctor doctor){

        return doctorAuthService.registerDoctor(doctor);

    }
}
