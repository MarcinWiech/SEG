package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;                                                  // for Button and stuff
import javafx.scene.canvas.*;                                                   // for Canvas and stuff
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;                                                // for Color
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;                                            // for shapes

public class Controller
{

    // Fields #################################################################################################################################

    @FXML private Button redeclareButton;

    @FXML private Canvas topDownCanvas;
    @FXML private Canvas sideOnCanvas;

    @FXML private Pane topDownPane;

    @FXML private TextField obstacleXField;
    @FXML private TextField obstacleYField;
    @FXML private TextField obstacleHeightField;

    @FXML private TextField toraInputField;
    @FXML private TextField todaInputField;
    @FXML private TextField asdaInputField;
    @FXML private TextField ldaInputField;
    @FXML private TextField dispTreshInputField;
    @FXML private TextField toraOutputField;
    @FXML private TextField todaOutputField;
    @FXML private TextField asdaOutputField;
    @FXML private TextField ldaOutputField;
    @FXML private TextField dispTreshOutputField;






    private CanvasDrawer canvasDrawer;
    private RedeclarationComputer redeclarationComputer;

    public void initialize()
    {
        // Main objects get initialized && related preparations
            canvasDrawer = new CanvasDrawer();
                canvasDrawer.setCanvases(topDownCanvas, sideOnCanvas);
            redeclarationComputer = new RedeclarationComputer();

        // Default textFields text
            obstacleXField.setText("0");
            obstacleYField.setText("0");
            obstacleHeightField.setText("0");
            toraInputField.setText("0");
            todaInputField.setText("0");
            asdaInputField.setText("0");
            ldaInputField.setText("0");
            dispTreshInputField.setText("0");

        // Empty views get drawn







        // REALLY IMPORTANT: the following 4 make the topDownPane resizable by binding it to topDownPane
            topDownCanvas.widthProperty().bind(topDownPane.widthProperty());
            topDownCanvas.heightProperty().bind(topDownPane.heightProperty());
            topDownCanvas.widthProperty().addListener(event -> canvasDrawer.drawTopDownCanvas());
            topDownCanvas.heightProperty().addListener(event -> canvasDrawer.drawTopDownCanvas());

    }

    public void redeclareButtonPressed(ActionEvent event)
    {
        //initialize();

        System.out.println("Canvas width = " + topDownCanvas.getWidth());
        System.out.println("Canvas height = " + topDownCanvas.getHeight());

        // Obstacle details
            double obstacleX = Double.parseDouble(obstacleXField.getText());
            double obstacleY = Double.parseDouble(obstacleYField.getText());
            double obstacleWidth = Double.parseDouble(obstacleHeightField.getText());
            redeclarationComputer.setObstacleDetails(obstacleX, obstacleY, obstacleWidth);

        // Recalculation of parameters

            double toraInput = Double.parseDouble(toraInputField.getText());
            double todaInput = Double.parseDouble(todaInputField.getText());
            double asdaInput = Double.parseDouble(asdaInputField.getText());
            double ldaInput = Double.parseDouble(ldaInputField.getText());
            double dispTreshInput = Double.parseDouble(dispTreshInputField.getText());

            // This block of code will also need the obstacle's details
            if(redeclarationComputer.needsRecalculation() == true)
            {
                redeclarationComputer.setParameters(toraInput, todaInput, asdaInput, ldaInput, dispTreshInput);
                redeclarationComputer.calculate();
            }

            toraOutputField.setText(Double.toString(redeclarationComputer.getTora()));
            todaOutputField.setText(Double.toString(redeclarationComputer.getToda()));
            asdaOutputField.setText(Double.toString(redeclarationComputer.getAsda()));
            ldaOutputField.setText(Double.toString(redeclarationComputer.getLda()));
            dispTreshOutputField.setText(Double.toString(redeclarationComputer.getDispTresh()));

        // Canvas drawing gets triggered here
            canvasDrawer.drawTopDownCanvas();
            canvasDrawer.drawSideOnCanvas();
    }

}

