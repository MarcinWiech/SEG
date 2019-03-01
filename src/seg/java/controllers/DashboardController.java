package seg.java.controllers;

import seg.java.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;                                                  // for Button and stuff
import javafx.scene.canvas.*;                                                   // for Canvas and stuff
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DashboardController
{
    public ToggleButton takeOffAwayButton;
    public ToggleButton takeOffTowardButton;
    
    @FXML private TextField widthTextbox;
    @FXML private TextField heightTextbox;
    @FXML private Pane topDownPane;
    @FXML private Pane sideOnPane;
    @FXML private Pane topDownPaneCopy;
    @FXML private Pane sideOnPaneCopy;

    @FXML private ChoiceBox runwayDroplist;

    @FXML private Canvas topDownCanvas;
    @FXML private Canvas sideOnCanvas;
    @FXML private Canvas topDownCanvasCopy;
    @FXML private Canvas sideOnCanvasCopy;

    @FXML private TextField xTextbox;
    @FXML private TextField yTextbox;

    @FXML private TextField toraInitialTextbox;
    @FXML private TextField todaInitialTextbox;
    @FXML private TextField asdaInitialTextbox;
    @FXML private TextField ldaInitialTextbox;
    @FXML private TextField thresholdInitialTextbox;
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

//==================================================================================================================================
//  Initialize
//==================================================================================================================================

    public void initialize()
    {
        //  Main objects get initialized & related preparations
            canvasDrawer = new CanvasDrawer();
            redeclarationComputer = new RedeclarationComputer();

        //  Empty views get drawn


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

    }

//==================================================================================================================================
//  Other methods
//==================================================================================================================================

    public void redeclareButtonPressed(ActionEvent event)
    {

        //  Printing used to show that canvas size changes
            System.out.println("Canvas width = " + topDownCanvas.getWidth());
            System.out.println("Canvas height = " + topDownCanvas.getHeight());

        //  Obstacle details
            double obstacleX = Double.parseDouble(xTextbox.getText());
            double obstacleY = Double.parseDouble(yTextbox.getText());
            double obstacleWidth = Double.parseDouble(heightTextbox.getText());
            redeclarationComputer.setObstacleDetails(obstacleX, obstacleY, obstacleWidth);

        //  Recalculation of parameters
            double toraInput = Double.parseDouble(toraInitialTextbox.getText());
            double todaInput = Double.parseDouble(todaInitialTextbox.getText());
            double asdaInput = Double.parseDouble(asdaInitialTextbox.getText());
            double ldaInput = Double.parseDouble(ldaInitialTextbox.getText());
            double dispThresholdInput = Double.parseDouble(thresholdInitialTextbox.getText());

            //  This block of code will also need the obstacle's details
                if (redeclarationComputer.needsRecalculation() == true)
                {
                    redeclarationComputer.setParameters(toraInput, todaInput, asdaInput, ldaInput, dispThresholdInput);
                    redeclarationComputer.calculate();
                }

        //  These will probably be removed as they are text boxes
            toraNewTextbox.setText(Double.toString(redeclarationComputer.getTora()));
            todaNewTextbox.setText(Double.toString(redeclarationComputer.getToda()));
            asdaNewTextbox.setText(Double.toString(redeclarationComputer.getAsda()));
            ldaNewTextbox.setText(Double.toString(redeclarationComputer.getLda()));
            thresholdNewTextbox.setText(Double.toString(redeclarationComputer.getDispTresh()));

        //  Canvas drawing gets triggered here
            canvasDrawer.drawTopDownCanvas(topDownCanvas);
            canvasDrawer.drawSideOnCanvas(sideOnCanvas);
            canvasDrawer.drawTopDownCanvas(topDownCanvasCopy);
            canvasDrawer.drawSideOnCanvas(sideOnCanvasCopy);
    }

    public void switchAirport(ActionEvent actionEvent)
    {
        try {
            Stage stage = (Stage) yTextbox.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/airportSelection.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Switch Airport");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (Exception e)
        {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    public void setValues(XMLReaderDOM xmlReaderDOM, Airport airport)
    {
        this.xmlReaderDOM = xmlReaderDOM;
        this.airport = airport;
        addToRunwayDroplist();
    }

    public void selectRunway(ActionEvent actionEvent)
    {
        runway = airport.getRunwayHashMap().get(runwayDroplist.getValue().toString());
        toraInitialTextbox.setText(runway.getTora().toString());
        todaInitialTextbox.setText(runway.getToda().toString());
        asdaInitialTextbox.setText(runway.getAsda().toString());
        ldaInitialTextbox.setText(runway.getLda().toString());
        thresholdInitialTextbox.setText(runway.getDisplacedThreshold().toString());
    }

   public void addToRunwayDroplist()
   {
        for (String runwayName: airport.getRunwayHashMap().keySet()) {
            runwayDroplist.getItems().add(runwayName);
        }
    }
}

