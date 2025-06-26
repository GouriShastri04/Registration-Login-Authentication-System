package login.page.registration_login_authentication.repository;

import login.page.registration_login_authentication.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long>{
} 
