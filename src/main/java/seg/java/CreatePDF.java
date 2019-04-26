package seg.java;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import seg.java.models.Runway;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreatePDF {
    public static final String DEST = "src/main/outputs/redeclared_runway.pdf";
    private PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
    private RedeclarationComputer redeclarationComputer;
    private Runway runway;
    private File file;
    private Image sideOnImage;
    private Image topDownImage;

    public CreatePDF(RedeclarationComputer redeclarationComputer, Runway runway, File file) throws IOException {
        this.redeclarationComputer = redeclarationComputer;
        this.runway = runway;

        sideOnImage =  new Image(ImageDataFactory.create("src/main/outputs/sideOnImage.png"));
        topDownImage = new Image(ImageDataFactory.create("src/main/outputs/topDownImage.png"));

        if (file == null) {
            this.file = new File(DEST);
        } else {
            this.file = file;
        }

        this.file.getParentFile().mkdirs();
        createPdf();
    }

    public void createPdf() throws IOException {
        //creating the PDF
        FileOutputStream fos = new FileOutputStream(file);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        createDocument(document);
    }

    public void createDocument(Document document) {
        //adding all components to the page
        document.setBottomMargin(0);
        document.add(pdfTitle());
        document.add(redeclaredRunwayText());
        document.add(valuesTableTitle("Runway Values"));
        document.add(createRunwayTable());
        document.add(valuesTableTitle("Obstacle Values"));
        document.add(createObstacleTable());
        document.add(valuesTableTitle("Calculations"));
        document.add(calculationBreakdown("TORA: ", redeclarationComputer.getToraBD()));
        document.add(calculationBreakdown("TODA: ", redeclarationComputer.getTodaBD()));
        document.add(calculationBreakdown("ASDA: ", redeclarationComputer.getAsdaBD()));
        document.add(calculationBreakdown("LDA: ", redeclarationComputer.getLdaBD()));
        document.add(new AreaBreak());
        document.add(valuesTableTitle("Diagrams"));
        document.add(new Paragraph("Top down view:"));
        topDownImage.scaleToFit(525,525);
        document.add(topDownImage);
        sideOnImage.scaleToFit(525,525);
        document.add(new Paragraph("Side on view:"));
        document.add(sideOnImage);
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

    public Paragraph redeclaredRunwayText() {
        Text runwayText = new Text("Redeclared Runway: ");
        runwayText
                .setFont(font)
                .setFontSize(13);
        Text runwayNameText = new Text(runway.getName()).setFontSize(13);
        Paragraph paragraph = new Paragraph();
        paragraph.add(runwayText);
        paragraph.add(runwayNameText);

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
}
