package login.page.registration_login_authentication.service;

import login.page.registration_login_authentication.entity.User;
import login.page.registration_login_authentication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//for email
import login.page.registration_login_authentication.entity.VerificationToken;
import login.page.registration_login_authentication.repository.VerificationTokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public boolean userExists(String email) {
        return repo.findByEmail(email).isPresent();
    }

   public void saveUser(User user) {
    try {
        System.out.println("Before encoding password: " + user.getPassword());
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println("After encoding password: " + user.getPassword());
        repo.save(user);
        System.out.println("User saved in DB successfully.");

        sendVerification(user);

    } catch (Exception e) {
        System.err.println("Error saving user: " + e.getMessage());
        e.printStackTrace();
    }
}

public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        tokenRepository.save(verificationToken);
    }

    public void sendVerification(User user) {
        String token = UUID.randomUUID().toString();
        createVerificationToken(user, token);
        System.out.println("Generated token: " + token);
        emailService.sendVerificationEmail(user.getUsername(), token);
    }

    public User findByUsername(String email) {
        return repo.findByEmail(email).orElse(null);
    }

    public void saveVerifiedUser(User user) {
    repo.save(user); 
}
}
