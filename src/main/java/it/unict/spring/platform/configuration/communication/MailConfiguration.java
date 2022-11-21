package it.unict.spring.platform.configuration.communication;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.utility.communication.JavaMailReader;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration
{
    @Bean
    public JavaMailSenderImpl emailSender(@Value("${mailsender.mail.host}") String emailHost,
                                          @Value("${mailsender.mail.port}") Integer emailPort,
                                          @Value("${mailsender.mail.username}") String username,
                                          @Value("${mailsender.mail.password}") String password)
                                          
    {
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        emailSender.setHost(emailHost);
        emailSender.setPort(emailPort);
        emailSender.setUsername(username);
        emailSender.setPassword(password);
        Properties mailProps = new Properties();
        mailProps.setProperty("mail.transport.protocol","smtp");
        mailProps.setProperty("mail.smtp.auth","true");
        mailProps.setProperty("mail.smtp.starttls.enable","true");
        mailProps.setProperty("mail.debug","false");
        emailSender.setJavaMailProperties(mailProps);
        return emailSender;
    }
    
    
    @Bean
    public JavaMailReader emailReader(@Value("imaps") String protocol,
                                            @Value("${mailreceiver.mail.host}")  String host,
                                            @Value("${mailreceiver.mail.password}")  String password,
                                            @Value("${mailreceiver.mail.username}") String username) throws NoSuchProviderException, MessagingException
    {
      Properties mailProps = new Properties();
      mailProps.setProperty("mail.transport.protocol","smtp");
      mailProps.setProperty("mail.smtp.auth","true");
      mailProps.setProperty("mail.smtp.starttls.enable","true");
      mailProps.setProperty("mail.debug","false");
      Session session = Session.getDefaultInstance(mailProps);   
      Store store = session.getStore(protocol);      
      return new JavaMailReader (store, host, username, password, "Inbox");
    }
    
}