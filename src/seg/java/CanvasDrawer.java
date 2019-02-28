package seg.java;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CanvasDrawer {
    /**
     * FIELDS
     **/
    private Canvas topDownCanvas;
    private Canvas sideOnCanvas;

    private GraphicsContext gc;

    /**
     * Constructors
     **/
    public CanvasDrawer() {

    }

    public CanvasDrawer(Canvas topDownCanvas, Canvas sideOnCanvas) {
        this.topDownCanvas = topDownCanvas;
        this.sideOnCanvas = sideOnCanvas;
    }

    /**
     * Drawing methods
     **/
    public void drawTopDownCanvas() {
        gc = topDownCanvas.getGraphicsContext2D();

        double canvasWidth = topDownCanvas.getWidth();
        double canvasHeight = canvasWidth / 3; // topDownCanvas.getHeight()
        double xCentre = canvasWidth / 2;
        double yCentre = canvasHeight / 2;

        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        /** LAYERS GET SET **/
        Rectangle grassRect = new Rectangle(0.1 * canvasWidth, 0.1 * canvasHeight, 0.8 * canvasWidth, 0.8 * canvasHeight);

        /** PINK AREA **/
        Rectangle runwayStripRect = new Rectangle(0.15 * canvasWidth, 0.15 * canvasHeight, 0.7 * canvasWidth, 0.7 * canvasHeight);

        /** "VERTICAL" DARK BLUE RECTANGLE **/
        Rectangle clearedGradedRect1 = new Rectangle(0.3 * canvasWidth, 0.2 * canvasHeight, 0.4 * canvasWidth, 0.6 * canvasHeight);

        /** WIDE DARK BLUE RECTANGLE **/
        Rectangle clearedGradedRect2 = new Rectangle(0.15 * canvasWidth, 0.30 * canvasHeight, 0.7 * canvasWidth, 0.4 * canvasHeight);

        /** ASPHALT AREA **/
        Rectangle runwayRect = new Rectangle(0.18 * canvasWidth, 0.35 * canvasHeight, 0.64 * canvasWidth, 0.3 * canvasHeight);

        /**
         Rectangle grassRect = new Rectangle(0.5 * canvasWidth - (canvasHeight - 20), 10, 2 * (canvasHeight - 20), canvasHeight - 20);
        Rectangle runwayStripRect = new Rectangle(0.5 * canvasWidth - (canvasHeight - 20) + 10, 20, 2 * (canvasHeight - 20) - 20, canvasHeight - 40);
        Rectangle clearedGradedRect1 = new Rectangle(0.5 * canvasWidth - (canvasHeight - 20) + 10, 110, 2 * (canvasHeight - 20) - 20, canvasHeight - 220);
        Rectangle clearedGradedRect2 = new Rectangle(0.5 * canvasWidth - (canvasHeight - 20) + 160, 70, 2 * (canvasHeight - 20) - 320, canvasHeight - 140);
        Rectangle runwayRect = new Rectangle(0.5 * canvasWidth - (canvasHeight - 20) + 70, 150, 2 * (canvasHeight - 20) - 140, canvasHeight - 300)
         */

        /** Layers are drawn - from the bottom up **/
        gc.setFill(Color.GREEN);
        fillRect(gc, grassRect);
        gc.setFill(Color.MEDIUMPURPLE);
        fillRect(gc, runwayStripRect);
        gc.setFill(Color.DARKBLUE);
        fillRect(gc, clearedGradedRect1);
        fillRect(gc, clearedGradedRect2);
        gc.setFill(Color.GRAY);
        fillRect(gc, runwayRect);

        /** fill in the rectangles on the sides **/
        gc.setFill(Color.DARKBLUE);
        gc.fillPolygon(new double[]{
                0.25 * canvasWidth,
                0.3 * canvasWidth + 1,
                0.3 * canvasWidth + 1,}, new double[]{
                0.3 * canvasHeight + 1,
                0.3 * canvasHeight + 1,
                0.2 * canvasHeight,}, 3);
        gc.fillPolygon(new double[]{
                0.7 * canvasWidth - 1,
                0.7 * canvasWidth - 1,
                0.75 * canvasWidth,}, new double[]{
                0.2 * canvasHeight,
                0.3 * canvasHeight + 1,
                0.3 * canvasHeight + 1,}, 3);
        gc.fillPolygon(new double[]{
                0.3 * canvasWidth + 1,
                0.3 * canvasWidth + 1,
                0.25 * canvasWidth,}, new double[]{
                0.8 * canvasHeight,
                0.7 * canvasHeight - 1,
                0.7 * canvasHeight - 1,}, 3);
        gc.fillPolygon(new double[]{
                0.7 * canvasWidth - 1,
                0.7 * canvasWidth - 1,
                0.75 * canvasWidth,}, new double[]{
                0.8 * canvasHeight,
                0.7 * canvasHeight - 1,
                0.7 * canvasHeight - 1,}, 3);

        /** The runway centerline gets drawn here **/
        double centerlineX = 0.3 * canvasWidth;
        double centerlineY = 0.5 * canvasHeight;
        double stripWidth = 0.01 * canvasWidth;
        double stripHeight = 0.003 * canvasHeight;
        gc.setFill(Color.WHITE);
        while (centerlineX < canvasWidth * 0.70) {
            if (centerlineX > 0.8 * canvasWidth) {
                stripWidth = 0.82 - centerlineX;
                Rectangle whiteRect = new Rectangle(centerlineX, centerlineY, stripWidth, stripHeight);
                fillRect(gc, whiteRect);
                break;
            }
            Rectangle whiteRect = new Rectangle(centerlineX, centerlineY, stripWidth, stripHeight);
            fillRect(gc, whiteRect);
            centerlineX += stripWidth * 2;
        }


        /** The next 2 while draw the sets of lines that look like crosswalks **/
        stripWidth = 0.05 * canvasWidth;
        stripHeight = 0.006 * canvasHeight;
        double y = 0.35 * canvasHeight;
        double stripStartLeftX = 0.2 * canvasWidth;
        double stripStartRightX = 0.74 * canvasWidth;

        gc.setFill(Color.WHITE);

        while (y + 4 < 0.65 * canvasHeight) {
            Rectangle whiteRect = new Rectangle(stripStartLeftX, y, stripWidth, stripHeight);
            fillRect(gc, whiteRect); // left one gets drawn

            whiteRect = new Rectangle(stripStartRightX, y, stripWidth, stripHeight);
            fillRect(gc, whiteRect); // right one gets drawn
            y += stripHeight * 3;
        }

        /** draw plane --the image needs to be in the assets folder **/
        ImageView imageView = new ImageView("/assets/top-down-plane.png");
        imageView.setRotate(-90);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(canvasHeight * 0.2);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        Image image1 = imageView.snapshot(parameters, null);
        gc.drawImage(image1, canvasWidth * 0.7, canvasHeight * 0.2);

        // gc.scale(2,2); //can be used for zoom later on
    }

    public void drawSideOnCanvas() {
        gc = sideOnCanvas.getGraphicsContext2D();


        double canvasWidth = sideOnCanvas.getWidth();
        double canvasHeight = canvasWidth / 3;

        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        /** Layers get set **/
        Rectangle grassRect = new Rectangle(0.1 * canvasWidth, 0.7 * canvasHeight, 0.8 * canvasWidth, 0.1 * canvasHeight);

        /** ASPHALT AREA **/
        Rectangle asphaltRect = new Rectangle(0.1 * canvasWidth, 0.7 * canvasHeight, 0.8 * canvasWidth, 0.01 * canvasHeight);

        /** Layers are drawn - from the bottom up **/
        gc.setFill(Color.GREEN);
        fillRect(gc, grassRect);
        gc.setFill(Color.GRAY);
        fillRect(gc, asphaltRect);

        /** draw plane --the image needs to be in the assets folder **/
        ImageView imageView = new ImageView("/assets/side-view-plane.png");
        imageView.setRotate(-4);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(canvasHeight * 0.1);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        Image image1 = imageView.snapshot(parameters, null);
        gc.drawImage(image1, canvasWidth * 0.7, canvasHeight * 0.3);

    }

    public void drawBothCanvases() {

    }

    /** Helper methods **/
    private void fillRect(GraphicsContext gc, Rectangle rect) {
        gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    /** Setters **/
    public void setCanvases(Canvas topDownCanvas, Canvas sideOnCanvas) {
        this.topDownCanvas = topDownCanvas;
        this.sideOnCanvas = sideOnCanvas;
    }
}
