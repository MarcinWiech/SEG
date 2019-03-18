package seg.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import seg.java.CanvasDrawer;
import seg.java.controllers.config.AirportConfigurationController;
import seg.java.models.AirportOld;
import seg.java.models.IllegalValueException;
import seg.java.models.RedeclarationComputer;
import seg.java.models.RunwayOld;


public class DashboardController {
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
    private AirportOld currentAirportOld;
    private RunwayOld currentRunwayOld;

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

        // Prevent user from changing textFields
        toraInitialTextbox.setEditable(false);
        todaInitialTextbox.setEditable(false);
        asdaInitialTextbox.setEditable(false);
        ldaInitialTextbox.setEditable(false);
        toraBDTextArea.setEditable(false);
        todaBDTextArea.setEditable(false);
        asdaBDTextArea.setEditable(false);
        ldaBDTextArea.setEditable(false);
        toraNewTextbox.setEditable(false);
        todaNewTextbox.setEditable(false);
        asdaNewTextbox.setEditable(false);
        ldaNewTextbox.setEditable(false);
        thresholdInitialTextbox.setEditable(false);

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
            redeclarationComputer.setRunwayOld(currentRunwayOld);


            //  Obstacle details get passed to redeclarationComputer
            redeclarationComputer.setObstacleDetails(obstacleXL, obstacleXR, obstacleY, obstacleHeight);

            // We check to see whether we need to recalculate
            if (redeclarationComputer.needsRecalculation(redeclarationComputer.getObstacleXL(), redeclarationComputer.getObstacleXR(), redeclarationComputer.getObstacleY())) {
                redeclare();
                if (redeclarationComputer.getTora() > 5600) {
                    makeNotification("Runway too large", "The redeclared runway is too large for operating", warningIcon);
                } else if (redeclarationComputer.getTora() < 200) {
                    makeNotification("Runway too small", "The redeclared runway is too small for operating", warningIcon);
                }else if(redeclarationComputer.getTora() > redeclarationComputer.getToda()){
                    makeNotification("TODA smaller than TORA", "The recalculated TODA is too small for operating", warningIcon);
                }else if(redeclarationComputer.getToda() < redeclarationComputer.getAsda()){
                    makeNotification("TODA smaller than ASDA ", "The recalculated TODA is too small for operating", warningIcon);
                }else if(redeclarationComputer.calculateClearway() > redeclarationComputer.getTora()/2){
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
        canvasDrawer.setRunwayOld(currentRunwayOld);
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
        if (currentRunwayOld == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a runway!").showAndWait();
            return;
        }
        if (currentRunwayOld.getReciprocalRunwayOld() == null) {
            new Alert(Alert.AlertType.ERROR, "No reciprocal runway exists!").showAndWait();
            return;
        }
        if (toraNewTextbox.getText().equals("")) {
            runwayDroplist.setValue(currentRunwayOld.getReciprocalRunwayOld().getRunwayName());
            return;
        }

        currentRunwayOld = currentRunwayOld.getReciprocalRunwayOld();

        //  Update the dropdown menu as well
        runwayDroplist.setValue(currentRunwayOld.getRunwayName());

        //  Switch computers
        RedeclarationComputer aux = redeclarationComputer;
        redeclarationComputer = reciprocalComputer;
        reciprocalComputer = aux;

        //  New values are displayed
        toraNewTextbox.setText(Double.toString(redeclarationComputer.getTora()));
        todaNewTextbox.setText(Double.toString(redeclarationComputer.getToda()));
        asdaNewTextbox.setText(Double.toString(redeclarationComputer.getAsda()));
        ldaNewTextbox.setText(Double.toString(redeclarationComputer.getLda()));

        //  Canvas drawing gets triggered here
        canvasDrawer.setRedeclarationComputer(redeclarationComputer);
        canvasDrawer.setRunwayOld(currentRunwayOld);
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
            stage = new Stage();
            stage.setTitle("Configure Airports");
            stage.setScene(new Scene(root1));
            stage.show();

            AirportConfigurationController acc = fxmlLoader.getController();
            acc.setAirportOld(currentAirportOld);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    public void setValues(AirportOld airportOld) {

        currentAirportOld = airportOld;
        addToRunwayDroplist();
    }

    public void selectRunway(ActionEvent actionEvent) {
        currentRunwayOld = currentAirportOld.getRunwayHashMap().get(runwayDroplist.getValue().toString());
        toraInitialTextbox.setText(currentRunwayOld.getTora().toString());
        todaInitialTextbox.setText(currentRunwayOld.getToda().toString());
        asdaInitialTextbox.setText(currentRunwayOld.getAsda().toString());
        ldaInitialTextbox.setText(currentRunwayOld.getLda().toString());
        thresholdInitialTextbox.setText(currentRunwayOld.getDisplacedThreshold().toString());
    }

    public void addToRunwayDroplist() {
        for (String runwayName : currentAirportOld.getRunwayHashMap().keySet()) {
            runwayDroplist.getItems().add(runwayName);
        }
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



}

