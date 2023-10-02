package com.multiClinic.springbootMultiClinic.doctor.security.auth;

import com.multiClinic.springbootMultiClinic.doctor.Doctor;
import com.multiClinic.springbootMultiClinic.doctor.repository.DoctorRepository;
import com.multiClinic.springbootMultiClinic.doctor.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponses register(RegisterRequest request) {
        var doctor = Doctor.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();


        doctorRepository.save(doctor);
        var jwtToken = jwtService.generateToken(doctor);
        return AuthenticationResponses.builder()
                .token(jwtToken)
                .build();

        
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
