package controllers;

import classes.XMLReaderDOM;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfigureRunwayController {
    public Button addRunwayButton;
    public Button backButton;
    public TextArea remarkTextbox;
    public TextField ldaTextbox;
    public TextField asdaTextbox;
    public TextField todaTextbox;
    public TextField toraTextbox;
    public TextField runwayDesignatorTextbox;

    private XMLReaderDOM xmlReaderDOM;

    /** WHEN BACK BUTTON IS PRESSED - GOES BACK TO ConfigureAirport **/
    public void backToConfigureAirport(ActionEvent actionEvent) {
            try {
                Stage stage = (Stage) asdaTextbox.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/configureAirport.fxml"));
                Parent root1 = fxmlLoader.load();
                stage = new Stage();
                stage.setTitle("Configure Airport");
                stage.setScene(new Scene(root1));
                stage.show();


                ConfigureRunwayController runwayController = fxmlLoader.getController();
                runwayController.setAirportArrayList(xmlReaderDOM);

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
            }
    }

    /** WHEN ADD BUTTON IS PRESSED **/
    public void addRunway(ActionEvent actionEvent) {
        if (runwayDesignatorTextbox.getText().isEmpty() || toraTextbox.getText().isEmpty() || todaTextbox.getText().isEmpty()
                || asdaTextbox.getText().isEmpty() || ldaTextbox.getText().isEmpty() || remarkTextbox.getText().isEmpty()) {

            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are filled!").showAndWait();
        } else {
            try {
                Stage stage = (Stage) asdaTextbox.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("configureRunway.fxml"));
                Parent root1 = fxmlLoader.load();
                stage = new Stage();
                stage.setTitle("Configure Runway");
                stage.setScene(new Scene(root1));
                stage.show();

                createRunway();

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
            }
        }
    }

    public void setAirportArrayList(XMLReaderDOM xmlReaderDOM) {
        this.xmlReaderDOM = xmlReaderDOM;
    }

    /** TO DO - NEED TO REPEAT UNTIL NUMBER OF RUNWAYS ARE MET **/
    public void createRunway() {

    }
}