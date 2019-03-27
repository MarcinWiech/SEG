package seg.java;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import seg.java.models.Runway;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;


public class CanvasDrawer {

/*==================================================================================================================================
//  Fields
//================================================================================================================================*/

    double imageX, imageY;
    private GraphicsContext gc;
    private Runway runway;
    private RedeclarationComputer redeclarationComputer;
    private double widthToHeightRatio = 2.3;
    private double canvasWidth;
    private double canvasHeight;
    private double xOffset;
    private double yOffset;
    private HashMap<String, double[]> horizontalLines;

    private double[] startPoint;
    private double[] middlePoint;
    private double[] topPint;

/*==================================================================================================================================
//  Constructors
//================================================================================================================================*/

    public CanvasDrawer(RedeclarationComputer redeclarationComputer) {
        this.redeclarationComputer = redeclarationComputer;
        horizontalLines = new HashMap<>();
    }

/*==================================================================================================================================
//  Drawing methods
//================================================================================================================================*/

    public void drawTopDownCanvas(Canvas canvas) {
        adjustDrawingSettings(canvas);

        //  Grass area gets set
        drawRectangle(0.02 * canvasWidth, 0.02 * canvasHeight, 0.96 * canvasWidth, 0.96 * canvasHeight, Color.GREEN);

        //  Purple area gets set
        drawRectangle(0.07 * canvasWidth, 0.07 * canvasHeight, 0.86 * canvasWidth, 0.86 * canvasHeight, Color.MEDIUMPURPLE);

        //  "Vertical dark blue are gets set
        drawRectangle(0.22 * canvasWidth, 0.12 * canvasHeight, 0.56 * canvasWidth, 0.76 * canvasHeight, Color.DARKBLUE);

        //  "Horizontal" dark blue area gets set
        drawRectangle(0.07 * canvasWidth, 0.23 * canvasHeight, 0.86 * canvasWidth, 0.56 * canvasHeight, Color.DARKBLUE);

        //  Asphalt area gets set
        drawRectangle(0.1 * canvasWidth, 0.4 * canvasHeight, 0.8 * canvasWidth, 0.2 * canvasHeight, Color.GRAY);


        //  The 4 blue triangles get drawn here
        gc.setFill(Color.DARKBLUE);
        gc.fillPolygon(new double[]{
                0.17 * canvasWidth + xOffset,
                0.22 * canvasWidth + 1 + xOffset,
                0.22 * canvasWidth + 1 + xOffset,}, new double[]{
                0.23 * canvasHeight + 1 + yOffset,
                0.23 * canvasHeight + 1 + yOffset,
                0.12 * canvasHeight + yOffset,}, 3);
        gc.fillPolygon(new double[]{
                0.78 * canvasWidth - 1 + xOffset,
                0.78 * canvasWidth - 1 + xOffset,
                0.83 * canvasWidth + xOffset,}, new double[]{
                0.12 * canvasHeight + yOffset,
                0.23 * canvasHeight + 1 + yOffset,
                0.23 * canvasHeight + 1 + yOffset,}, 3);
        gc.fillPolygon(new double[]{
                0.22 * canvasWidth + 1 + xOffset,
                0.22 * canvasWidth + 1 + xOffset,
                0.17 * canvasWidth + xOffset,}, new double[]{
                0.88 * canvasHeight + yOffset,
                0.79 * canvasHeight - 1 + yOffset,
                0.79 * canvasHeight - 1 + yOffset,}, 3);
        gc.fillPolygon(new double[]{
                0.78 * canvasWidth - 1 + xOffset,
                0.78 * canvasWidth - 1 + xOffset,
                0.83 * canvasWidth + xOffset,}, new double[]{
                0.88 * canvasHeight + yOffset,
                0.79 * canvasHeight - 1 + yOffset,
                0.79 * canvasHeight - 1 + yOffset,}, 3);

        //  The runway centerline gets drawn here
        double stripWidth = 0.02 * canvasWidth;
        double stripHeight = 0.008 * canvasHeight;
        double centerlineX = 0.23 * canvasWidth;
        double centerlineY = 0.5 * canvasHeight - 0.5 * stripHeight;

        while (centerlineX < canvasWidth * 0.73) {
            if (centerlineX > 0.8 * canvasWidth) {
                stripWidth = 0.82 - centerlineX;
                drawRectangle(centerlineX, centerlineY, stripWidth, stripHeight, Color.WHITE);
                break;
            }
            drawRectangle(centerlineX, centerlineY, stripWidth, stripHeight, Color.WHITE);
            centerlineX += stripWidth * 2;
        }

        //  Sets of lines that look like crosswalks get drawn
        stripWidth = 0.08 * canvasWidth;
        stripHeight = 0.006 * canvasHeight;
        double y = 0.412 * canvasHeight;
        double stripStartLeftX = 0.12 * canvasWidth;
        double stripStartRightX = 0.80 * canvasWidth;

        while (y + 4 < 0.6 * canvasHeight) {

            drawRectangle(stripStartLeftX, y, stripWidth, stripHeight, Color.WHITE);
            // left one gets drawn
            drawRectangle(stripStartRightX, y, stripWidth, stripHeight, Color.WHITE);
            y += stripHeight * 2.8;
        }

        // Runway designator gets drawn here
        String text;
        if (runway != null) {
            text = runway.getName();
        } else {
            text = "N/A";
        }
        gc.setFill(Color.WHITE);
        Font font = Font.font("Arial", canvasWidth * 0.03);
        gc.setFont(font);
        gc.save();
        Affine rotate = new Affine();
        rotate.appendRotation(-90, 0.5 * canvasWidth + xOffset, 0.5 * canvasHeight + yOffset);
        gc.setTransform(rotate);
        gc.fillText(text, 0.473 * canvasWidth + xOffset, 1.133 * canvasHeight + yOffset);
        gc.restore();

        // Cleared and graded area label
        gc.setFill(Color.WHITE);
        text = "Cleared and Graded area";
        font = Font.font("Arial", canvasWidth * 0.017);
        gc.setFont(font);
        gc.fillText(text, 0.4 * canvasWidth + xOffset, 0.3 * canvasHeight + yOffset);

        // Details get drawn here
        if (runway != null) {
            drawTopDownRunwayDirection(canvas);
            // Really important that the image gets drawn before details do
            drawTopDownObstacle(canvas);
            drawTopDownDetails(canvas);
            // Second time drawn so that the image stays on top
            drawTopDownObstacle(canvas);
        }

        // gc.scale(2,2); // can be used for zoom later on
    }

    public void drawSideOnCanvas(Canvas canvas) {

        adjustDrawingSettings(canvas);

        //  Sky layer gets set
        drawRectangle(0.02 * canvasWidth, 0.02 * canvasHeight, 0.96 * canvasWidth, 0.96 * canvasHeight, Color.SKYBLUE);

        //  Grass layer gets set
        drawRectangle(0.02 * canvasWidth, 0.55 * canvasHeight, 0.96 * canvasWidth, 0.43 * canvasHeight, Color.GREEN);

        //  Asphalt layer gets set
        drawRectangle(0.1 * canvasWidth, 0.55 * canvasHeight, 0.80 * canvasWidth, 0.03 * canvasHeight, Color.GRAY);
        // Rectangle asphaltRect = new Rectangle(0.02 * canvasWidth, 0.55 * canvasHeight, 0.96 * canvasWidth, 0.03 * canvasHeight);

        if (runway != null) {
            drawSideOnRunwayDirection(canvas);
            // Really important that the image gets drawn before details do
            drawSideOnObstacle(canvas);
            drawSideOnDetails(canvas);
            // Second time drawn so that the image stays on top
            drawSideOnObstacle(canvas);
        }
    }

    private void drawTopDownObstacle(Canvas canvas) {
        //  Draws the plane - the image needs to be in the assets folder
        gc = canvas.getGraphicsContext2D();
        ImageView imageView = new ImageView("/seg/resources/images/top-down-plane.png");
        imageView.setRotate(-90);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(canvasHeight * 0.2);


        if (redeclarationComputer.getCalculationCase() == 1 || redeclarationComputer.getCalculationCase() == 3) {
            Translate flipTranslation = new Translate(0, imageView.getImage().getHeight());
            Rotate flipRotation = new Rotate(180, Rotate.X_AXIS);
            imageView.getTransforms().addAll(flipTranslation, flipRotation);
        }
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        Image image1 = imageView.snapshot(parameters, null);

        Double x = redeclarationComputer.getAppropriateX();
        Double tora = runway.getTora();
        Double dispTresh = runway.getThreshold();

        if (redeclarationComputer.getCalculationCase() == 1 || redeclarationComputer.getCalculationCase() == 3) {
            imageX = canvasWidth * (0.9 - 0.8 * ((dispTresh + x) / tora)) + xOffset;
            imageY = canvasHeight * 0.5 - 0.5 * image1.getHeight() - canvasHeight * 0.1 * ((redeclarationComputer.getObstacleY() / 30)) + yOffset;
            gc.drawImage(image1, imageX, imageY);
        } else {
            imageX = canvasWidth * (0.9 - 0.8 * ((dispTresh + x) / tora)) + xOffset - image1.getWidth();
            imageY = canvasHeight * 0.5 - 0.5 * image1.getHeight() + canvasHeight * 0.1 * ((redeclarationComputer.getObstacleY() / 30)) + yOffset;
            gc.drawImage(image1, imageX, imageY);
        }
    }

    private void drawSideOnObstacle(Canvas canvas) {
        //  Draws the plane - the image needs to be in the assets folder
        gc = canvas.getGraphicsContext2D();
        ImageView imageView = new ImageView("/seg/resources/images/side-view-plane.png");
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(canvasHeight * 0.006 * redeclarationComputer.getObstacleHeight());
        if (redeclarationComputer.getCalculationCase() == 1 || redeclarationComputer.getCalculationCase() == 3) {
            Translate flipTranslation = new Translate(0, imageView.getImage().getHeight());
            Rotate flipRotation = new Rotate(180, Rotate.Y_AXIS);
            imageView.getTransforms().addAll(flipTranslation, flipRotation);
        }
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        Image image1 = imageView.snapshot(parameters, null);

        Double x = redeclarationComputer.getAppropriateX();
        Double tora = runway.getTora();
        Double dispTresh = runway.getThreshold();

        if (redeclarationComputer.getCalculationCase() == 1 || redeclarationComputer.getCalculationCase() == 3) {
            imageX = canvasWidth * (0.9 - 0.8 * ((dispTresh + x) / tora)) + xOffset;
            imageY = canvasHeight * 0.55 - 0.9 * image1.getHeight() + yOffset;
            gc.drawImage(image1, imageX, imageY);
        } else {
            imageX = canvasWidth * (0.9 - 0.8 * ((dispTresh + x) / tora)) + xOffset - image1.getWidth();
            imageY = canvasHeight * 0.55 - 0.9 * image1.getHeight() + yOffset;
            gc.drawImage(image1, imageX, imageY);
        }
    }

    private void drawHorizontalArrow(double x, double y, double length, boolean leftArrowTipOn, boolean rightArrowTipOn, String text, Color color) {
        double textOffset = 0;

        if (leftArrowTipOn && !rightArrowTipOn) {
            textOffset += 0.006;
        } else if (!leftArrowTipOn && rightArrowTipOn) {
            textOffset -= 0.006;
        }

        //  Color and font get set + text drawing preparation
        gc.setFill(color);
        Font font = Font.font("Arial", canvasWidth * 0.015);
        gc.setFont(font);
        Text textObject = new Text(text);
        textObject.setFont(font);
        double textWidth = textObject.getBoundsInLocal().getWidth();

        //  Text gets drawn
        gc.fillText(text, (x + textOffset + length / 2) * canvasWidth - 0.5 * textWidth + xOffset, (y - 0.01) * canvasHeight + yOffset);

        //  Left arrow tip gets drawn
        if (leftArrowTipOn) {
            gc.fillPolygon(new double[]{
                    x * canvasWidth + xOffset,
                    (x + 0.015) * canvasWidth + xOffset,
                    (x + 0.015) * canvasWidth + xOffset,}, new double[]{
                    y * canvasHeight + yOffset,
                    (y - 0.015) * canvasHeight + yOffset,
                    (y + 0.015) * canvasHeight + yOffset,}, 3);

            x += 0.008;
            length -= 0.008;
        }

        //  Right arrow tip gets drawn
        if (rightArrowTipOn) {
            gc.fillPolygon(new double[]{
                    (x + length) * canvasWidth + xOffset,
                    (x + length - 0.015) * canvasWidth + xOffset,
                    (x + length - 0.015) * canvasWidth + xOffset,}, new double[]{
                    y * canvasHeight + yOffset,
                    (y - 0.015) * canvasHeight + yOffset,
                    (y + 0.015) * canvasHeight + yOffset,}, 3);
            length -= 0.008;
        }

        //  Arrow body gets drawn
        gc.fillRect(x * canvasWidth + xOffset, (y - 0.0025) * canvasHeight + yOffset, length * canvasWidth, 0.005 * canvasHeight);
    }

    private void drawVerticalArrow(double x, double y, double length, boolean upperArrowTipOn, boolean lowerArrowTipOn, String text, Color color) {
        //  Color and font get set
        gc.setFill(color);
        gc.setFont(Font.font("Arial", canvasWidth * 0.015));

        //  Text gets drawn
        gc.fillText(text, (x + 0.005) * canvasWidth + xOffset, (y + 0.14) * canvasHeight + yOffset);

        //  Upper arrow tip gets drawn
        if (upperArrowTipOn) {
            gc.fillPolygon(new double[]{
                    x * canvasWidth + xOffset,
                    (x - 0.006) * canvasWidth + xOffset,
                    (x + 0.006) * canvasWidth + xOffset,}, new double[]{
                    y * canvasHeight + yOffset,
                    (y + 0.035) * canvasHeight + yOffset,
                    (y + 0.035) * canvasHeight + yOffset,}, 3);
            y += 0.008;
            length -= 0.008;
        }

        //  Lower arrow tip gets drawn
        if (lowerArrowTipOn) {
            gc.fillPolygon(new double[]{
                    x * canvasWidth + xOffset,
                    (x - 0.006) * canvasWidth + xOffset,
                    (x + 0.006) * canvasWidth + xOffset,}, new double[]{
                    (y + length) * canvasHeight + yOffset,
                    (y + length - 0.035) * canvasHeight + yOffset,
                    (y + length - 0.035) * canvasHeight + yOffset,}, 3);
            length -= 0.008;
        }

        //  Arrow body gets drawn
        gc.fillRect((x - 0.0009) * canvasWidth + xOffset, y * canvasHeight + yOffset, 0.002 * canvasWidth, length * canvasHeight);
    }

    private void drawTopDownRunwayDirection(Canvas canvas) {
        Rectangle backgroundRect = new Rectangle(0.025 * canvasWidth, 0.03 * canvasHeight, 0.342 * canvasWidth, 0.13 * canvasHeight);
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        fillRect(backgroundRect);

        drawHorizontalArrow(0.03, 0.08, 0.33, true, false, runway.getName() + " - Landing and Take-off in this direction", Color.WHITE);
        if (redeclarationComputer.getCalculationCase() == 2 || redeclarationComputer.getCalculationCase() == 4) {
            drawHorizontalArrow(0.03, 0.13, 0.33, true, false, "Take Off Towards, Landing Towards", Color.WHITE);
        } else {
            drawHorizontalArrow(0.03, 0.13, 0.3, true, false, "Take Off Away, Landing Over", Color.WHITE);
        }

        backgroundRect = new Rectangle(0.77 * canvasWidth, 0.03 * canvasHeight, 0.205 * canvasWidth, 0.21 * canvasHeight);
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        fillRect(backgroundRect);

        gc.setFill((Color.WHITE));
        Font font = Font.font("Arial", canvasWidth * 0.015);
        gc.setFont(font);
        gc.fillText("RL = Landing RESA/Slope", 0.785 * canvasWidth + xOffset, 0.07 * canvasHeight + yOffset);
        gc.fillText("RL = Take Off RESA/Slope", 0.785 * canvasWidth + xOffset, 0.12 * canvasHeight + yOffset);
        gc.fillText("DT = Displaced Treshold", 0.785 * canvasWidth + xOffset, 0.17 * canvasHeight + yOffset);
        gc.fillText("60 = Strip End", 0.785 * canvasWidth + xOffset, 0.22 * canvasHeight + yOffset);
    }

    private void drawSideOnRunwayDirection(Canvas canvas) {
        Rectangle backgroundRect = new Rectangle(0.025 * canvasWidth, 0.03 * canvasHeight, 0.342 * canvasWidth, 0.13 * canvasHeight);
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        fillRect(backgroundRect);

        drawHorizontalArrow(0.03, 0.08, 0.33, true, false, runway.getName() + " - Landing and Take-off in this direction", Color.WHITE);
        if (redeclarationComputer.getCalculationCase() == 2 || redeclarationComputer.getCalculationCase() == 4) {
            drawHorizontalArrow(0.03, 0.13, 0.33, true, false, "Take Off Towards, Landing Towards", Color.WHITE);
        } else {
            drawHorizontalArrow(0.03, 0.13, 0.3, true, false, "Take Off Away, Landing Over", Color.WHITE);
        }

        backgroundRect = new Rectangle(0.77 * canvasWidth, 0.03 * canvasHeight, 0.205 * canvasWidth, 0.21 * canvasHeight);
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        fillRect(backgroundRect);

        gc.setFill((Color.WHITE));
        Font font = Font.font("Arial", canvasWidth * 0.015);
        gc.setFont(font);
        gc.fillText("RL = Landing RESA/Slope", 0.785 * canvasWidth + xOffset, 0.07 * canvasHeight + yOffset);
        gc.fillText("RL = Take Off RESA/Slope", 0.785 * canvasWidth + xOffset, 0.12 * canvasHeight + yOffset);
        gc.fillText("DT = Displaced Treshold", 0.785 * canvasWidth + xOffset, 0.17 * canvasHeight + yOffset);
        gc.fillText("60 = Strip End", 0.785 * canvasWidth + xOffset, 0.22 * canvasHeight + yOffset);
    }

    private void drawTopDownDetails(Canvas canvas) {
        // Rectangle asphaltRect = new Rectangle(0.1 * canvasWidth, 0.55 * canvasHeight, 0.80 * canvasWidth, 0.03 * canvasHeight);
        Double initialTora = runway.getTora();
        Double dispTresh = runway.getThreshold();
        Double tora = redeclarationComputer.getTora();
        Double toda = redeclarationComputer.getToda();
        Double asda = redeclarationComputer.getAsda();
        Double lda = redeclarationComputer.getLda();
        Double stopway = redeclarationComputer.calculateStopway();
        Double clearway = redeclarationComputer.calculateClearway();

        Double toraX = 0.0, toraLength = 0.0;
        Double todaX = 0.0, todaLength = 0.0;
        Double asdaX = 0.0, asdaLength = 0.0;
        Double ldaX = 0.0, ldaLength = 0.0;
        Double dispTreshX = 0.0, dispTreshLength = 0.0;
        Double stripEndLX = 0.0, stripEndLLength = 0.0;
        Double stripEndTX = 0.0, stripEndTLength = 0.0;
        Double resaMinLX = 0.0, resaMinLLength = 0.0;
        Double resaMinTX = 0.0, resaMinTLength = 0.0;

        // Arrow coordinates get computed
        if (redeclarationComputer.getCalculationCase() == 2 || redeclarationComputer.getCalculationCase() == 4) {
            ldaX = 0.1 + 0.8 * (1 - (lda + dispTresh) / initialTora);
            ldaLength = 0.8 * (lda / initialTora);

            if (dispTresh != 0) {
                dispTreshX = 0.1 + 0.8 * (1 - dispTresh / initialTora);
                dispTreshLength = 0.8 * (dispTresh / initialTora);
            }

            stripEndLX = ldaX - 0.8 * (60 / initialTora);
            stripEndLLength = 0.8 * (60 / initialTora);

            resaMinLX = stripEndLX - 0.8 * ((redeclarationComputer.getAppropriateX() - lda - 60) / initialTora);
            resaMinLLength = stripEndLX - resaMinLX;

            toraX = 0.1 + 0.8 * (1 - tora / initialTora);
            toraLength = 0.8 * (tora / initialTora);

            stripEndTX = toraX - 0.8 * (60 / initialTora);
            stripEndTLength = 0.8 * (60 / initialTora);

            resaMinTX = resaMinLX;
            resaMinTLength = stripEndTX - resaMinTX;

            asdaX = 0.1 + 0.8 * (1 - asda / initialTora);
            asdaLength = 0.8 * (asda / initialTora);

            todaX = 0.1 + 0.8 * (1 - toda / initialTora);
            todaLength = 0.8 * (toda / initialTora);
        } else {
            ldaX = 0.1;
            ldaLength = 0.8 * (lda / initialTora);

            stripEndLX = ldaX + ldaLength;
            stripEndLLength = 0.8 * (60 / initialTora);

            resaMinLX = stripEndLX + stripEndLLength;
            resaMinLLength = 0.9 - 0.8 * ((dispTresh + redeclarationComputer.getAppropriateX()) / initialTora) - resaMinLX;

            toraX = 0.1;
            toraLength = 0.8 * (tora / initialTora);

            stripEndTX = toraX + toraLength;
            stripEndTLength = 0.8 * (60 / initialTora);

            resaMinTX = stripEndTX + stripEndTLength;
            resaMinTLength = 0.9 - 0.8 * ((dispTresh + redeclarationComputer.getAppropriateX()) / initialTora) - resaMinTX;

            asdaX = 0.1 - 0.8 * (stopway / initialTora);
            asdaLength = 0.8 * (asda / initialTora);

            todaX = 0.1 - 0.8 * (clearway / initialTora);
            todaLength = 0.8 * (toda / initialTora);
        }

        // Clearway gets drawn
        Rectangle clearwayRect = new Rectangle((toraX - 0.8 * (clearway / initialTora)) * canvasWidth, 0.35 * canvasHeight, 0.8 * (clearway / initialTora) * canvasWidth, 0.3 * canvasHeight);
        gc.setFill(Color.rgb(159, 181, 0));
        fillRect(clearwayRect);

        // Stopway gets drawn
        Rectangle stopwayRect = new Rectangle((toraX - 0.8 * (stopway / initialTora)) * canvasWidth, 0.4 * canvasHeight, 0.8 * (stopway / initialTora) * canvasWidth, 0.2 * canvasHeight);
        gc.setFill(Color.rgb(188, 91, 92));
        fillRect(stopwayRect);

        // Reciprocal clearway and stopway get drawn
        if (runway.getReciprocalRunway() != null) {
            RedeclarationComputer recipComp = redeclarationComputer.getReciprocalComputer();
            clearway = recipComp.getToda() - recipComp.getTora();
            stopway = recipComp.getAsda() - recipComp.getTora();
            Double recipTora = recipComp.getTora();
            Double recipInitTora = runway.getReciprocalRunway().getTora();

            if (redeclarationComputer.getCalculationCase() == 1 || redeclarationComputer.getCalculationCase() == 3) {
                // Reciprocal clearway gets drawn
                drawRectangle((0.1 + 0.8 * (recipTora / recipInitTora)) * canvasWidth, 0.35 * canvasHeight, 0.8 * (clearway / recipInitTora) * canvasWidth, 0.3 * canvasHeight, Color.rgb(159, 181, 0, 0.6));

                // Reciprocal stopway gets drawn
                drawRectangle((0.1 + 0.8 * (recipTora / recipInitTora)) * canvasWidth, 0.4 * canvasHeight, 0.8 * (stopway / recipInitTora) * canvasWidth, 0.2 * canvasHeight, Color.rgb(188, 91, 92, 0.6));
            } else {
                // Reciprocal clearway gets drawn
                drawRectangle(0.9 * canvasWidth, 0.35 * canvasHeight, 0.8 * (clearway / recipInitTora) * canvasWidth, 0.3 * canvasHeight, Color.rgb(159, 181, 0, 0.6));

                // Reciprocal stopway gets drawn
                drawRectangle(0.9 * canvasWidth, 0.4 * canvasHeight, 0.8 * (stopway / recipInitTora) * canvasWidth, 0.2 * canvasHeight, Color.rgb(188, 91, 92, 0.6));
            }
        }

        // Vertical lines get drawn here
        double vertPercent = 0.001;
        gc.setFill(Color.DARKGRAY);

        // LDA vertical lines
        Rectangle lineRect = new Rectangle(ldaX * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.6) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((ldaX + ldaLength) * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.6) * canvasHeight);
        fillRect(lineRect);

        // DispTresh vertical lines
        if ((redeclarationComputer.getCalculationCase() == 2 || redeclarationComputer.getCalculationCase() == 4) && dispTresh != 0) {
            lineRect = new Rectangle(dispTreshX * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
            fillRect(lineRect);
            lineRect = new Rectangle((dispTreshX + dispTreshLength) * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
            fillRect(lineRect);
        }

        // StripEndL vertical lines
        lineRect = new Rectangle(stripEndLX * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.6) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((stripEndLX + stripEndLLength) * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.6) * canvasHeight);
        fillRect(lineRect);

        // ResaMinL vertical lines
        lineRect = new Rectangle(resaMinLX * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.6) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((resaMinLX + resaMinLLength) * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.6) * canvasHeight);
        fillRect(lineRect);

        // TORA vertical lines
        lineRect = new Rectangle(toraX * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.75 - 0.6) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((toraX + toraLength) * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.75 - 0.6) * canvasHeight);
        fillRect(lineRect);

        // StripEndT vertical lines
        lineRect = new Rectangle(stripEndTX * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.6) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((stripEndTX + stripEndTLength) * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.6) * canvasHeight);
        fillRect(lineRect);

        // ResaMinT vertical lines
        lineRect = new Rectangle(resaMinTX * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.75 - 0.6) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((resaMinTX + resaMinTLength) * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.75 - 0.6) * canvasHeight);
        fillRect(lineRect);

        // ASDA vertical lines
        lineRect = new Rectangle(asdaX * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.85 - 0.6) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((asdaX + asdaLength) * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.85 - 0.6) * canvasHeight);
        fillRect(lineRect);

        // TODA vertical lines
        lineRect = new Rectangle(todaX * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.95 - 0.6) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((todaX + todaLength) * canvasWidth, 0.6 * canvasHeight, vertPercent * canvasWidth, (0.95 - 0.6) * canvasHeight);
        fillRect(lineRect);

        // Arrows get drawn here
        drawHorizontalArrow(ldaX, 0.65, ldaLength, true, true, "LDA = " + lda, Color.WHITE);
        if ((redeclarationComputer.getCalculationCase() == 2 || redeclarationComputer.getCalculationCase() == 4) && dispTresh != 0)
            drawHorizontalArrow(dispTreshX, 0.65, dispTreshLength, true, true, "DT", Color.YELLOW);
        drawHorizontalArrow(stripEndLX, 0.65, stripEndLLength, false, false, "60", Color.YELLOW);
        drawHorizontalArrow(resaMinLX, 0.65, resaMinLLength, true, true, "RL", Color.YELLOW);
        drawHorizontalArrow(toraX, 0.75, toraLength, true, true, "TORA = " + tora, Color.WHITE);
        drawHorizontalArrow(stripEndTX, 0.75, stripEndTLength, false, false, "60", Color.YELLOW);
        drawHorizontalArrow(resaMinTX, 0.75, resaMinTLength, true, true, "RT", Color.YELLOW);
        drawHorizontalArrow(asdaX, 0.85, asdaLength, true, true, "ASDA = " + asda, Color.WHITE);
        drawHorizontalArrow(todaX, 0.95, todaLength, true, true, "TODA = " + toda, Color.WHITE);

    }

    private void drawSideOnDetails(Canvas canvas) {
        // Rectangle asphaltRect = new Rectangle(0.1 * canvasWidth, 0.55 * canvasHeight, 0.80 * canvasWidth, 0.03 * canvasHeight);
        Double initialTora = runway.getTora();
        Double dispTresh = runway.getThreshold();
        Double tora = redeclarationComputer.getTora();
        Double toda = redeclarationComputer.getToda();
        Double asda = redeclarationComputer.getAsda();
        Double lda = redeclarationComputer.getLda();
        Double stopway = redeclarationComputer.calculateStopway();
        Double clearway = redeclarationComputer.calculateClearway();

        Double toraX = 0.0, toraLength = 0.0;
        Double todaX = 0.0, todaLength = 0.0;
        Double asdaX = 0.0, asdaLength = 0.0;
        Double ldaX = 0.0, ldaLength = 0.0;
        Double dispTreshX = 0.0, dispTreshLength = 0.0;
        Double stripEndLX = 0.0, stripEndLLength = 0.0;
        Double stripEndTX = 0.0, stripEndTLength = 0.0;
        Double resaMinLX = 0.0, resaMinLLength = 0.0;
        Double resaMinTX = 0.0, resaMinTLength = 0.0;

        // Arrow coordinates get computed
        if (redeclarationComputer.getCalculationCase() == 2 || redeclarationComputer.getCalculationCase() == 4) {
            ldaX = 0.1 + 0.8 * (1 - (lda + dispTresh) / initialTora);
            ldaLength = 0.8 * (lda / initialTora);

            if (dispTresh != 0) {
                dispTreshX = 0.1 + 0.8 * (1 - dispTresh / initialTora);
                dispTreshLength = 0.8 * (dispTresh / initialTora);
            }

            stripEndLX = ldaX - 0.8 * (60 / initialTora);
            stripEndLLength = 0.8 * (60 / initialTora);

            resaMinLX = stripEndLX - 0.8 * ((redeclarationComputer.getAppropriateX() - lda - 60) / initialTora);
            resaMinLLength = stripEndLX - resaMinLX;

            toraX = 0.1 + 0.8 * (1 - tora / initialTora);
            toraLength = 0.8 * (tora / initialTora);

            stripEndTX = toraX - 0.8 * (60 / initialTora);
            stripEndTLength = 0.8 * (60 / initialTora);

            resaMinTX = resaMinLX;
            resaMinTLength = stripEndTX - resaMinTX;

            asdaX = 0.1 + 0.8 * (1 - asda / initialTora);
            asdaLength = 0.8 * (asda / initialTora);

            todaX = 0.1 + 0.8 * (1 - toda / initialTora);
            todaLength = 0.8 * (toda / initialTora);
        } else {
            ldaX = 0.1;
            ldaLength = 0.8 * (lda / initialTora);

            stripEndLX = ldaX + ldaLength;
            stripEndLLength = 0.8 * (60 / initialTora);

            resaMinLX = stripEndLX + stripEndLLength;
            resaMinLLength = 0.9 - 0.8 * ((dispTresh + redeclarationComputer.getAppropriateX()) / initialTora) - resaMinLX;

            toraX = 0.1;
            toraLength = 0.8 * (tora / initialTora);

            stripEndTX = toraX + toraLength;
            stripEndTLength = 0.8 * (60 / initialTora);

            resaMinTX = stripEndTX + stripEndTLength;
            resaMinTLength = 0.9 - 0.8 * ((dispTresh + redeclarationComputer.getAppropriateX()) / initialTora) - resaMinTX;

            asdaX = 0.1 - 0.8 * (stopway / initialTora);
            asdaLength = 0.8 * (asda / initialTora);

            todaX = 0.1 - 0.8 * (clearway / initialTora);
            todaLength = 0.8 * (toda / initialTora);
        }

        // Clearway gets drawn
        Rectangle clearwayRect = new Rectangle((toraX - 0.8 * (clearway / initialTora)) * canvasWidth, 0.55 * canvasHeight, 0.8 * (clearway / initialTora) * canvasWidth, 0.05 * canvasHeight);
        gc.setFill(Color.rgb(159, 181, 0));
        fillRect(clearwayRect);

        // Stopway gets drawn
        Rectangle stopwayRect = new Rectangle((toraX - 0.8 * (stopway / initialTora)) * canvasWidth, 0.55 * canvasHeight, 0.8 * (stopway / initialTora) * canvasWidth, 0.03 * canvasHeight);
        gc.setFill(Color.rgb(188, 91, 92));
        fillRect(stopwayRect);

        // Reciprocal clearway and stopway get drawn
        if (runway.getReciprocalRunway() != null) {
            RedeclarationComputer recipComp = redeclarationComputer.getReciprocalComputer();
            clearway = recipComp.getToda() - recipComp.getTora();
            stopway = recipComp.getAsda() - recipComp.getTora();
            Double recipTora = recipComp.getTora();
            Double recipInitTora = runway.getReciprocalRunway().getTora();

            if (redeclarationComputer.getCalculationCase() == 1 || redeclarationComputer.getCalculationCase() == 3) {
                // Reciprocal clearway gets drawn
                clearwayRect = new Rectangle((0.1 + 0.8 * (recipTora / recipInitTora)) * canvasWidth, 0.55 * canvasHeight, 0.8 * (clearway / recipInitTora) * canvasWidth, 0.05 * canvasHeight);
                gc.setFill(Color.rgb(159, 181, 0, 0.6));
                fillRect(clearwayRect);

                // Reciprocal stopway gets drawn
                stopwayRect = new Rectangle((0.1 + 0.8 * (recipTora / recipInitTora)) * canvasWidth, 0.55 * canvasHeight, 0.8 * (stopway / recipInitTora) * canvasWidth, 0.03 * canvasHeight);
                gc.setFill(Color.rgb(188, 91, 92, 0.6));
                fillRect(stopwayRect);
            } else {
                // Reciprocal clearway gets drawn
                clearwayRect = new Rectangle(0.9 * canvasWidth, 0.55 * canvasHeight, 0.8 * (clearway / recipInitTora) * canvasWidth, 0.05 * canvasHeight);
                gc.setFill(Color.rgb(159, 181, 0, 0.6));
                fillRect(clearwayRect);

                // Reciprocal stopway gets drawn
                stopwayRect = new Rectangle(0.9 * canvasWidth, 0.55 * canvasHeight, 0.8 * (stopway / recipInitTora) * canvasWidth, 0.03 * canvasHeight);
                gc.setFill(Color.rgb(188, 91, 92, 0.6));
                fillRect(stopwayRect);
            }
        }

        // Vertical lines get drawn here
        double vertPercent = 0.001;
        gc.setFill(Color.DARKGRAY);

        // LDA vertical lines
        Rectangle lineRect = new Rectangle(ldaX * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((ldaX + ldaLength) * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
        fillRect(lineRect);

        // DispTresh vertical lines
        if ((redeclarationComputer.getCalculationCase() == 2 || redeclarationComputer.getCalculationCase() == 4) && dispTresh != 0) {
            lineRect = new Rectangle(dispTreshX * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
            fillRect(lineRect);
            lineRect = new Rectangle((dispTreshX + dispTreshLength) * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
            fillRect(lineRect);
        }

        // StripEndL vertical lines
        lineRect = new Rectangle(stripEndLX * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((stripEndLX + stripEndLLength) * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
        fillRect(lineRect);

        // ResaMinL vertical lines
        lineRect = new Rectangle(resaMinLX * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((resaMinLX + resaMinLLength) * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
        fillRect(lineRect);

        // TORA vertical lines
        lineRect = new Rectangle(toraX * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.75 - 0.58) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((toraX + toraLength) * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.75 - 0.58) * canvasHeight);
        fillRect(lineRect);

        // StripEndT vertical lines
        lineRect = new Rectangle(stripEndTX * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((stripEndTX + stripEndTLength) * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.65 - 0.58) * canvasHeight);
        fillRect(lineRect);

        // ResaMinT vertical lines
        lineRect = new Rectangle(resaMinTX * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.75 - 0.58) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((resaMinTX + resaMinTLength) * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.75 - 0.58) * canvasHeight);
        fillRect(lineRect);

        // ASDA vertical lines
        lineRect = new Rectangle(asdaX * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.85 - 0.58) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((asdaX + asdaLength) * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.85 - 0.58) * canvasHeight);
        fillRect(lineRect);

        // TODA vertical lines
        lineRect = new Rectangle(todaX * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.95 - 0.58) * canvasHeight);
        fillRect(lineRect);
        lineRect = new Rectangle((todaX + todaLength) * canvasWidth, 0.58 * canvasHeight, vertPercent * canvasWidth, (0.95 - 0.58) * canvasHeight);
        fillRect(lineRect);

        // Arrows get drawn here
        drawHorizontalArrow(ldaX, 0.65, ldaLength, true, true, "LDA = " + lda, Color.WHITE);
        if ((redeclarationComputer.getCalculationCase() == 2 || redeclarationComputer.getCalculationCase() == 4) && dispTresh != 0)
            drawHorizontalArrow(dispTreshX, 0.65, dispTreshLength, true, true, "DT", Color.BLACK);
        drawHorizontalArrow(stripEndLX, 0.65, stripEndLLength, false, false, "60", Color.BLACK);
        drawHorizontalArrow(resaMinLX, 0.65, resaMinLLength, true, true, "RL", Color.BLACK);
        drawHorizontalArrow(toraX, 0.75, toraLength, true, true, "TORA = " + tora, Color.PINK);
        drawHorizontalArrow(stripEndTX, 0.75, stripEndTLength, false, false, "60", Color.BLACK);
        drawHorizontalArrow(resaMinTX, 0.75, resaMinTLength, true, true, "RT", Color.BLACK);
        drawHorizontalArrow(asdaX, 0.85, asdaLength, true, true, "ASDA = " + asda, Color.WHITE);
        drawHorizontalArrow(todaX, 0.95, todaLength, true, true, "TODA = " + toda, Color.WHITE);

//        horizontalLines.put("LDA", new double[] {ldaX, 0.58, ldaLength});

        // Slopes get drawn here
        gc.setStroke((Color.BLACK));
        gc.setFill(Color.BLACK);
        if (redeclarationComputer.getCalculationCase() == 1 || redeclarationComputer.getCalculationCase() == 3) {
            gc.setLineWidth(0.005 * canvasHeight);
            gc.strokeLine(resaMinLX * canvasWidth + xOffset, 0.55 * canvasHeight + yOffset, (resaMinLX + resaMinLLength) * canvasWidth + xOffset, 1.005 * imageY);
            gc.strokeLine(resaMinTX * canvasWidth + xOffset, 0.55 * canvasHeight + yOffset, (resaMinTX + resaMinTLength) * canvasWidth + xOffset, 1.005 * imageY);
            gc.setStroke((Color.BLUEVIOLET));
            gc.setFill((Color.BLUEVIOLET));
            gc.strokeLine((resaMinTX + resaMinTLength) * canvasWidth + xOffset, 0.55 * canvasHeight + yOffset, (resaMinTX + resaMinTLength) * canvasWidth + xOffset, 1.005 * imageY);
            Font font = Font.font("Arial", canvasWidth * 0.015);
            gc.setFont(font);
            gc.fillText("H", (resaMinTX + resaMinTLength - 0.014) * canvasWidth + xOffset, 0.55 * canvasHeight - 0.5 * redeclarationComputer.getObstacleHeight() + yOffset);

            //update values
            horizontalLines.clear();
            //array (0,1) leftmost flat point, (2,3) closest point for taking off away, (4,5) start of landing over, scenario, X of the landing over point on the asphalt
            horizontalLines.put("13", new double[]{toraX * canvasWidth + xOffset, 0.55 * canvasHeight, toraLength * canvasWidth, 0.005 * canvasHeight, (resaMinTX + resaMinTLength) * canvasWidth + xOffset , 1.005 * imageY, 1, ldaLength * canvasWidth});

        } else {
            gc.setLineWidth(0.005 * canvasHeight);
            gc.strokeLine((resaMinLX + resaMinLLength) * canvasWidth + xOffset, 0.55 * canvasHeight + yOffset, resaMinLX * canvasWidth + xOffset, 1.005 * imageY);
            gc.strokeLine((resaMinTX + resaMinTLength) * canvasWidth + xOffset, 0.55 * canvasHeight + yOffset, resaMinTX * canvasWidth + xOffset, 1.005 * imageY);
            gc.setStroke((Color.BLUEVIOLET));
            gc.setFill((Color.BLUEVIOLET));
            gc.strokeLine(resaMinTX * canvasWidth + xOffset, 0.55 * canvasHeight + yOffset, resaMinTX * canvasWidth + xOffset, 1.005 * imageY);
            Font font = Font.font("Arial", canvasWidth * 0.015);
            gc.setFont(font);
            gc.fillText("H", (resaMinTX + 0.0023) * canvasWidth + xOffset, 0.55 * canvasHeight - 0.5 * redeclarationComputer.getObstacleHeight() + yOffset);

            //update values
            horizontalLines.clear();

            horizontalLines.put("24", new double[]{resaMinTX * canvasWidth + xOffset,1.005 * imageY,ldaX * canvasWidth + xOffset, 0.55 * canvasHeight, toraX * canvasWidth + xOffset, ldaLength * canvasWidth, toraLength * canvasWidth,2});
        }

    }

/*==================================================================================================================================
//  Helper methods
//================================================================================================================================*/

    private void adjustDrawingSettings(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();

        //  Clears canvas for next paint session
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //  Determines how to set the the canvasWidth and canvasHeight
        if (canvas.getHeight() < canvas.getWidth() / widthToHeightRatio) {
            canvasHeight = canvas.getHeight();
            canvasWidth = widthToHeightRatio * canvasHeight;
            xOffset = (canvas.getWidth() - canvasWidth) / 2;
            yOffset = 0;
        } else {
            canvasWidth = canvas.getWidth();
            canvasHeight = canvasWidth / widthToHeightRatio;
            xOffset = 0;
            yOffset = (canvas.getHeight() - canvasHeight) / 2;
        }

    }

    private void fillRect(Rectangle rect) {
        gc.fillRect(rect.getX() + xOffset, rect.getY() + yOffset, rect.getWidth(), rect.getHeight());
    }

    private void drawRectangle(double x, double y, double width, double height, Color color){
        gc.setFill(color);
        fillRect(new Rectangle(x,y,width,height));
    }

/*==================================================================================================================================
//  Setters
//================================================================================================================================*/

    public void setRunway(Runway runway) {
        this.runway = runway;
    }

    public void setRedeclarationComputer(RedeclarationComputer redeclarationComputer) {
        this.redeclarationComputer = redeclarationComputer;
    }

/*==================================================================================================================================
//  Getters
//================================================================================================================================*/

    //needs to be accessed by the plane animation
    public RedeclarationComputer getRedeclarationComputer() {
        return redeclarationComputer;
    }

    public double getCanvasWidth() {
        return canvasWidth;
    }

    public double getCanvasHeight() {
        return canvasHeight;
    }

    public HashMap<String, double[]> getHorizontalLines() {
        return horizontalLines;
    }
}
