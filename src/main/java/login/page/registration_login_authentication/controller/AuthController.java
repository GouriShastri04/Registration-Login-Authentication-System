package login.page.registration_login_authentication.controller;

import login.page.registration_login_authentication.dto.ApiResponse;
import login.page.registration_login_authentication.dto.ConsultationDTO;
import login.page.registration_login_authentication.dto.loginjwt.JwtAuthRequest;
import login.page.registration_login_authentication.dto.loginjwt.JwtAuthResponse;
import login.page.registration_login_authentication.dto.UserRegistrationRequest;
import login.page.registration_login_authentication.entity.ContactMessage;
import login.page.registration_login_authentication.entity.User;
import login.page.registration_login_authentication.security.JwtUtil;
import login.page.registration_login_authentication.service.EmailService;
import login.page.registration_login_authentication.service.UserService;
import login.page.registration_login_authentication.service.ConsultationService;
import login.page.registration_login_authentication.service.ContactMessageSercvice;
import login.page.registration_login_authentication.entity.VerificationToken;
import login.page.registration_login_authentication.repository.PasswordResetTokenRepository;
import login.page.registration_login_authentication.repository.UserRepository;
import login.page.registration_login_authentication.repository.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import login.page.registration_login_authentication.model.PasswordResetToken;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//@Profile("jwt")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    //For Forgot Password
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //For Scheduling Consultation
    @Autowired
    private ConsultationService consultationService;

    //For Contact Us Message
    @Autowired
    private ContactMessageSercvice service; 

    //constructor to include AuthenticationManager and JwtUtil
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRegistrationRequest request) {
        System.out.println("Registration endpoint hit for email: " + request.getUsername());
        if (userService.userExists(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse("Email already taken", false));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // password encoded in service
        user.setName(request.getName());
        user.setNumber(request.getNumber());

        userService.saveUser(user);

        return ResponseEntity.ok(new ApiResponse("Registration successful", true));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthRequest request) {

        User user = userService.findByUsername(request.getUsername());
        if (user == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid credentials", false));
        }

        if (!user.isEnabled()) {
            return ResponseEntity
                .badRequest()
                .body(new ApiResponse("Email not verified. Please verify your email.", false));
        }

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String token = jwtUtil.generateToken(request.getUsername());

        // Return token in response
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse> verifyAccount(@RequestParam("token") String token) {
        Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid token", false));
        }

        VerificationToken verificationToken = optionalToken.get();
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(new ApiResponse("Token expired", false));
        }

        User user = verificationToken.getUser();

        user.setEnabled(true); 
        tokenRepository.delete(verificationToken);
        userService.saveVerifiedUser(user);

        return ResponseEntity.ok(new ApiResponse("Email verified successfully!", true));
    }


    @GetMapping("/home")
    public ResponseEntity<ApiResponse> home(Authentication authentication) {
        String user = authentication.getName();
        return ResponseEntity.ok(new ApiResponse("Welcome " + user + "!", true));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> payload) {
    String email = payload.get("email");

    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {
        return ResponseEntity
                .badRequest()
                .body(new ApiResponse("No account found with that email.", false));
    }

    User user = optionalUser.get();

    String token = UUID.randomUUID().toString();

    PasswordResetToken resetToken = new PasswordResetToken();
    resetToken.setToken(token);
    resetToken.setUser(user);
    resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));

    passwordResetTokenRepository.save(resetToken);

    String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + token;
    emailService.sendEmail(user.getUsername(), "Reset Your Password",
            "Click the link to reset your password: " + resetLink);

    return ResponseEntity.ok(new ApiResponse("Password reset link sent to your email.", true));
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token,
                                       @RequestParam String newPassword) {
    PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
    if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
        return ResponseEntity.badRequest().body(new ApiResponse("Invalid or expired token.", false));
    }

    User user = resetToken.getUser();
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
    passwordResetTokenRepository.delete(resetToken);

    return ResponseEntity.ok(new ApiResponse("Password has been reset successfully.", true));
    }

    @PostMapping("/consultation/schedule")
    public ResponseEntity<?> scheduleConsultation(@RequestBody ConsultationDTO dto) {
        consultationService.scheduleConsultation(dto);
        return ResponseEntity.ok("Consultation scheduled successfully!");
    }

    @DeleteMapping("/consultation/{id}")
    public ResponseEntity<String> deleteConsultation(@PathVariable Long id) {
        consultationService.deleteConsultation(id);
        return ResponseEntity.ok("Consultation deleted successfully");
    }

    @PostMapping("/contact")
    public ResponseEntity<String> submitMessage(@RequestBody ContactMessage message) {
        service.saveMessage(message);
        return ResponseEntity.ok("Message Recieved successfully");
    }
}
