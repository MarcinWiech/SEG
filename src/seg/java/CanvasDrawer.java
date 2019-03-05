package seg.java;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class CanvasDrawer
{

//==================================================================================================================================
//  Fields
//==================================================================================================================================

    private GraphicsContext gc;

    private double widthToHeightRatio = 2.3;
    private double canvasWidth;
    private double canvasHeight;
    private double xOffset;
    private double yOffset;

    //for displaying obstacles dynamically
    private boolean drawSideOnObstacle = false;
    private boolean drawTopDownObstacle = false;
    private double xRelativePosition;
    private double yRelativePosition;



//==================================================================================================================================
//  Constructors
//==================================================================================================================================

    public CanvasDrawer()
    {

    }

//==================================================================================================================================
//  Drawing methods
//==================================================================================================================================

    public void drawTopDownCanvas(Canvas canvas)
    {

        adjustDrawingSettings(canvas);

        //  Grass area gets set
        Rectangle grassRect = new Rectangle(0.02 * canvasWidth, 0.02 * canvasHeight, 0.96 * canvasWidth, 0.96 * canvasHeight);

        //  Purple area gets set
        Rectangle runwayStripRect = new Rectangle(0.07 * canvasWidth, 0.07 * canvasHeight, 0.86 * canvasWidth, 0.86 * canvasHeight);

        //  "Vertical dark blue are gets set
        Rectangle clearedGradedRect1 = new Rectangle(0.22 * canvasWidth, 0.12 * canvasHeight, 0.56 * canvasWidth, 0.76 * canvasHeight);

        //  "Horizontal" dark blue area gets set
        Rectangle clearedGradedRect2 = new Rectangle(0.07 * canvasWidth, 0.23 * canvasHeight, 0.86 * canvasWidth, 0.56 * canvasHeight);

        //  Asphalt area gets set
        Rectangle runwayRect = new Rectangle(0.1 * canvasWidth, 0.27 * canvasHeight, 0.8 * canvasWidth, 0.46 * canvasHeight);

        //  Layers are drawn - from the bottom up
        gc.setFill(Color.GREEN);
        fillRect(grassRect);
        gc.setFill(Color.MEDIUMPURPLE);
        fillRect(runwayStripRect);
        gc.setFill(Color.DARKBLUE);
        fillRect(clearedGradedRect1);
        fillRect(clearedGradedRect2);
        gc.setFill(Color.GRAY);
        fillRect(runwayRect);

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
        double centerlineX = 0.23 * canvasWidth;
        double centerlineY = 0.5 * canvasHeight;
        double stripWidth = 0.02 * canvasWidth;
        double stripHeight = 0.008 * canvasHeight;
        gc.setFill(Color.WHITE);
        while (centerlineX < canvasWidth * 0.78) {
            if (centerlineX > 0.8 * canvasWidth) {
                stripWidth = 0.82 - centerlineX;
                Rectangle whiteRect = new Rectangle(centerlineX, centerlineY, stripWidth, stripHeight);
                fillRect(whiteRect);
                break;
            }
            Rectangle whiteRect = new Rectangle(centerlineX, centerlineY, stripWidth, stripHeight);
            fillRect(whiteRect);
            centerlineX += stripWidth * 2;
        }

        //  Sets of lines that look like crosswalks get drawn
        stripWidth = 0.08 * canvasWidth;
        stripHeight = 0.006 * canvasHeight;
        double y = 0.30 * canvasHeight;
        double stripStartLeftX = 0.12 * canvasWidth;
        double stripStartRightX = 0.80 * canvasWidth;

        gc.setFill(Color.WHITE);

        while (y + 4 < 0.72 * canvasHeight)
        {
            Rectangle whiteRect = new Rectangle(stripStartLeftX, y, stripWidth, stripHeight);
            fillRect(whiteRect); // left one gets drawn

            whiteRect = new Rectangle(stripStartRightX, y, stripWidth, stripHeight);
            fillRect(whiteRect); // right one gets drawn
            y += stripHeight * 3;
        }

//        //  Draws the plane - the image needs to be in the assets folder
//        ImageView imageView = new ImageView("/seg/resources/images/top-down-plane.png");
//        imageView.setRotate(-90);
//        imageView.setPreserveRatio(true);
//        imageView.setFitHeight(canvasHeight * 0.2);
//        SnapshotParameters parameters = new SnapshotParameters();
//        parameters.setFill(Color.TRANSPARENT);
//        Image image1 = imageView.snapshot(parameters, null);
//        gc.drawImage(image1, canvasWidth * 0.7 + xOffset, canvasHeight * 0.14 + yOffset);

        if(drawTopDownObstacle)
        {
            drawObstacleTopDown(canvas);
        }

        // gc.scale(2,2); // can be used for zoom later on
    }

    public void drawSideOnCanvas(Canvas canvas)
    {

        adjustDrawingSettings(canvas);

        //  Sky layer gets set
        Rectangle skyRect = new Rectangle(0.02 * canvasWidth, 0.02 * canvasHeight, 0.96 * canvasWidth, 0.96 * canvasHeight);

        //  Grass layer gets set
        Rectangle grassRect = new Rectangle(0.02 * canvasWidth, 0.55 * canvasHeight, 0.96 * canvasWidth, 0.43 * canvasHeight);

        //  Asphalt layer gets set
        Rectangle asphaltRect = new Rectangle(0.1 * canvasWidth, 0.55 * canvasHeight, 0.80 * canvasWidth, 0.03 * canvasHeight);
//        Rectangle asphaltRect = new Rectangle(0.02 * canvasWidth, 0.55 * canvasHeight, 0.96 * canvasWidth, 0.03 * canvasHeight);

        //  Layers are drawn - from the bottom up
        gc.setFill(Color.SKYBLUE);
        fillRect(skyRect);
        gc.setFill(Color.GREEN);
        fillRect(grassRect);
        gc.setFill(Color.GRAY);
        fillRect(asphaltRect);

        if(drawSideOnObstacle)
        {
            drawObstacleSideOn(canvas);
        }

    }

    private void drawObstacleSideOn(Canvas canvas)
    {
        //  Draws the plane - the image needs to be in the assets folder
        gc = canvas.getGraphicsContext2D();
        ImageView imageView = new ImageView("/seg/resources/images/side-view-plane.png");
        //imageView.setRotate(-4);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(canvasHeight * 0.06);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        Image image1 = imageView.snapshot(parameters, null);
        gc.drawImage(image1, canvasWidth*(0.90 - xRelativePosition*(0.8))+ xOffset, canvasHeight * 0.50 + yOffset);
    }

    private void drawObstacleTopDown(Canvas canvas)
    {
        //  Draws the plane - the image needs to be in the assets folder
        gc = canvas.getGraphicsContext2D();
        ImageView imageView = new ImageView("/seg/resources/images/top-down-plane.png");
        imageView.setRotate(-90);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(canvasHeight * 0.2);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        Image image1 = imageView.snapshot(parameters, null);
        //asphalt strip points (0.1,0.27)(0.86,0.46) i had to add 0.155 and *2.3 most probably because of the image properties

        gc.drawImage(image1, canvasWidth*(0.90 - xRelativePosition*(0.8))+ xOffset, canvasHeight*((0.46-0.27)/2+ 0.155 - ((0.46-0.27)*yRelativePosition)*2.3) + yOffset);
    }

//==================================================================================================================================
//  Helper methods
//==================================================================================================================================

    private void adjustDrawingSettings(Canvas canvas)
    {
        gc = canvas.getGraphicsContext2D();

        //  Clears canvas for next paint session
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //  Determines how to set the the canvasWidth and canvasHeight
        if(canvas.getHeight() < canvas.getWidth() / widthToHeightRatio)
        {
            canvasHeight = canvas.getHeight();
            canvasWidth = widthToHeightRatio * canvasHeight;
            xOffset = (canvas.getWidth() - canvasWidth) / 2;
            yOffset = 0;
        }
        else
        {
            canvasWidth = canvas.getWidth();
            canvasHeight = canvasWidth / widthToHeightRatio;
            xOffset = 0;
            yOffset = (canvas.getHeight() - canvasHeight) / 2;
        }

    }

    private void fillRect(Rectangle rect)
    {
        gc.fillRect(rect.getX() + xOffset, rect.getY() + yOffset, rect.getWidth(), rect.getHeight());
    }

    public boolean isDrawSideOnObstacle() {
        return drawSideOnObstacle;
    }

    public void setDrawSideOnObstacle(boolean drawSideOnObstacle) {
        this.drawSideOnObstacle = drawSideOnObstacle;
    }

    public void drawTopDownObstacle(boolean drawTopDownObstacle, double xRelativePosition, double yRelativePosition) {
        this.drawTopDownObstacle = drawTopDownObstacle;
        this.xRelativePosition = xRelativePosition;
        this.yRelativePosition = yRelativePosition;
    }
}
