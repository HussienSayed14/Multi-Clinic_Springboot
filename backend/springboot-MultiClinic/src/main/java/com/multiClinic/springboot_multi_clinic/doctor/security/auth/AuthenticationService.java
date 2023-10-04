package com.multiClinic.springboot_multi_clinic.doctor.security.auth;

import com.multiClinic.springboot_multi_clinic.doctor.Doctor;
import com.multiClinic.springboot_multi_clinic.doctor.repository.DoctorRepository;
import com.multiClinic.springboot_multi_clinic.doctor.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public Doctor register(RegisterRequest request) {

        if(doctorRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        var doctor = Doctor.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();



        return doctorRepository.save(doctor);

        
    }

    public String authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var doctor = doctorRepository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(doctor);
        return jwtToken;
    }
}
