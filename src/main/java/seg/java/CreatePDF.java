package seg.java;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CreatePDF {
    private String fromEmail, username, password, toEmail;

    public static final String DEST = "src/main/outputs/redeclared_runway.pdf";

    public static void main(String[] args) throws IOException, MessagingException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CreatePDF().createPDF(DEST);
        new CreatePDF().sendEmail();
    }

    public void createPDF(String DEST) throws IOException {
        //creating the PDF
        FileOutputStream fos = new FileOutputStream(DEST);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        //creating the title
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        Text text = new Text("Runway Redeclaration");
        text.setFont(font);
        Paragraph title = new Paragraph();
        title.add(text);



        //adding all components to the page
        document.add(title);
        document.close();
    }

    public void sendEmail() throws MessagingException, IOException {
       // password = "runway123**";
        //String user = "runwayredeclaration11";
       // fromEmail = "runwayredeclaration11@gmail.com";

        final String username = "runwayredeclaration11@gmail.com";
        final String password = "runway123**";

        //authentication info

        String fromEmail = "runwayredeclaration11@gmail.com";
        String toEmail = "runwayredeclaration11@gmail.com";

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

        //Start our mail message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject("Subject Line");

            Multipart emailContent = new MimeMultipart();

            //Text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("My multipart text");

            //Attachment body part.
            //MimeBodyPart pdfAttachment = new MimeBodyPart();
           // pdfAttachment.attachFile("/home/parallels/Documents/docs/javamail.pdf");

            //Attach body parts
            emailContent.addBodyPart(textBodyPart);
           // emailContent.addBodyPart(pdfAttachment);

            //Attach multipart to message
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
