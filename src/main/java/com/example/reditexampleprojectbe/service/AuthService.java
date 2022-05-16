package com.example.reditexampleprojectbe.service;

import com.example.reditexampleprojectbe.dto.RegisterRequest;
import com.example.reditexampleprojectbe.exception.SpringRedditException;
import com.example.reditexampleprojectbe.model.NotificationEmail;
import com.example.reditexampleprojectbe.model.User;
import com.example.reditexampleprojectbe.model.VerificationToken;
import com.example.reditexampleprojectbe.repository.UserRepository;
import com.example.reditexampleprojectbe.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail(
                "Please Activate your Account", user.getEmail(),
                "Thank you for signing up " + "please click on the below url" +
                        "to activate your account: " + "http://localhost:8080/api/auth/accountVerification/" + token));

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }


    public void verifyAccount(String token) {
       Optional<VerificationToken> verificationToken =  verificationTokenRepository.findByToken(token);
       verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
       fetchUserAndEnable(verificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
       String username =  verificationToken.getUser().getUsername();
       User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found " + username));
       user.setEnabled(true);
       userRepository.save(user);
    }
}
