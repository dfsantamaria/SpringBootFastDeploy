package it.unict.spring.platform.service.communication;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.serviceinterface.communication.MailServiceInterface;
import it.unict.spring.platform.utility.communication.JavaMailReader;
import java.io.FileNotFoundException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Service
public class CustomMailService implements MailServiceInterface
{
 private static final Logger applogger = LoggerFactory.getLogger(CustomMailService.class);  
 @Autowired
 public JavaMailSender emailSender;
 @Autowired
 public JavaMailReader emailReader;

 @Value("${mailsender.mail.username}")
 private String senderMail=""; 
 

  public void closeReader() throws MessagingException
  {
    emailReader.getStore().close();
  }
 
  public Message[] getInbox() throws MessagingException
    {        
      emailReader.connect();
      Folder emailFolder = emailReader.getStore().getFolder("INBOX");
      emailFolder.open(Folder.READ_ONLY);
      // retrieve the messages from the folder in an array and print it
      Message[] messages=emailFolder.getMessages().clone();       
      return messages;
    }
 
 @Override
 public void sendSimpleEmail(String toAddress, String subject, String message) {

  SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
  simpleMailMessage.setTo(toAddress);
  simpleMailMessage.setSubject(subject);
  simpleMailMessage.setText(message);
  emailSender.send(simpleMailMessage);
  applogger.info("sending to "+ toAddress +" - " + subject +" - "+ message);
 }
 
 @Override
 public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment, String attachFileName) throws MessagingException, FileNotFoundException {

  MimeMessage mimeMessage = emailSender.createMimeMessage();
  MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
  messageHelper.setTo(toAddress);
  messageHelper.setSubject(subject);
  messageHelper.setText(message);
  FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
  messageHelper.addAttachment(attachFileName, file);
  emailSender.send(mimeMessage);
 }
 
 @Override
 public String getSenderMail()
 {
  return senderMail;
 }
 
 @Override
 public void setSenderMail(String sender)
 {
   this.senderMail=sender;
 } 
}