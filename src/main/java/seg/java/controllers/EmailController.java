package seg.java.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seg.java.CreatePDF;
import seg.java.Notification;
import seg.java.models.Runway;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;

public class EmailController {
    public TextField emailTextbox;
    private InternetAddress toEmail;
    private static final String username = "runwayredeclaration11@gmail.com";
    private static final String password = "runway123**";
    private static final String DEST = "src/main/outputs/redeclared_runway.pdf";
    private CreatePDF pdf;
    private Runway runway;
    private Notification notification;
    private Image emailIcon;

    public EmailController() {
        notification = new Notification();
        emailIcon  = new Image("/images/email-sent.png");
    }

    public void sendEmail(ActionEvent actionEvent) throws MessagingException, IOException {
        try {
            toEmail = new InternetAddress(emailTextbox.getText());
            toEmail.validate();
            createEmail();
            Stage stage = (Stage) emailTextbox.getScene().getWindow();
            stage.close();
            notification.makeNotification("Email sent" , "Email has been successfully sent", emailIcon);
        } catch (AddressException e) {
            e.printStackTrace();
        }
    }


    public void createEmail() throws MessagingException, IOException {
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
        message.addRecipient(Message.RecipientType.TO, toEmail);
        String subject = "URGENT: Runway " + runway.getName() + " has been redeclared";
        message.setSubject(subject);
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
    public void setRunway(Runway runway) {
        this.runway = runway;
    }
}
