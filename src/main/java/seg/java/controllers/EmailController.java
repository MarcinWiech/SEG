package seg.java.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class EmailController {
    public TextField emailTextbox;
    private String username;
    private String password;
    private String fromEmail;
    private String toEmail;

    public void sendEmail(ActionEvent actionEvent) throws MessagingException, IOException {
        username = "runwayredeclaration@yahoo.com";
        password = "runway123";
        fromEmail = "florence.marshall@outlook.com";
        toEmail = emailTextbox.getText();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(fromEmail);
        msg.setFrom(new InternetAddress(fromEmail));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        msg.setSubject("Subject Line");

        Multipart emailContent = new MimeMultipart();

        //Text body part
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText("My multipart text");

        //Attachment body part.
        MimeBodyPart pdfAttachment = new MimeBodyPart();
        pdfAttachment.attachFile("/home/parallels/Documents/docs/javamail.pdf");

        //Attach body parts
        emailContent.addBodyPart(textBodyPart);
        emailContent.addBodyPart(pdfAttachment);

        //Attach multipart to message
        msg.setContent(emailContent);

        Transport.send(msg);
        System.out.println("Sent message");
    }
}
