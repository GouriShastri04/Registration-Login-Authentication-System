package login.page.registration_login_authentication.entity;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(nullable = false, unique = true, length = 100)
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Column(nullable = false, length = 100)
    private String name;

    @Pattern(regexp = "\\d{10}", message = "Number must be exactly 10 digits")
    @Column(nullable = false, length = 10)
    private String number;

    @Column(nullable = false)
    private String password;
    
    //Field for verification
    @Column(nullable = false)
    private boolean enabled = false;

    public User() {} //No-args constructor

    // Getters and Setters
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    //for email
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    //for email
    public boolean getEnabled() {
        return enabled;
    }

    // UserDetails interface methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
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
        return enabled;
    }

}
