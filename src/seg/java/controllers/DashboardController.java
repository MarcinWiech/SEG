package seg.java.controllers;

import seg.java.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;                                                  // for Button and stuff
import javafx.scene.canvas.*;                                                   // for Canvas and stuff
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seg.java.models.Airport;
import seg.java.models.RedeclarationComputer;
import seg.java.models.Runway;

public class DashboardController
{
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

    @FXML private TextField xLTextbox;
    @FXML private TextField xRTextbox;
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

    @FXML private TextArea toraBDTextArea;
    @FXML private TextArea todaBDTextArea;
    @FXML private TextArea asdaBDTextArea;
    @FXML private TextArea ldaBDTextArea;

    private XMLReaderDOM xmlReaderDOM;
    private CanvasDrawer canvasDrawer;
    private RedeclarationComputer redeclarationComputer;
    private RedeclarationComputer reciprocalComputer;
    private Airport currentAirport;
    private Runway currentRunway;

    private double obstacleXL = 0;
    private double obstacleXR = 0;
    private double obstacleY = 0;
    private double obstacleHeight = 0;
    private double toraInput;
    private double todaInput;
    private double asdaInput;
    private double ldaInput;
    private double dispThresholdInput;

/*==================================================================================================================================
//  Initialize
//================================================================================================================================*/

    public void initialize()
    {
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
    }

/*==================================================================================================================================
//  Other methods
//================================================================================================================================*/

    public void redeclareButtonPressed(ActionEvent event) throws RedeclarationComputer.InvalidActionException
    {
        //  Obstacle details
        double obstacleX = 0;
        double obstacleY = 0;
        double obstacleHeight = 0;

        //  Obstacle details get read
        try
        {
            obstacleHeight = Double.parseDouble(heightTextbox.getText());
        }
        catch (Exception e)
        {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: obstacle height!").showAndWait();
            return;
        }

        try
        {
            obstacleXL = Double.parseDouble(xLTextbox.getText());
        }
        catch (Exception e)
        {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: obstacle XL position!").showAndWait();
            return;
        }

        try
        {
            obstacleXR = Double.parseDouble(xRTextbox.getText());
        }
        catch (Exception e)
        {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: obstacle XR position!").showAndWait();
            return;
        }

        try
        {
            obstacleY = Double.parseDouble(yTextbox.getText());
        }
        catch (Exception e)
        {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid value for: obstacle Y position!").showAndWait();
            return;
        }

        //  Recalculation of parameters
        try
        {
            toraInput = Double.parseDouble(toraInitialTextbox.getText());
            todaInput = Double.parseDouble(todaInitialTextbox.getText());
            asdaInput = Double.parseDouble(asdaInitialTextbox.getText());
            ldaInput = Double.parseDouble(ldaInitialTextbox.getText());
            dispThresholdInput = Double.parseDouble(thresholdInitialTextbox.getText());
            redeclarationComputer.setRunway(currentRunway);

            //  Obstacle details get passed to redeclarationComputer
            redeclarationComputer.setObstacleDetails(obstacleXL, obstacleXR, obstacleY, obstacleHeight);

            // We check to see whether we need to recalculate
            if (redeclarationComputer.needsRecalculation(redeclarationComputer.getObstacleXL(), redeclarationComputer.getObstacleXR(), redeclarationComputer.getObstacleY()))
            {
                redeclare();
            }

        }
        catch (Exception e)
        {
            new Alert(Alert.AlertType.ERROR, "Please select a runway!").showAndWait();
            e.printStackTrace();
            return;
        }

    }

    private void redeclare()
    {
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

    public void switchButtonPressed()
    {
        //  Check special cases
        if(currentRunway == null)
        {
            new Alert(Alert.AlertType.ERROR, "Please select a runway!").showAndWait();
            return;
        }
        if(currentRunway.getReciprocalRunway() == null)
        {
            new Alert(Alert.AlertType.ERROR, "No reciprocal runway exists!").showAndWait();
            return;
        }
        if(toraNewTextbox.getText().equals(""))
        {
            runwayDroplist.setValue(currentRunway.getReciprocalRunway().getRunwayName());
            return;
        }

        currentRunway = currentRunway.getReciprocalRunway();

        //  Update the dropdown menu as well
        runwayDroplist.setValue(currentRunway.getRunwayName());

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

    public void switchAirport(ActionEvent actionEvent)
    {
        try
        {
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
        xmlReaderDOM = xmlReaderDOM;
        currentAirport = airport;
        addToRunwayDroplist();
    }

    public void selectRunway(ActionEvent actionEvent)
    {
        currentRunway = currentAirport.getRunwayHashMap().get(runwayDroplist.getValue().toString());
        toraInitialTextbox.setText(currentRunway.getTora().toString());
        todaInitialTextbox.setText(currentRunway.getToda().toString());
        asdaInitialTextbox.setText(currentRunway.getAsda().toString());
        ldaInitialTextbox.setText(currentRunway.getLda().toString());
        thresholdInitialTextbox.setText(currentRunway.getDisplacedThreshold().toString());
    }

    public void addToRunwayDroplist()
    {
        for (String runwayName: currentAirport.getRunwayHashMap().keySet())
        {
            runwayDroplist.getItems().add(runwayName);
        }
    }

}

