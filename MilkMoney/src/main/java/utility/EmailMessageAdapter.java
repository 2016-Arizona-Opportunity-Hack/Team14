package utility;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;

/**
 * Created by calebripley on 10/1/16.
 */
public class EmailMessageAdapter {

  private final String EMAIL_SMTP_SERVER = "smtp.gmail.com";
  private final String EMAIL_SERVER_PORT = "465";

  private String senderEmail;
  private String senderPassword;
  private ArrayList<String> receiverEmails;
  private String emailSubject;
  private String emailBody;

  // Constructor
  public EmailMessageAdapter(String senderEmail, String senderPassword, ArrayList<String> receiverEmails, String subject, String message) {
    this.senderEmail = senderEmail;
    this.senderPassword = senderPassword;
    this.receiverEmails = receiverEmails;
    this.emailSubject = subject;
    this.emailBody = message;
  }

  // Authentication
  private class SMTPAuthenticator extends javax.mail.Authenticator {

    public PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(senderEmail, senderPassword);
    }
  }

  // Send the email message
  public void sendEmail() {

    Properties props = new Properties();
    props.put("mail.smtp.user", senderEmail);
    props.put("mail.smtp.host", EMAIL_SMTP_SERVER);
    props.put("mail.smtp.port", EMAIL_SERVER_PORT);
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.socketFactory.fallback", "true");
    props.put("mail.smtp.socketFactory.port", EMAIL_SERVER_PORT);
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

    SecurityManager security = System.getSecurityManager();

    for (int i = 0; i < receiverEmails.size(); i++) {
      try {
        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getInstance(props, auth);

        Message msg = new MimeMessage(session);
        msg.setText(emailBody);
        msg.setSubject(emailSubject);
        msg.setFrom(new InternetAddress(senderEmail));
        msg.addRecipient(Message.RecipientType.TO,
            new InternetAddress(receiverEmails.get(i)));
        Transport.send(msg);
        System.out.println("EMAIL " + (i + 1) + " SENT");
      } catch (Exception ex) {
        System.err.println("Error occurred while sending.");
        ex.printStackTrace();
      }
    }
  }

}

