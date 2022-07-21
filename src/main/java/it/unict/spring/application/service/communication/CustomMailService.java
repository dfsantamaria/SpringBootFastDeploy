package it.unict.spring.application.service.communication;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.serviceinterface.communication.MailServiceInterface;
import java.io.FileNotFoundException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@Service
@PropertySource(value = "classpath:application.properties")
public class CustomMailService implements MailServiceInterface
{

 @Autowired
 public JavaMailSender emailSender;

 @Value("${spring.mail.username}")
 String senderMail="";
 
 @Override
 public void sendSimpleEmail(String toAddress, String subject, String message) {

  SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
  simpleMailMessage.setTo(toAddress);
  simpleMailMessage.setSubject(subject);
  simpleMailMessage.setText(message);
  emailSender.send(simpleMailMessage);
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