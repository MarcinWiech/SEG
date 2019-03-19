package seg.java.controllers;

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
            } else if (obstacleHeight > 100) {
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

    public void makeNotification(String title, String text, Image icon) {

        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(new ImageView(icon))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);

        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public void runButtonPressed(){


        //maybe something such as using top down pane and adding a new canvas there
        Button btn = new Button("ClickMe");
        ImageView imageView = new ImageView("/seg/resources/images/side-view-plane.png");
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(25);
        imageView.setY(100);
        imageView.setRotate(180);
        System.out.println("run Pressed");

//        Rectangle rectPath = new Rectangle (0, 0, 40, 40);
//        rectPath.setArcHeight(10);
//        rectPath.setArcWidth(10);
//        rectPath.setFill(Color.ORANGE);

//        Path path = new Path();
//        path.getElements().add(new MoveTo(500,100));
//        path.getElements().add(new MoveTo(550,70));
//        path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
//        path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.millis(4000));
//        pathTransition.setPath(path);
//        pathTransition.setNode(imageView);
//        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pathTransition.setCycleCount(Timeline.INDEFINITE);
//        pathTransition.setAutoReverse(true);
//        pathTransition.play();
//        System.out.println(path.getTranslateX());


        Path path = new Path();

        MoveTo moveTo = new MoveTo();
        moveTo.setX(0.0f);
        moveTo.setY(200);

        HLineTo hLineTo = new HLineTo();
        hLineTo.setX(300);

//        QuadCurveTo quadCurveTo = new QuadCurveTo();
//        quadCurveTo.setX(120.0f);
//        quadCurveTo.setY(60.0f);
//        quadCurveTo.setControlX(100.0f);
//        quadCurveTo.setControlY(0.0f);

        LineTo lineTo = new LineTo();
        lineTo.setX(400);
        lineTo.setY(150);

//        ArcTo arcTo = new ArcTo();
//        arcTo.setX(50.0f);
//        arcTo.setY(50.0f);
//        arcTo.setRadiusX(50.0f);
//        arcTo.setRadiusY(50.0f);

        path.getElements().add(moveTo);
        path.getElements().add(hLineTo);
//        path.getElements().add(quadCurveTo);
        path.getElements().add(lineTo);
//        path.getElements().add(arcTo);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pathTransition.setCycleCount(Timeline.INDEFINITE);
//        pathTransition.setAutoReverse(true);
        pathTransition.play();

//        //Duration = 2.5 seconds
//        Duration duration = Duration.millis(2500);
//        //Create new translate transition
//        TranslateTransition transition = new TranslateTransition(duration, imageView);
//
//        //Move in X axis by +200
//        transition.setByX(200);
//        //Move in Y axis by +100
//        transition.setByY(0);
//
//        //Move in X axis by +200
//        transition.setByX(50);
//        //Move in Y axis by +100
//        transition.setByY(-20);
//
//        transition.play();

        //Go back to previous position after 2.5 seconds
//        transition.setAutoReverse(true);
        //Repeat animation twice
//        transition.setCycleCount(2);

        // works only on one pane
        topDownPane.getChildren().add(imageView);
//        topDownPaneCopy.getChildren().add(btn);

    }

}

