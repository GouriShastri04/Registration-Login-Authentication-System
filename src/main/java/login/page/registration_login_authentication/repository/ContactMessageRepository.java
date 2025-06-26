package login.page.registration_login_authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import login.page.registration_login_authentication.entity.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    
}
