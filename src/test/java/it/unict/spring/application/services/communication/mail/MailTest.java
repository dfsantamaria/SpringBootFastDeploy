package it.unict.spring.application.services.communication.mail;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.Application;
import it.unict.spring.application.service.communication.CustomMailService;
import javax.mail.MessagingException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ComponentScan(basePackages = {"it.unict.spring.application.service.*", "it.unict.spring.application.configuration.*"})
@SpringBootTest(classes=Application.class, properties = {"spring.main.allow-bean-definition-overriding=true"})

public class MailTest 
{  
    @SpyBean
    private CustomMailService mailService;    

    @Disabled
    @Test
    public void emailTest() throws MessagingException
    {
        String recipient = "example@example.com";      
        mailService.sendSimpleEmail(mailService.getSenderMail(), "test subject", recipient );        
    }
    
    
}
