package seg.java.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import seg.java.CreatePDF;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class EmailController {
    public TextField emailTextbox;
    private String toEmail;
    private static final String username = "runwayredeclaration11@gmail.com";
    private static final String password = "runway123**";
    private static final String DEST = "src/main/outputs/redeclared_runway.pdf";
    private CreatePDF pdf;

    public void sendEmail(ActionEvent actionEvent) throws MessagingException, IOException {
        toEmail = emailTextbox.getText();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.googlemail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject("A runway has been redeclared");
        Multipart content = new MimeMultipart();

        /** main message **/
        MimeBodyPart text = new MimeBodyPart();
        text.setText("Find all information about the redeclaration in the attachment.");

        /** PDF attachment **/
        MimeBodyPart pdfAttachment = new MimeBodyPart();
        pdfAttachment.attachFile(DEST);

        /** adding PDF and main message to email **/
        content.addBodyPart(text);
        content.addBodyPart(pdfAttachment);

        /** content added to message **/
        message.setContent(content);

        Transport.send(message);
    }
}
