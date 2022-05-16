package com.example.reditexampleprojectbe.controller;


import com.example.reditexampleprojectbe.dto.LoginRequest;
import com.example.reditexampleprojectbe.dto.RegisterRequest;
import com.example.reditexampleprojectbe.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                HttpStatus.OK);
    }


    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Actived Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest){

    }
}
