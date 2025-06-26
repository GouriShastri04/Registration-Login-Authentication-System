/*
package login.page.registration_login_authentication.controller;

import login.page.registration_login_authentication.service.UserService;
import login.page.registration_login_authentication.entity.User;
import login.page.registration_login_authentication.model.LoginRequest;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("session")
@RestController
@RequestMapping("/auth")
public class SessionAuthController {

    private final UserService userService;

    public SessionAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody LoginRequest loginRequest) {
    User user = new User();
    user.setUsername(loginRequest.getUsername());
    user.setPassword(loginRequest.getPassword());

    user.setName("user5");
    user.setNumber(7845963215L);

    userService.saveUser(user); 
    return ResponseEntity.ok("User registered successfully.");
}


}
*/
