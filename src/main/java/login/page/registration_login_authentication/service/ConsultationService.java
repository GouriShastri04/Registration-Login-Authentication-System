package login.page.registration_login_authentication.service;

import login.page.registration_login_authentication.dto.ConsultationDTO;
import login.page.registration_login_authentication.entity.Consultation;
import login.page.registration_login_authentication.repository.ConsultationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class ConsultationService {
    
    private final ConsultationRepository consultationRepository;

    public ConsultationService(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    public void scheduleConsultation(ConsultationDTO dto) {
        Consultation consultation = new Consultation();
        consultation.setName(dto.getName());
        consultation.setEmail(dto.getEmail());
        consultation.setCompany(dto.getCompany());
        consultation.setPhone(dto.getPhone());
        consultation.setMessage(dto.getMessage());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        consultation.setPreferredDate(LocalDate.parse(dto.getPreferredDate(), dateFormatter));
        consultation.setPreferredTime(LocalTime.parse(dto.getPreferredTime(), timeFormatter));

        consultationRepository.save(consultation);
    }

    //for manual deletion of consultation
    public void deleteConsultation(Long id) {
        consultationRepository.deleteById(id);
    }

}
