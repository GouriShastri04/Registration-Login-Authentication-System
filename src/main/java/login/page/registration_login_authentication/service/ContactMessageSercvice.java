package login.page.registration_login_authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import login.page.registration_login_authentication.entity.ContactMessage;
import login.page.registration_login_authentication.repository.ContactMessageRepository;

@Service
public class ContactMessageSercvice {
    
    @Autowired
    private ContactMessageRepository repository;

    public ContactMessage saveMessage(ContactMessage message) {
        return repository.save(message);
    } 

    //we can add methods over here to get/delete messages
}
