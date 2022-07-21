package it.unict.spring.application.services.communication.mail;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.Application;
import it.unict.spring.application.service.communication.CustomMailService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.mail.host}")
    private String host="";
    @Value("${spring.mail.password}")
    private String password="";
    @Value("${spring.mail.username}")      
    private String user;
    
    private final String subject=MailTest.class+Timestamp.valueOf(LocalDateTime.now()).toString();
        
    
    @BeforeEach
    //Send an email containing a timestamp, we then check the inbox for such email
    public void emailTest() throws MessagingException
    {
        String recipient = "example@example.com";      
        mailService.sendSimpleEmail(mailService.getSenderMail(), subject, recipient);        
    }
    
    @Test
    //we check the inbox to find the email with the given timestamp
    public void read() throws NoSuchProviderException, MessagingException
    {
      Properties mailProps = new Properties();
      mailProps.setProperty("mail.transport.protocol","smtp");
      mailProps.setProperty("mail.smtp.auth","true");
      mailProps.setProperty("mail.smtp.starttls.enable","true");
      mailProps.setProperty("mail.debug","false");
      Session emailSession = Session.getDefaultInstance(mailProps);
  
      //create the POP3 store object and connect with the pop server
      Store store = emailSession.getStore("imaps");
      store.connect(host, user, password);
      //create the folder object and open it
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_ONLY);
      // retrieve the messages from the folder in an array and print it
      Message[] messages = emailFolder.getMessages(); 
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
    }
    
    
}
