package seg.java.controllers;

import seg.java.Airport;
import seg.java.Runway;
import seg.java.RedeclarationComputer;
import seg.java.XMLReaderDOM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;                                                  // for Button and stuff
import javafx.scene.canvas.*;                                                   // for Canvas and stuff
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seg.java.CanvasDrawer;

public class DashboardController {
    public TextField widthTextbox;
    public TextField heightTextbox;
    public Pane topDownPane;
    public Pane sideOnPane;
    public ChoiceBox runwayDroplist;

    @FXML private Canvas topDownCanvas;
    @FXML private Canvas sideOnCanvas;

    @FXML private TextField xTextbox;
    @FXML private TextField yTextbox;

    @FXML private TextField toraInitialTextbox;
    @FXML private TextField todaInitialTextbox;
    @FXML private TextField asdaInitialTextbox;
    @FXML private TextField ldaInitialTextbox;
    @FXML private TextField thresholdInitialTextbox;
    @FXML private TextField actionInitialTextbox;
    @FXML private TextField toraNewTextbox;
    @FXML private TextField todaNewTextbox;
    @FXML private TextField asdaNewTextbox;
    @FXML private TextField ldaNewTextbox;
    @FXML private TextField thresholdNewTextbox;

    private XMLReaderDOM xmlReaderDOM;
    private CanvasDrawer canvasDrawer;
    private RedeclarationComputer redeclarationComputer;
    private Airport airport;
    private Runway runway;

    public void initialize() {
        /** Main objects get initialized & related preparations */
        canvasDrawer = new CanvasDrawer();
        canvasDrawer.setCanvases(topDownCanvas, sideOnCanvas);


        /** Empty views get drawn **/


        /** REALLY IMPORTANT: the following 4 make the topDownPane resizable by binding it to topDownPane **/
        topDownCanvas.widthProperty().bind(topDownPane.widthProperty());
        //topDownCanvas.heightProperty().bind(topDownPane.heightProperty());
        topDownCanvas.widthProperty().addListener(event -> canvasDrawer.drawTopDownCanvas());
        //topDownCanvas.heightProperty().addListener(event -> canvasDrawer.drawTopDownCanvas());

        sideOnCanvas.widthProperty().bind(sideOnPane.widthProperty());
        //sideOnCanvas.heightProperty().bind(sideOnPane.heightProperty());
        sideOnCanvas.widthProperty().addListener(event -> canvasDrawer.drawSideOnCanvas());
        //sideOnCanvas.heightProperty().addListener(event -> canvasDrawer.drawSideOnCanvas());
    }

    public void redeclareButtonPressed(ActionEvent event) throws RedeclarationComputer.InvalidActionException {
        //initialize();

        System.out.println("Canvas width = " + topDownCanvas.getWidth());
        System.out.println("Canvas height = " + topDownCanvas.getHeight());



        /** Recalculation of parameters */
        double toraInput = Double.parseDouble(toraInitialTextbox.getText());
        double todaInput = Double.parseDouble(todaInitialTextbox.getText());
        double asdaInput = Double.parseDouble(asdaInitialTextbox.getText());
        double ldaInput = Double.parseDouble(ldaInitialTextbox.getText());
        double dispThresholdInput = Double.parseDouble(thresholdInitialTextbox.getText());
        double actionInput = Double.parseDouble(actionInitialTextbox.getText());

        redeclarationComputer = new RedeclarationComputer(toraInput,todaInput,asdaInput,ldaInput,dispThresholdInput,actionInput);
        /** Obstacle details */
        double obstacleX = Double.parseDouble(xTextbox.getText());
        double obstacleY = Double.parseDouble(yTextbox.getText());
        double obstacleHeight = Double.parseDouble(heightTextbox.getText());
        redeclarationComputer.setObstacleDetails(obstacleX, obstacleY, obstacleHeight);
        /** This block of code will also need the obstacle's details */
        if (redeclarationComputer.needsRecalculation(redeclarationComputer.getObstacleX(), redeclarationComputer.getObstacleY())) {
            redeclarationComputer.resetParameters(toraInput, todaInput, asdaInput, ldaInput);
            redeclarationComputer.calculate();
        }

        toraNewTextbox.setText(Double.toString(redeclarationComputer.getTora()));
        todaNewTextbox.setText(Double.toString(redeclarationComputer.getToda()));
        asdaNewTextbox.setText(Double.toString(redeclarationComputer.getAsda()));
        ldaNewTextbox.setText(Double.toString(redeclarationComputer.getLda()));
        thresholdNewTextbox.setText(Double.toString(redeclarationComputer.getDispTresh()));

        /** Canvas drawing gets triggered here */
        canvasDrawer.drawTopDownCanvas();
        canvasDrawer.drawSideOnCanvas();
    }

    public void switchAirport(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) yTextbox.getScene().getWindow();
            stage.close();
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

    public void setValues(XMLReaderDOM xmlReaderDOM, Airport airport) {
        this.xmlReaderDOM = xmlReaderDOM;
        this.airport = airport;
        addToRunwayDroplist();
    }

    public void selectRunway(ActionEvent actionEvent) {
        runway = airport.getRunwayHashMap().get(runwayDroplist.getValue().toString());
        toraInitialTextbox.setText(runway.getTora().toString());
        todaInitialTextbox.setText(runway.getToda().toString());
        asdaInitialTextbox.setText(runway.getAsda().toString());
        ldaInitialTextbox.setText(runway.getLda().toString());
        thresholdInitialTextbox.setText(runway.getDisplacedThreshold().toString());
    }

   public void addToRunwayDroplist() {
        for (String runwayName: airport.getRunwayHashMap().keySet()) {
            runwayDroplist.getItems().add(runwayName);
        }
    }
}

