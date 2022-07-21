package it.unict.spring.platform.serviceinterface.communication;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import java.io.FileNotFoundException;
import javax.mail.MessagingException;


public interface MailServiceInterface
{
  void sendSimpleEmail(String toAddress, String subject, String message);
  void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment, String attacchFileName) throws MessagingException, FileNotFoundException;
  void setSenderMail(String sender);
  String getSenderMail();
}
