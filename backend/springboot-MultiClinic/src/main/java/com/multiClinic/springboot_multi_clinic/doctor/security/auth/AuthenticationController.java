package com.multiClinic.springboot_multi_clinic.doctor.security.auth;

import com.multiClinic.springboot_multi_clinic.doctor.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<Doctor> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthenticationRequest request) {
        return authService.authenticate(request);

    }

}

