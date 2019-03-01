package seg.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seg.java.xml.XMLReaderDOM;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigureAirportController implements Initializable {
    /**
     * FXML variables
     */
    public Button backToAirportSelection;
    public Button addAirportButton;
    public TextField airportNameTextbox;
    public ChoiceBox runwayDroplist;

    private XMLReaderDOM xmlReaderDOM;

    /**
     * ADDS NUMBER OF RUNWAYS TO DROPDOWN LIST
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        runwayDroplist.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    }

    /**
     * GOES BACK TO AirportSelectionController
     */
    public void backToAirportSelection(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) addAirportButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/airportSelection.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Airport Selection");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    /**
     * ADDS Airport TO ARRAYLIST & OPENS EITHER dashboard.fxml ***** OR should loop for amount of runways ****** FOR DAN TO DO
     */
    public void addAirport(ActionEvent actionEvent) {
        if (airportNameTextbox.getText().isEmpty() || runwayDroplist.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are filled!").showAndWait();
        } else {
            try {
                Stage stage = (Stage) addAirportButton.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/dashboard.fxml"));
                Parent root1 = fxmlLoader.load();
                stage = new Stage();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(root1));
                stage.show();

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
            }
        }
    }

    public void setAirportArrayList(XMLReaderDOM xmlReaderDOM) {
        this.xmlReaderDOM = xmlReaderDOM;
    }

    /**
     * *** TO BE DONE **** IMPORTS AIRPORTS FROM airports.xml AND ADDS THEM TO ARRAY LIST
     */
    public void importAirportsFromXML(ActionEvent actionEvent) {
    }
}
