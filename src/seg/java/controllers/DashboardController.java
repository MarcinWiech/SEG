package seg.java.controllers;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import seg.java.CanvasDrawer;
import seg.java.controllers.config.AirportConfigurationController;
import seg.java.models.Airport;
import seg.java.IllegalValueException;
import seg.java.RedeclarationComputer;
import seg.java.models.Runway;


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
    private CanvasDrawer canvasDrawer;
    private RedeclarationComputer redeclarationComputer;
    private RedeclarationComputer reciprocalComputer;
    private double obstacleXL = 0;
    private double obstacleXR = 0;
    private double toraInput;
    private double todaInput;
    private double asdaInput;
    private double ldaInput;
    private double dispThresholdInput;
    private Image greentickIcon;
    private Image warningIcon;
    private Image switchIcon;

/*==================================================================================================================================
//  Initialize
//================================================================================================================================*/

    public void initialize() {
        //  Main objects get initialized & related preparations
        redeclarationComputer = new RedeclarationComputer();
        reciprocalComputer = new RedeclarationComputer();
        redeclarationComputer.setReciprocalComputer(reciprocalComputer);
        reciprocalComputer.setReciprocalComputer(redeclarationComputer);
        canvasDrawer = new CanvasDrawer(redeclarationComputer);


        //  REALLY IMPORTANT: canvases get resizable by binding them to their parents
        topDownCanvas.widthProperty().bind(topDownPane.widthProperty());
        topDownCanvas.heightProperty().bind(topDownPane.heightProperty());
        topDownCanvas.widthProperty().addListener(event -> canvasDrawer.drawTopDownCanvas(topDownCanvas));
        topDownCanvas.heightProperty().addListener(event -> canvasDrawer.drawTopDownCanvas(topDownCanvas));

        sideOnCanvas.widthProperty().bind(sideOnPane.widthProperty());
        sideOnCanvas.heightProperty().bind(sideOnPane.heightProperty());
        sideOnCanvas.widthProperty().addListener(event -> canvasDrawer.drawSideOnCanvas(sideOnCanvas));
        sideOnCanvas.heightProperty().addListener(event -> canvasDrawer.drawSideOnCanvas(sideOnCanvas));

        topDownCanvasCopy.widthProperty().bind(topDownPaneCopy.widthProperty());
        topDownCanvasCopy.heightProperty().bind(topDownPaneCopy.heightProperty());
        topDownCanvasCopy.widthProperty().addListener(event -> canvasDrawer.drawTopDownCanvas(topDownCanvasCopy));
        topDownCanvasCopy.heightProperty().addListener(event -> canvasDrawer.drawTopDownCanvas(topDownCanvasCopy));


        sideOnCanvasCopy.widthProperty().bind(sideOnPaneCopy.widthProperty());
        sideOnCanvasCopy.heightProperty().bind(sideOnPaneCopy.heightProperty());
        sideOnCanvasCopy.widthProperty().addListener(event -> canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy));
        sideOnCanvasCopy.heightProperty().addListener(event -> canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy));

        greentickIcon = new Image("/seg/resources/images/greentick.png");
        warningIcon = new Image("/seg/resources/images/alert-triangle-yellow.png");
        switchIcon = new Image("/seg/resources/images/switch.png");
    }

/*==================================================================================================================================
//  Other methods
//================================================================================================================================*/

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
                redeclare();
                if (redeclarationComputer.getTora() > 5600) {
                    makeNotification("Runway too large", "The redeclared runway is too large for operating", warningIcon);
                } else if (redeclarationComputer.getTora() < 200) {
                    makeNotification("Runway too small", "The redeclared runway is too small for operating", warningIcon);
                } else if (redeclarationComputer.getTora() > redeclarationComputer.getToda()) {
                    makeNotification("TODA smaller than TORA", "The recalculated TODA is too small for operating", warningIcon);
                } else if (redeclarationComputer.getToda() < redeclarationComputer.getAsda()) {
                    makeNotification("TODA smaller than ASDA ", "The recalculated TODA is too small for operating", warningIcon);
                } else if (redeclarationComputer.calculateClearway() > redeclarationComputer.getTora() / 2) {
                    makeNotification("Clearway too big", "The recalculated values indicated that clearway is larger than half of TORA", warningIcon);
                }
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please select a runway!").showAndWait();
            e.printStackTrace();
            return;
        }
        makeNotification("Runway Redeclared", "The runway has now been redeclared.", greentickIcon);
    }

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
        canvasDrawer.drawTopDownCanvas(topDownCanvas);
        canvasDrawer.drawSideOnCanvas(sideOnCanvas);
        canvasDrawer.drawTopDownCanvas(topDownCanvasCopy);
        canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy);

        //  Update the calculations breakdown
        toraBDTextArea.setText(redeclarationComputer.getToraBD());
        todaBDTextArea.setText(redeclarationComputer.getTodaBD());
        asdaBDTextArea.setText(redeclarationComputer.getAsdaBD());
        ldaBDTextArea.setText(redeclarationComputer.getLdaBD());

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
        canvasDrawer.drawTopDownCanvas(topDownCanvas);
        canvasDrawer.drawSideOnCanvas(sideOnCanvas);
        canvasDrawer.drawTopDownCanvas(topDownCanvasCopy);
        canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy);

        //  Update the calculations breakdown
        toraBDTextArea.setText(redeclarationComputer.getToraBD());
        todaBDTextArea.setText(redeclarationComputer.getTodaBD());
        asdaBDTextArea.setText(redeclarationComputer.getAsdaBD());
        ldaBDTextArea.setText(redeclarationComputer.getLdaBD());

        makeNotification("Switched runway", "The reciprocal runway is now being viewed", switchIcon);
    }

    public void switchAirport(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) yTextbox.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/airportSelection.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Switch Airport");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    public void configureAirports(ActionEvent actionEvent) {

        try {
            Stage stage = (Stage) yTextbox.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/config/airportConfig.fxml"));


            Parent root1 = fxmlLoader.load();
            AirportConfigurationController acc = fxmlLoader.getController();
            acc.setAirport(currentAirport);

            stage = new Stage();
            stage.setTitle("Configure Airport");
            stage.setScene(new Scene(root1));
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

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

    private void makeNotification(String title, String text, Image icon) {

        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(new ImageView(icon))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);

        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public void simulateLandingButtonPressed(){

        while(sideOnPane.getChildren().size() > 1)
            sideOnPane.getChildren().remove(sideOnPane.getChildren().size()-1);



        //set up the image
        ImageView imageView = new ImageView("/seg/resources/images/side-view-plane.png");
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(sideOnCanvas.getHeight()*0.03);
        Rotate flipRotationX = new Rotate(180, Rotate.X_AXIS);
        Rotate flipRotationY = new Rotate(180, Rotate.Y_AXIS);
        imageView.getTransforms().addAll(flipRotationX, flipRotationY);

        Path path = new Path();

        if(canvasDrawer.getHorizontalLines().containsKey("13")) {
            path = landingOverSimulation(imageView,path);
        }


        if(canvasDrawer.getHorizontalLines().containsKey("24")) {
            path = landingTowardsSimulation(imageView,path);
        }

        //transition settings
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setPath(path);
        pathTransition.setNode(imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();

        sideOnPane.getChildren().add(imageView);
    }

    public void simulateTakeOffButtonPressed() {
                //get runway direction
        try {

            if(currentRunway == null){
                throw new IllegalValueException("noRunway");
            }
        }
        catch (Exception e){
            return;
        }

        while(sideOnPane.getChildren().size() > 1)
            sideOnPane.getChildren().remove(sideOnPane.getChildren().size()-1);



        //set up the image
        ImageView imageView = new ImageView("/seg/resources/images/side-view-plane.png");
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(sideOnCanvas.getHeight()*0.03);
        Rotate flipRotationX = new Rotate(180, Rotate.X_AXIS);
        Rotate flipRotationY = new Rotate(180, Rotate.Y_AXIS);
        imageView.getTransforms().addAll(flipRotationX, flipRotationY);

        Path path = new Path();

        if(canvasDrawer.getHorizontalLines().containsKey("13")) {
            path = takeOffAwaySimulation(imageView,path);
        }


        if(canvasDrawer.getHorizontalLines().containsKey("24")) {
            path = takeOffTowardsSimulation(imageView,path);
        }

        //transition settings
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setPath(path);
        pathTransition.setNode(imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();

        sideOnPane.getChildren().add(imageView);
    }

    public void clearSimulation(){
        while(sideOnPane.getChildren().size() > 1)
            sideOnPane.getChildren().remove(sideOnPane.getChildren().size()-1);
    }

    private Path landingTowardsSimulation(ImageView imageView, Path path){

        double[] case24 = canvasDrawer.getHorizontalLines().get("24");

        //points from left to right starting with the top one
        double upperPointX = case24[0];
        double upperPointY = case24[1];
        double ldaEndX = case24[2];
        double ldaEndY = case24[3] + (sideOnPane.getHeight() - canvasDrawer.getCanvasHeight()) / 2; //gitara
        double toraEndX = case24[4];
        double ldaStartX = case24[5] + ldaEndX;
        double toraStartX = case24[6] + toraEndX;

        // landing towards
        MoveTo moveTo = new MoveTo();
        moveTo.setX(sideOnCanvas.getWidth() - (sideOnPane.getWidth() - canvasDrawer.getCanvasWidth()) / 2);
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

    private Path takeOffTowardsSimulation(ImageView imageView, Path path){

        double[] case24 = canvasDrawer.getHorizontalLines().get("24");

        // points from left to right starting with the top one
        double upperPointX = case24[0];
        double upperPointY = case24[1];
        double ldaEndX = case24[2];
        double ldaEndY = case24[3] + (sideOnPane.getHeight() - canvasDrawer.getCanvasHeight()) / 2;
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
        flyToTheEnd.setX(0);
        flyToTheEnd.setY(upperPointY - 2.5*imageView.getBoundsInParent().getHeight());

        path.getElements().add(moveTo);
        path.getElements().add(lineTo);
        path.getElements().add(hLineTo);
        path.getElements().add(flyToTheEnd);

        return path;
    }

    private Path takeOffAwaySimulation(ImageView imageView, Path path){

        double[] toraData = canvasDrawer.getHorizontalLines().get("13");

        //declare variables for path;
        double horizontalLineStartX = toraData[0] - (sideOnPane.getWidth() - canvasDrawer.getCanvasWidth()) / 2;
        double horizontalLineStartY = toraData[1] + (sideOnPane.getHeight() - canvasDrawer.getCanvasHeight()) / 2;
        double horizontalLineEndX = toraData[0] + toraData[2];
        double landingOverFirstPointX = toraData[4] - (sideOnPane.getWidth() - canvasDrawer.getCanvasWidth()) / 2; //end of the canvas
        double landingOverFirstPointY = toraData[5];
        double landingOverFirstAsphaltPointX = toraData[7] + toraData[0];

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

    private Path landingOverSimulation(ImageView imageView, Path path){

        double[] toraData = canvasDrawer.getHorizontalLines().get("13");


        //declare variables for path;
        double horizontalLineStartX = toraData[0] - (sideOnPane.getWidth() - canvasDrawer.getCanvasWidth()) / 2;
        double horizontalLineStartY = toraData[1] + (sideOnPane.getHeight() - canvasDrawer.getCanvasHeight()) / 2;
        double horizontalLineEndX = toraData[0] + toraData[2];
        double landingOverFirstPointX = toraData[4] - (sideOnPane.getWidth() - canvasDrawer.getCanvasWidth()) / 2; //end of the canvas
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

}

