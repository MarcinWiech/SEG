package seg.java;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CreatePDF {
    public static final String DEST = "src/main/outputs/redeclared_runway.pdf";

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CreatePDF().createPDF(DEST);
    }

    public void createPDF(String DEST) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(DEST);
        PdfWriter writer = new PdfWriter(fos);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        document.add(new Paragraph("Wagwarn"));

        document.close();
    }
}
