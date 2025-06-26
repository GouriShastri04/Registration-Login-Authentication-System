package login.page.registration_login_authentication.service;

import login.page.registration_login_authentication.entity.User;
import login.page.registration_login_authentication.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository repo;
    public CustomUserDetailService(UserRepository repo) { this.repo = repo; }

    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {
        User user = repo.findByEmail(email)
                   .orElseThrow(() -> 
                      new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword()) 
            .roles("USER") 
            .build();

    }
}
