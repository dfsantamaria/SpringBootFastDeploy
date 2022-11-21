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
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Disabled
public class MailTest 
{  
    @SpyBean
    private CustomMailService mailService;        
    
    private final String subject=MailTest.class+"-"+Timestamp.valueOf(LocalDateTime.now()).toString();
        
    
    @BeforeAll
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
      Message[] messages = mailService.getInbox(); //getInbox("Inbox")
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
