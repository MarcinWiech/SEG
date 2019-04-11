package seg.java;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import seg.java.models.Runway;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreatePDF {
    private static final String username = "runwayredeclaration11@gmail.com";
    private static final String password = "runway123**";
    public static final String DEST = "src/main/outputs/redeclared_runway.pdf";
    private RedeclarationComputer redeclarationComputer;
    private Runway runway;
    PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

    public CreatePDF(RedeclarationComputer redeclarationComputer, Runway runway) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        this.redeclarationComputer = redeclarationComputer;
        this.runway = runway;
        createPDF(DEST);
    }

    public void createPDF(String DEST) throws IOException {
        //creating the PDF
        FileOutputStream fos = new FileOutputStream(DEST);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        //adding all components to the page
        document.add(pdfTitle());
        document.add(redeclaredRunway());
        document.add(valuesTableTitle("Runway Values"));
        document.add(createRunwayTable());
        document.add(valuesTableTitle("Obstacle Values"));
        document.add(createObstacleTable());
        document.add(valuesTableTitle("Calculations"));
        document.add(calculationBreakdown("TORA: ", redeclarationComputer.getToraBD()));
        document.add(calculationBreakdown("TODA: ", redeclarationComputer.getTodaBD()));
        document.add(calculationBreakdown("ASDA: ", redeclarationComputer.getAsdaBD()));
        document.add(calculationBreakdown("LDA: ", redeclarationComputer.getLdaBD()));
        document.add(valuesTableTitle("Diagrams"));
        document.close();
    }

    public Paragraph calculationBreakdown(String name, String value) {
        Text runwayText = new Text(name);
        runwayText
                .setFont(font)
                .setFontSize(13);
        Paragraph paragraph = new Paragraph();
        paragraph.add(runwayText);
        paragraph.add(value);
        paragraph.add("\n");

        return paragraph;
    }

    public Paragraph pdfTitle() {
        Text title = new Text("Runway Redeclaration");
        title.setFont(font).setFontSize(20);
        Paragraph titleParagraph = new Paragraph();
        titleParagraph.add(title);

        return titleParagraph;
    }

    public Paragraph redeclaredRunway() {
        Text runwayText = new Text("Redeclared Runway: ");
        runwayText
                .setFont(font)
                .setFontSize(13);
        Text runwayNameText = new Text(runway.getName()).setFontSize(13);
        Paragraph paragraph = new Paragraph();
        paragraph.add(runwayText);
        paragraph.add(runwayNameText);
        paragraph.add("\n");

        return paragraph;
    }

    public Paragraph valuesTableTitle(String title) {
        Text runwayTableTitle = new Text(title);
        runwayTableTitle
                .setFont(font)
                .setUnderline()
                .setFontSize(13);

        Paragraph paragraph = new Paragraph();
        paragraph.add("\n");
        paragraph.add(runwayTableTitle);
        paragraph.add("\n");

        return paragraph;
    }

    public Table createObstacleTable() {
        float [] pointColumnWidths = {150F, 150F, 150F, 150F};
        Table table = new Table(pointColumnWidths);

        // Adding cells to the table
        table.addCell(new Cell().add("HEIGHT").setFont(font));
        table.addCell(new Cell().add("XL POSITION").setFont(font));
        table.addCell(new Cell().add("XR POSITION").setFont(font));
        table.addCell(new Cell().add("THRESHOLD").setFont(font));
        table.addCell(new Cell().add(String.valueOf(redeclarationComputer.getObstacleHeight())));
        table.addCell(new Cell().add(String.valueOf(redeclarationComputer.getObstacleXL())));
        table.addCell(new Cell().add(String.valueOf(redeclarationComputer.getObstacleXR())));
        table.addCell(new Cell().add(String.valueOf(redeclarationComputer.getObstacleY())));
        return table;
    }

    public Table createRunwayTable() {
        float [] pointColumnWidths = {150F, 100F, 100F, 100F, 100F, 100F};
        Table table = new Table(pointColumnWidths);

        // Adding cells to the table
        table.addCell(new Cell().add(""));
        table.addCell(new Cell().add("TORA").setFont(font));
        table.addCell(new Cell().add("TODA").setFont(font));
        table.addCell(new Cell().add("ASDA").setFont(font));
        table.addCell(new Cell().add("LDA").setFont(font));
        table.addCell(new Cell().add("THRESHOLD").setFont(font));
        table.addCell(new Cell().add("INITIAL VALUES").setFont(font));
        table.addCell(new Cell().add(String.valueOf(runway.getTora())));
        table.addCell(new Cell().add(String.valueOf(runway.getToda())));
        table.addCell(new Cell().add(String.valueOf(runway.getAsda())));
        table.addCell(new Cell().add(String.valueOf(runway.getLda())));
        table.addCell(new Cell().add(String.valueOf(runway.getThreshold())));
        table.addCell(new Cell().add("NEW VALUES").setFont(font));
        table.addCell(new Cell().add(String.valueOf(redeclarationComputer.getTora())));
        table.addCell(new Cell().add(String.valueOf(redeclarationComputer.getToda())));
        table.addCell(new Cell().add(String.valueOf(redeclarationComputer.getAsda())));
        table.addCell(new Cell().add(String.valueOf(redeclarationComputer.getLda())));
        table.addCell(new Cell().add(String.valueOf(runway.getThreshold())));

        return table;
    }



    /**
     public void sendEmail() throws MessagingException, IOException {
     /** TO BE REPLACED WITH TEXTBOX EMAIL
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

     MimeMessage message = new MimeMessage(session);
     try {
     message.setFrom(new InternetAddress(username));
     message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
     message.setSubject("A runway has been redeclared");

     Multipart emailContent = new MimeMultipart();

     /** Main message
     MimeBodyPart text = new MimeBodyPart();
     text.setText("Find all information about the redeclaration in the attachment.");

     /** PDF attachment
     MimeBodyPart pdfAttachment = new MimeBodyPart();
     pdfAttachment.attachFile(DEST);

     /** adding PDF and main message to email *
     emailContent.addBodyPart(text);
     emailContent.addBodyPart(pdfAttachment);

     /** Attach multipart to message **
     message.setContent(emailContent);

     Transport.send(message);
     } catch (MessagingException e) {
     e.printStackTrace();
     }

     }
     **/
}
