package it.unict.spring.platform.services.communication.mail;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.Application;
import it.unict.spring.platform.service.communication.CustomMailService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ComponentScan(basePackages = {"it.unict.spring.application.service.*", "it.unict.spring.application.configuration.*"})
@SpringBootTest(classes=Application.class, properties = {"spring.main.allow-bean-definition-overriding=true"})

@Disabled
public class MailTest 
{  
    @SpyBean
    private CustomMailService mailService;        
    
    private final String subject=MailTest.class+"-"+Timestamp.valueOf(LocalDateTime.now()).toString();
        
    
    @BeforeEach
    //Send myself an email  containing a timestamp, we then check the inbox for such email
    public void emailTest() throws MessagingException
    {
        String recipient = "example@example.com";      
        mailService.sendSimpleEmail(mailService.getSenderMail(), subject, recipient);        
    }
    
    @Test
    //we check the inbox to find the email with the given timestamp
    public void read() throws NoSuchProviderException, MessagingException
    {     
      Message[] messages = mailService.getInbox(); 
      Message found=null;
      for(Message m: messages)
      {
        if(m.getSubject().equalsIgnoreCase(subject))
        {
            found=m;
            break;
        }
      }
      assertNotNull(found);
      mailService.closeReader();
    }
    
    
}
