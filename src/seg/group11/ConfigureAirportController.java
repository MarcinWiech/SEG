package seg.group11;

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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConfigureAirportController implements Initializable {
    /**
     * FXML variables
     */
    public Button backToAirportSelection;
    public Button addAirportButton;
    public TextField airportNameTextbox;
    public ChoiceBox runwayDroplist;


    private Airport newAirport;
    private Main main;

    /**
     * Set up of screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        runwayDroplist.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
    }

    /**
     * Goes back to Airport Selection Screen
     * @param actionEvent
     */
    public void backToAirportSelection(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) addAirportButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("airportSelection.fxml"));
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
     * Adds an Airport to arraylist - opens Dashboard screen
     * @param actionEvent
     */
    public void addAirport(ActionEvent actionEvent) {
        if (airportNameTextbox.getText().isEmpty() || runwayDroplist.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are filled!").showAndWait();
        } else {
            newAirport = new Airport(airportNameTextbox.getText(), Integer.valueOf(runwayDroplist.getValue().toString()));
            this.main.getAirportArrayList().add(newAirport);

            try {
                Stage stage = (Stage) addAirportButton.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
                Parent root1 = fxmlLoader.load();
                stage = new Stage();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(root1));
                stage.show();

                SampleController sampleController = fxmlLoader.getController();
                sampleController.setAirportArrayList(main);

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
            }
        }
    }

    public void setAirportArrayList(Main main) {
        this.main = main;
    }
}
