package seg.java.controllers.config;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seg.java.models.Airport;

public class AirportCreationController {
    public Button addAirportButton;
    public TextField airportNameTextbox;

    public void addAirport(ActionEvent actionEvent) {
        if (airportNameTextbox.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are filled!").showAndWait();
        } else {

            Airport airport = new Airport(airportNameTextbox.getText());

            try {
                Stage stage = (Stage) addAirportButton.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/config/airportConfig.fxml"));
                Parent root1 = fxmlLoader.load();

                AirportConfigurationController controller = fxmlLoader.getController();

                controller.setAirport(airport);

                stage = new Stage();
                stage.setTitle("Airport Configuration");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
            }
        }
    }
}
