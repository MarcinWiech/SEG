package seg.java.controllers;

import javafx.animation.PathTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import seg.java.*;
import seg.java.controllers.config.AirportConfigurationController;
import seg.java.models.Airport;
import seg.java.models.Obstacle;
import seg.java.models.Runway;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DashboardController {
    Airport currentAirport;
    Runway currentRunway;
    @FXML
    private TextField heightTextbox;
    @FXML
    private Pane topDownPane;
    @FXML
    private Pane sideOnPane;
    @FXML
    private Pane topDownPaneCopy;
    @FXML
    private Pane sideOnPaneCopy;
    @FXML
    private ChoiceBox runwayDroplist;
    @FXML
    private Canvas topDownCanvas;
    @FXML
    private Canvas sideOnCanvas;
    @FXML
    private Canvas topDownCanvasCopy;
    @FXML
    private Canvas sideOnCanvasCopy;
    @FXML
    private TextField xLTextbox;
    @FXML
    private TextField xRTextbox;
    @FXML
    private TextField yTextbox;
    @FXML
    private TextField toraInitialTextbox;
    @FXML
    private TextField todaInitialTextbox;
    @FXML
    private TextField asdaInitialTextbox;
    @FXML
    private TextField ldaInitialTextbox;
    @FXML
    private TextField thresholdInitialTextbox;
    @FXML
    private TextField toraNewTextbox;
    @FXML
    private TextField todaNewTextbox;
    @FXML
    private TextField asdaNewTextbox;
    @FXML
    private TextField ldaNewTextbox;
    @FXML
    private TextArea toraBDTextArea;
    @FXML
    private TextArea todaBDTextArea;
    @FXML
    private TextArea asdaBDTextArea;
    @FXML
    private TextArea ldaBDTextArea;
    @FXML
    private ComboBox obstacleSelector;


    private CanvasDrawer canvasDrawer;
    private RedeclarationComputer redeclarationComputer, reciprocalComputer;
    private double obstacleXL = 0;
    private double obstacleXR = 0;
    private double toraInput, todaInput, asdaInput,ldaInput,dispThresholdInput  ;
    private Image greentickIcon,warningIcon ,switchIcon ;
    private Notification notification;
    private  ArrayList<Obstacle> obstacleArrayList;
    public int pallete = 1;
    private boolean runwayRotationEnabled = false;

/*==================================================================================================================================
//  Initialize
//================================================================================================================================*/

    public void initialize() {
        notification = new Notification();
        //  Main objects get initialized & related preparations
        redeclarationComputer = new RedeclarationComputer();
        reciprocalComputer = new RedeclarationComputer();
        redeclarationComputer.setReciprocalComputer(reciprocalComputer);
        reciprocalComputer.setReciprocalComputer(redeclarationComputer);
        canvasDrawer = new CanvasDrawer(redeclarationComputer);

        XMLLoader loader = XMLLoader.getInstance();
        obstacleArrayList = loader.getObstacleArrayList();
        for(Obstacle ob : obstacleArrayList){
            obstacleSelector.getItems().add(ob.getName());
        }


        //  REALLY IMPORTANT: canvases get resizable by binding them to their parents
        topDownCanvas.widthProperty().bind(topDownPane.widthProperty());
        topDownCanvas.heightProperty().bind(topDownPane.heightProperty());
        topDownCanvas.widthProperty().addListener(event -> {
            canvasDrawer.setTopDownRotation(runwayRotationEnabled);
            canvasDrawer.drawTopDownCanvas(topDownCanvas,getPallete());
            canvasDrawer.setTopDownRotation(false);
        });
        topDownCanvas.heightProperty().addListener(event -> {
            canvasDrawer.setTopDownRotation(runwayRotationEnabled);
            canvasDrawer.drawTopDownCanvas(topDownCanvas,getPallete());
            canvasDrawer.setTopDownRotation(false);
        });

        sideOnCanvas.widthProperty().bind(sideOnPane.widthProperty());
        sideOnCanvas.heightProperty().bind(sideOnPane.heightProperty());
        sideOnCanvas.widthProperty().addListener(event -> canvasDrawer.drawSideOnCanvas(sideOnCanvas,getPallete()));
        sideOnCanvas.heightProperty().addListener(event -> canvasDrawer.drawSideOnCanvas(sideOnCanvas,getPallete()));

        topDownCanvasCopy.widthProperty().bind(topDownPaneCopy.widthProperty());
        topDownCanvasCopy.heightProperty().bind(topDownPaneCopy.heightProperty());
        topDownCanvasCopy.widthProperty().addListener(event -> canvasDrawer.drawTopDownCanvas(topDownCanvasCopy,getPallete()));
        topDownCanvasCopy.heightProperty().addListener(event -> canvasDrawer.drawTopDownCanvas(topDownCanvasCopy,getPallete()));


        sideOnCanvasCopy.widthProperty().bind(sideOnPaneCopy.widthProperty());
        sideOnCanvasCopy.heightProperty().bind(sideOnPaneCopy.heightProperty());
        sideOnCanvasCopy.widthProperty().addListener(event -> canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy,getPallete()));
        sideOnCanvasCopy.heightProperty().addListener(event -> canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy,getPallete()));

        greentickIcon = new Image("/images/greentick.png");
        warningIcon = new Image("/images/alert-triangle-yellow.png");
        switchIcon = new Image("/images/switch.png");
    }

/*==================================================================================================================================
//  Other methods
//================================================================================================================================*/
    /**
     * DRAWING
     **/
    public void redeclareButtonPressed(ActionEvent event) throws IllegalValueException {
        //  Obstacle details
        double obstacleX = 0;
        double obstacleY = 0;
        double obstacleHeight = 0;


        //  Obstacle details get read
        try {
            obstacleHeight = Double.parseDouble(heightTextbox.getText());

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: obstacle height!").showAndWait();
            return;
        }

        try {
            obstacleHeight = Double.parseDouble(heightTextbox.getText());
            if (obstacleHeight < 0) {
                throw new IllegalValueException("negativeheight");
            } else if (obstacleHeight > 35) {
                throw new IllegalValueException("largeheight");
            }

        } catch (IllegalValueException e) {
            return;
        }


        try {
            obstacleXL = Double.parseDouble(xLTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: obstacle XL position!").showAndWait();
            return;
        }

        try {
            obstacleXR = Double.parseDouble(xRTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: obstacle XR position!").showAndWait();
            return;
        }

        try {
            obstacleY = Double.parseDouble(yTextbox.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: obstacle Y position!").showAndWait();
            return;
        }

        //  Recalculation of parameters
        try {
            toraInput = Double.parseDouble(toraInitialTextbox.getText());
            todaInput = Double.parseDouble(todaInitialTextbox.getText());
            asdaInput = Double.parseDouble(asdaInitialTextbox.getText());
            ldaInput = Double.parseDouble(ldaInitialTextbox.getText());
            dispThresholdInput = Double.parseDouble(thresholdInitialTextbox.getText());
            redeclarationComputer.setRunway(currentRunway);


            //  Obstacle details get passed to redeclarationComputer
            redeclarationComputer.setObstacleDetails(obstacleXL, obstacleXR, obstacleY, obstacleHeight);

            // We check to see whether we need to recalculate
            if (redeclarationComputer.needsRecalculation(redeclarationComputer.getObstacleXL(), redeclarationComputer.getObstacleXR(), redeclarationComputer.getObstacleY())) {
                notification.makeNotification("Runway Redeclared", "The runway has now been redeclared.", greentickIcon);
                redeclare();
                if (redeclarationComputer.getTora() > 5600) {
                    notification.makeNotification("Runway too large", "The redeclared runway is too large for operating", warningIcon);
                } else if (redeclarationComputer.getTora() < 200) {
                    notification.makeNotification("Runway too small", "The redeclared runway is too small for operating", warningIcon);
                } else if (redeclarationComputer.getTora() > redeclarationComputer.getToda()) {
                    notification.makeNotification("TODA smaller than TORA", "The recalculated TODA is too small for operating", warningIcon);
                } else if (redeclarationComputer.getToda() < redeclarationComputer.getAsda()) {
                    notification.makeNotification("TODA smaller than ASDA ", "The recalculated TODA is too small for operating", warningIcon);
                } else if (redeclarationComputer.calculateClearway() > redeclarationComputer.getTora() / 2) {
                    notification.makeNotification("Clearway too big", "The recalculated values indicated that clearway is larger than half of TORA", warningIcon);
                }
            }else{
                notification.makeNotification("Runway Wasn't redeclared", "The values imputed cannot lead to a redeclaration", warningIcon);

            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please select a runway!").showAndWait();
            e.printStackTrace();
            return;
        }

    }

    public void switchButtonPressed() {
        //  Check special cases
        if (currentRunway == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a runway!").showAndWait();
            return;
        }
        if (currentRunway.getReciprocalRunway() == null) {
            new Alert(Alert.AlertType.ERROR, "No reciprocal runway exists!").showAndWait();
            return;
        }
        if (toraNewTextbox.getText().equals("")) {
            runwayDroplist.setValue(currentRunway.getReciprocalRunway().getName());
            return;
        }


        setCurrentRunway(currentRunway.getReciprocalRunway());

        //  Switch computers
        RedeclarationComputer aux = redeclarationComputer;
        redeclarationComputer = reciprocalComputer;
        reciprocalComputer = aux;

        //  Canvas drawing gets triggered here
        canvasDrawer.setRedeclarationComputer(redeclarationComputer);
        canvasDrawer.setRunway(currentRunway);
        canvasDrawer.setTopDownRotation(runwayRotationEnabled);
        canvasDrawer.drawTopDownCanvas(topDownCanvas,getPallete());
        canvasDrawer.setTopDownRotation(false);
        canvasDrawer.drawSideOnCanvas(sideOnCanvas,getPallete());
        canvasDrawer.drawTopDownCanvas(topDownCanvasCopy,getPallete());
        canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy,getPallete());

        //  Update the calculations breakdown
        toraBDTextArea.setText(redeclarationComputer.getToraBD());
        todaBDTextArea.setText(redeclarationComputer.getTodaBD());
        asdaBDTextArea.setText(redeclarationComputer.getAsdaBD());
        ldaBDTextArea.setText(redeclarationComputer.getLdaBD());

        notification.makeNotification("Switched runway", "The reciprocal runway is now being viewed", switchIcon);
    }

    private ImageView transformImageView(ImageView imageView, Canvas canvas){

        imageView.setPreserveRatio(true);
        imageView.setFitHeight(canvas.getHeight()*0.03);
        Rotate flipRotationX = new Rotate(180, Rotate.X_AXIS);
        Rotate flipRotationY = new Rotate(180, Rotate.Y_AXIS);
        imageView.getTransforms().addAll(flipRotationX, flipRotationY);
        return imageView;
    }

    /**
     * REDECLARING CALCULATIONS
     */
    private void redeclare() {
        //  We set the parameters and then calculate
        redeclarationComputer.calculate();

        //  New values are displayed
        toraNewTextbox.setText(Double.toString(redeclarationComputer.getTora()));
        todaNewTextbox.setText(Double.toString(redeclarationComputer.getToda()));
        asdaNewTextbox.setText(Double.toString(redeclarationComputer.getAsda()));
        ldaNewTextbox.setText(Double.toString(redeclarationComputer.getLda()));

        //  Canvas drawing gets triggered here
        canvasDrawer.setRunway(currentRunway);
        canvasDrawer.setTopDownRotation(runwayRotationEnabled);
        canvasDrawer.drawTopDownCanvas(topDownCanvas,getPallete());
        canvasDrawer.setTopDownRotation(false);
        canvasDrawer.drawSideOnCanvas(sideOnCanvas,getPallete());
        canvasDrawer.drawTopDownCanvas(topDownCanvasCopy,getPallete());
        canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy,getPallete());

        //  Update the calculations breakdown
        toraBDTextArea.setText(redeclarationComputer.getToraBD());
        todaBDTextArea.setText(redeclarationComputer.getTodaBD());
        asdaBDTextArea.setText(redeclarationComputer.getAsdaBD());
        ldaBDTextArea.setText(redeclarationComputer.getLdaBD());

    }

    /**
     * FUNCTIONALITY
     * **/
    public void setAirport(Airport airport) {
        currentAirport = airport;
        updateRunwayDropList();
    }

    public void updateRunwayDropList() {
        for (Runway runway : currentAirport.getRunways()) {
            runwayDroplist.getItems().add(runway.getName());
        }
    }

    public void selectRunway(ActionEvent actionEvent) {
        try {
            Runway runway = currentAirport.getRunwayByName(runwayDroplist.getValue().toString());
            setCurrentRunway(runway);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    public void setCurrentRunway(Runway runway) {
        currentRunway = runway;
        toraInitialTextbox.setText(Double.toString(runway.getTora()));
        todaInitialTextbox.setText(Double.toString(runway.getToda()));
        asdaInitialTextbox.setText(Double.toString(runway.getAsda()));
        ldaInitialTextbox.setText(Double.toString(runway.getLda()));
        thresholdInitialTextbox.setText(Double.toString(runway.getThreshold()));

        // Update selected box
        runwayDroplist.setValue(runway.getName());
    }

    public void obstacleSelected(){
        Obstacle curr;
        for (Obstacle o: obstacleArrayList) {
            if (o.getName() == obstacleSelector.getValue()) {
                curr = o;
                xLTextbox.setText(Float.toString(curr.getXl()));
                xRTextbox.setText(Float.toString(curr.getXr()));
                heightTextbox.setText(Float.toString(curr.getHeight()));
                yTextbox.setText(Float.toString(curr.getY()));
            }
        }
    }

    /**
     * SIMULATION
     */
    public void simulateLandingButtonPressed(){

        try {
            if(currentRunway == null){
                throw new IllegalValueException("noRunway");
            }
            if(toraBDTextArea.getText() == null || toraBDTextArea.getText().length() == 0){
                throw new IllegalValueException("noRedeclaration");
            }
        }
        catch (Exception e){
            return;
        }

        canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy,getPallete());
        simulateLandingInner(sideOnPaneCopy, sideOnCanvasCopy);
        canvasDrawer.drawSideOnCanvas(sideOnCanvas,getPallete());
        simulateLandingInner(sideOnPane, sideOnCanvas);

    }

    public void simulateTakeOffButtonPressed() {
        try {
            if(currentRunway == null){
                throw new IllegalValueException("noRunway");
            }
            if(ldaBDTextArea.getText() == null || ldaBDTextArea.getText().length() == 0){
                throw new IllegalValueException("noRedeclaration");
            }
        }
        catch (Exception e){
            return;
        }

        canvasDrawer.drawSideOnCanvas(sideOnCanvas , getPallete());
        simulateTakeOffInner(sideOnPane, sideOnCanvas);
        canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy,getPallete());
        simulateTakeOffInner(sideOnPaneCopy, sideOnCanvasCopy);
    }

    private void simulateLandingInner(Pane pane, Canvas canvas){

        while(pane.getChildren().size() > 1){
            pane.getChildren().remove(pane.getChildren().size()-1);
        }

        //set up the image
        ImageView imageView = new ImageView("/images/side-view-plane-simulation.png");
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(canvas.getHeight()*0.03);
        Rotate flipRotationX = new Rotate(180, Rotate.X_AXIS);
        Rotate flipRotationY = new Rotate(180, Rotate.Y_AXIS);
        imageView.getTransforms().addAll(flipRotationX, flipRotationY);

        Path path = new Path();

        if(canvasDrawer.getHorizontalLines().containsKey("13")) {
            path = landingOverSimulation(imageView,path, pane, canvas);
        }


        if(canvasDrawer.getHorizontalLines().containsKey("24")) {
            path = landingTowardsSimulation(imageView,path, pane, canvas);
        }

        //transition settings
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setPath(path);
        pathTransition.setNode(imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();

        pane.getChildren().add(imageView);
    }

    private void simulateTakeOffInner(Pane pane, Canvas canvas){

        while(pane.getChildren().size() > 1){
            pane.getChildren().remove(pane.getChildren().size()-1);
        }

        //set up the image
        ImageView imageView = new ImageView("/images/side-view-plane-simulation.png");
        imageView = transformImageView(imageView,canvas);


        Path path = new Path();

        if(canvasDrawer.getHorizontalLines().containsKey("13")) {
            path = takeOffAwaySimulation(imageView,path, pane, canvas);
        }


        if(canvasDrawer.getHorizontalLines().containsKey("24")) {
            path = takeOffTowardsSimulation(imageView,path, pane, canvas);
        }

        //transition settings
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setPath(path);
        pathTransition.setNode(imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();

        pane.getChildren().add(imageView);
    }

    private Path landingTowardsSimulation(ImageView imageView, Path path, Pane pane, Canvas canvas){

        double[] case24 = canvasDrawer.getHorizontalLines().get("24");

        //points from left to right starting with the top one
        double ldaEndY;
        MoveTo moveTo = new MoveTo();



        if(pane.equals(sideOnPane)){
            ldaEndY = case24[3] + (pane.getHeight() - canvasDrawer.getCanvasHeight()) / 2;
            moveTo.setX(canvas.getWidth() - (pane.getWidth() - canvasDrawer.getCanvasWidth()) / 2);
        }
        else{
            ldaEndY = case24[3] + (pane.getHeight() - canvas.getHeight()) / 2;
            moveTo.setX(canvas.getWidth());
        }

        double upperPointX = case24[0];
        double upperPointY = case24[1];
        double ldaEndX = case24[2];
        double toraEndX = case24[4];
        double ldaStartX = case24[5] + ldaEndX;
        double toraStartX = case24[6] + toraEndX;

        // landing towards
        moveTo.setY(ldaEndY - 2*imageView.getBoundsInParent().getHeight());

        LineTo lineTo = new LineTo();
        lineTo.setX(ldaStartX - imageView.getBoundsInParent().getWidth());
        lineTo.setY(ldaEndY - imageView.getBoundsInParent().getHeight());

        LineTo hLineTo = new LineTo();
        hLineTo.setX(ldaEndX - 0.5*imageView.getBoundsInParent().getWidth());
        hLineTo.setY(ldaEndY - imageView.getBoundsInParent().getHeight());

        path.getElements().add(moveTo);
        path.getElements().add(lineTo);
        path.getElements().add(hLineTo);

        return path;
    }

    private Path takeOffTowardsSimulation(ImageView imageView, Path path, Pane pane, Canvas canvas){

        double[] case24 = canvasDrawer.getHorizontalLines().get("24");

        // points from left to right starting with the top one
        double ldaEndX;
        double ldaEndY;
        if(pane.equals(sideOnPane)){
            ldaEndX = case24[2];
            ldaEndY = case24[3] + (pane.getHeight() - canvasDrawer.getCanvasHeight()) / 2;
        }
        else{
            ldaEndX = case24[2];
            ldaEndY = case24[3];
        }


        double upperPointX = case24[0];
        double upperPointY = case24[1];
        double toraEndX = case24[4];
        double ldaStartX = case24[5] + ldaEndX;
        double toraStartX = case24[6] + toraEndX;

        // takeoff towards
        MoveTo moveTo = new MoveTo();
        moveTo.setX(toraStartX - 0.5*imageView.getBoundsInParent().getWidth());
        moveTo.setY(ldaEndY - imageView.getBoundsInParent().getHeight());

        LineTo lineTo = new LineTo();
        lineTo.setX(toraEndX);
        lineTo.setY(ldaEndY - 1.5*imageView.getBoundsInParent().getHeight());

        LineTo hLineTo = new LineTo();
        hLineTo.setX(upperPointX - 0.5*imageView.getBoundsInParent().getWidth());
        hLineTo.setY(upperPointY - 2.5*imageView.getBoundsInParent().getHeight());

        LineTo flyToTheEnd = new LineTo();

        flyToTheEnd.setX(upperPointX- 4*imageView.getBoundsInParent().getWidth());
        flyToTheEnd.setY(upperPointY - 2.5*imageView.getBoundsInParent().getHeight());

        path.getElements().add(moveTo);
        path.getElements().add(lineTo);
        path.getElements().add(hLineTo);
        path.getElements().add(flyToTheEnd);

        return path;
    }

    private Path takeOffAwaySimulation(ImageView imageView, Path path, Pane pane, Canvas canvas){

        double[] toraData = canvasDrawer.getHorizontalLines().get("13");

        double horizontalLineStartX;
        double horizontalLineStartY;
        double landingOverFirstPointX;

        if(canvas.equals(sideOnCanvas)){
            horizontalLineStartX = toraData[0]  - (sideOnPane.getWidth() - canvasDrawer.getCanvasWidth())/2;
            horizontalLineStartY = toraData[1]  + (sideOnPane.getHeight() - canvasDrawer.getCanvasHeight())/2;
        }
        else{
            horizontalLineStartX = toraData[0];
            horizontalLineStartY = toraData[1];
        }
        double horizontalLineEndX = toraData[0] + toraData[2];

        //take off away
        MoveTo moveTo = new MoveTo();
        moveTo.setX(horizontalLineEndX - 1.5*imageView.getBoundsInParent().getWidth());
        moveTo.setY(horizontalLineStartY- imageView.getBoundsInParent().getHeight());

        LineTo lineTo = new LineTo();
        lineTo.setX(horizontalLineStartX - 0.5*imageView.getBoundsInParent().getWidth());
        lineTo.setY(horizontalLineStartY- 2*imageView.getBoundsInParent().getHeight());

        path.getElements().add(moveTo);
        path.getElements().add(lineTo);

        return path;
    }

    private Path landingOverSimulation(ImageView imageView, Path path, Pane pane, Canvas canvas){

        double[] toraData = canvasDrawer.getHorizontalLines().get("13");

        //declare variables for path;

        double horizontalLineStartX;
        double horizontalLineStartY;
        double landingOverFirstPointX;

        if(canvas.equals(sideOnCanvas)){
            horizontalLineStartX = toraData[0]  - (sideOnPane.getWidth() - canvasDrawer.getCanvasWidth())/2;
            horizontalLineStartY = toraData[1]  + (sideOnPane.getHeight() - canvasDrawer.getCanvasHeight())/2;
            landingOverFirstPointX = toraData[4] - (sideOnPane.getWidth() - canvasDrawer.getCanvasWidth())/2;
        }
        else{
            horizontalLineStartX = toraData[0];
            horizontalLineStartY = toraData[1];
            landingOverFirstPointX = toraData[4];
        }
        double horizontalLineEndX = toraData[0] + toraData[2];
        double landingOverFirstPointY = toraData[5]; // height of the obstacle * 1.5
        double landingOverFirstAsphaltPointX = toraData[7] + toraData[0];

//        landing over
        MoveTo moveTo = new MoveTo();
        moveTo.setX(landingOverFirstPointX);
        moveTo.setY(landingOverFirstPointY - 2*imageView.getBoundsInParent().getHeight());

        LineTo lineTo = new LineTo();
        lineTo.setX(landingOverFirstAsphaltPointX - imageView.getBoundsInParent().getWidth());
        lineTo.setY(horizontalLineStartY - 2*imageView.getBoundsInParent().getHeight());

        LineTo hLineTo = new LineTo();
        hLineTo.setX(horizontalLineStartX - 0.5*imageView.getBoundsInParent().getWidth());
        hLineTo.setY(horizontalLineStartY - imageView.getBoundsInParent().getHeight());

        path.getElements().add(moveTo);
        path.getElements().add(lineTo);
        path.getElements().add(hLineTo);

        return path;
    }

    public void clearSimulation(){
        while(sideOnPane.getChildren().size() > 1)
            sideOnPane.getChildren().remove(sideOnPane.getChildren().size()-1);
        sideOnPaneCopy.getChildren().remove(sideOnPaneCopy.getChildren().size()-1);
    }

    /**
     * OPENING VIEWS
     * **/
    public void switchAirport(ActionEvent actionEvent) {
        openFXML("/views/airportSelection.fxml","Switch Airport");
    }

    public void configureAirports(ActionEvent actionEvent) {
        openFXML("/views/config/airportConfig.fxml","Configure Airport");
    }

    public void openEmailForm(ActionEvent actionEvent) throws IOException {
        openFXML("/views/emailForm.fxml", "Share");
        CreatePDF createPDF = new CreatePDF(redeclarationComputer, currentRunway, null);
    }

    public void exportPDF(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        Stage stage = (Stage) thresholdInitialTextbox.getScene().getWindow();
        FileChooser.ExtensionFilter pdfExtensionFilter = new FileChooser.ExtensionFilter("PDF - Portable Document Format (.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(pdfExtensionFilter);
        fileChooser.setSelectedExtensionFilter(pdfExtensionFilter);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            CreatePDF createPDF = new CreatePDF(redeclarationComputer, currentRunway, file);
            notification.makeNotification("PDF save successful", "PDF has been successfully saved", greentickIcon);
        }
    }

    public void openFXML(String path, String title) {
        try {
            Stage stage = (Stage) yTextbox.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource( path));
            Parent root1 = fxmlLoader.load();

            if (title == "Configure Airport") {
                AirportConfigurationController acc = fxmlLoader.getController();
                acc.setAirport(currentAirport);
            } else if (title == "Share"){
                EmailController ec = fxmlLoader.getController();
                ec.setRunway(currentRunway);
            }

            stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong with loading the view :(").showAndWait();
        }
    }

    public void changeColorScheme(ActionEvent actionEvent) {
        if(pallete == 1){
            pallete = 2;
        }else{
            pallete = 1;
        }
        //  Canvas drawing gets triggered here
        canvasDrawer.setRunway(currentRunway);
        canvasDrawer.drawTopDownCanvas(topDownCanvas,getPallete());
        canvasDrawer.drawSideOnCanvas(sideOnCanvas,getPallete());
        canvasDrawer.drawTopDownCanvas(topDownCanvasCopy,getPallete());
        canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy,getPallete());
    }

    public int getPallete(){
        return pallete;
    }

    public void setPallete(int pallete){
        this.pallete = pallete;
    }


    /**
     * Action to save the TOP DOWN view as an image
     * @param actionEvent
     * @throws IOException
     */
    public void saveTopDown(ActionEvent actionEvent) throws IOException {



        /**
         * Setting up the extension
         */
        FileChooser.ExtensionFilter e1 = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        FileChooser.ExtensionFilter e2 = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter e3 = new FileChooser.ExtensionFilter("jpeg files (*.jpeg)", "*.jpeg");


        FileChooser f= new FileChooser();
        Stage stage = (Stage) topDownCanvas.getScene().getWindow();
        f.getExtensionFilters().add(e1);
        f.getExtensionFilters().add(e2);
        f.getExtensionFilters().add(e3);


        File file = f.showSaveDialog(stage);

        if(file != null){

                WritableImage img = new WritableImage((int)topDownCanvas.getWidth(), (int)topDownCanvas.getHeight());
                topDownCanvas.snapshot(null,img);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(img, null);
                ImageIO.write(renderedImage,"png",file);

        }

        notification.makeNotification("Top Down view saved", "The view has been saved as image", greentickIcon);

    }


    /**
     * Action to save the SIDE ON view as an image
     * @param actionEvent
     * @throws IOException
     */
    public void saveSideOn(ActionEvent actionEvent) throws IOException {


        /**
         * Setting up the extension
         */
        FileChooser.ExtensionFilter e1 = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        FileChooser.ExtensionFilter e2 = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter e3 = new FileChooser.ExtensionFilter("jpeg files (*.jpeg)", "*.jpeg");

        FileChooser f = new FileChooser();
        Stage stage = (Stage) sideOnCanvas.getScene().getWindow();
        f.getExtensionFilters().add(e1);
        f.getExtensionFilters().add(e2);
        f.getExtensionFilters().add(e3);

        File file = f.showSaveDialog(stage);

        if(file != null){

                WritableImage img = new WritableImage((int)topDownCanvas.getWidth(), (int)topDownCanvas.getHeight());
                sideOnCanvas.snapshot(null, img);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(img, null);
                ImageIO.write(renderedImage, "png", file);

        }
        notification.makeNotification("Side on view saved", "The view has been saved as image", greentickIcon);

    }

    public void enableDisableRunwayRotation()
    {
        runwayRotationEnabled = !runwayRotationEnabled;

        //  Canvas drawing gets triggered here
        if(currentRunway != null && redeclarationComputer != null && redeclarationComputer.getRunway() != null) {
            canvasDrawer.setRedeclarationComputer(redeclarationComputer);
            canvasDrawer.setRunway(currentRunway);
            canvasDrawer.setTopDownRotation(runwayRotationEnabled);
            canvasDrawer.drawTopDownCanvas(topDownCanvas, getPallete());
            canvasDrawer.setTopDownRotation(false);
            canvasDrawer.drawSideOnCanvas(sideOnCanvas, getPallete());
            canvasDrawer.drawTopDownCanvas(topDownCanvasCopy, getPallete());
            canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy, getPallete());
        }
    }

    public void runwayRotationButton(ActionEvent actionEvent) {
        runwayRotationEnabled = !runwayRotationEnabled;

        //  Canvas drawing gets triggered here
        if(currentRunway != null && redeclarationComputer != null && redeclarationComputer.getRunway() != null) {
            canvasDrawer.setRedeclarationComputer(redeclarationComputer);
            canvasDrawer.setRunway(currentRunway);
            canvasDrawer.setTopDownRotation(runwayRotationEnabled);
            canvasDrawer.drawTopDownCanvas(topDownCanvas, getPallete());
            canvasDrawer.setTopDownRotation(false);
            canvasDrawer.drawSideOnCanvas(sideOnCanvas, getPallete());
            canvasDrawer.drawTopDownCanvas(topDownCanvasCopy, getPallete());
            canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy, getPallete());
        }
    }
}

