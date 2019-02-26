package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.*;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CanvasDrawer
{
    // Fields #################################################################################################################################

    private Canvas topDownCanvas;
    private Canvas sideOnCanvas;

    private GraphicsContext gc;

    // Constructors #################################################################################################################################

    public CanvasDrawer()
    {

    }

    public CanvasDrawer(Canvas topDownCanvas, Canvas sideOnCanvas)
    {
        this.topDownCanvas = topDownCanvas;
        this.sideOnCanvas = sideOnCanvas;
    }

    // Drawing methods #################################################################################################################################

    public void drawTopDownCanvas()
    {
        gc = topDownCanvas.getGraphicsContext2D();

        double canvasWidth = topDownCanvas.getWidth();
        double canvasHeight = topDownCanvas.getHeight();
        double xCentre = canvasWidth / 2;
        double yCentre = canvasHeight /2;


        gc.clearRect(0, 0, canvasWidth, canvasHeight);


        // Layers get set
            Rectangle grassRect = new Rectangle(0.5 * canvasWidth - (canvasHeight - 20), 10, 2 * (canvasHeight - 20), canvasHeight - 20);
            Rectangle runwayStripRect = new Rectangle(0.5 * canvasWidth - (canvasHeight - 20) + 10, 20, 2 * (canvasHeight - 20) - 20, canvasHeight - 40);
            Rectangle clearedGradedRect1 = new Rectangle(0.5 * canvasWidth - (canvasHeight - 20) + 10, 110, 2 * (canvasHeight - 20) - 20, canvasHeight - 220);
            Rectangle clearedGradedRect2 = new Rectangle(0.5 * canvasWidth - (canvasHeight - 20) + 160, 70, 2 * (canvasHeight - 20) - 320, canvasHeight - 140);
            Rectangle runwayRect = new Rectangle(0.5 * canvasWidth - (canvasHeight - 20) + 70, 150, 2 * (canvasHeight - 20) - 140, canvasHeight - 300);


        // Layers are drawn - from the bottom up
            gc.setFill(Color.GREEN);
            fillRect(gc, grassRect);
            gc.setFill(Color.MEDIUMPURPLE);
            fillRect(gc, runwayStripRect);
            gc.setFill(Color.DARKBLUE);
            fillRect(gc, clearedGradedRect1);
            fillRect(gc, clearedGradedRect2);
            gc.setFill(Color.GRAY);
            fillRect(gc, runwayRect);


        // The runway centerline gets drawn here
            double x = (0.5 * canvasWidth - (canvasHeight - 20) + 160);
            double y = 0.5 * canvasHeight - 1;
            gc.setFill(Color.WHITE);
            while (x < canvasWidth - (0.5 * canvasWidth - (canvasHeight - 20) + 160))
            {
                Rectangle whiteRect = new Rectangle(x, y, 12, 2);
                fillRect(gc, whiteRect);
                x += 24;
            }


        // The next 2 while draw the sets of lines that look like crosswalks
            y = 154;
            gc.setFill(Color.WHITE);
            while(y + 4 < 150 + (canvasHeight - 300) )
            {
                x = 0.5 * canvasWidth - (canvasHeight - 20) + 80;
                Rectangle whiteRect = new Rectangle(x, y, 34, 2);
                fillRect(gc, whiteRect); // left one gets drawn
                x = (0.5 * canvasWidth - (canvasHeight - 20) + 70) + (2 * (canvasHeight - 20) - 140) - 44;
                whiteRect = new Rectangle(x, y, 34, 2);
                fillRect(gc, whiteRect); // right one gets drawn
                y += 4;
            }

         // gc.scale(2,2); //can be used for zoom later on
    }

    public void drawSideOnCanvas()
    {
        // gc = sideOnCanvas.getGraphicsContext2D();
    }

    public void drawBothCanvases()
    {

    }

    // Helper methods #################################################################################################################################

    private void fillRect(GraphicsContext gc, Rectangle rect)
    {
        gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    // Setters #################################################################################################################################

    public void setCanvases(Canvas topDownCanvas, Canvas sideOnCanvas)
    {
        this.topDownCanvas = topDownCanvas;
        this.sideOnCanvas = sideOnCanvas;
    }
}
