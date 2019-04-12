package seg.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seg.java.XMLLoader;
import seg.java.controllers.config.AirportCreationController;
import seg.java.models.Airport;

import java.net.URL;
import java.util.ResourceBundle;

public class AirportSelectionController implements Initializable {
    public Label addAirportLabel;
    public ChoiceBox airportDroplist;
    public Button selectButton;

    /**
     * INITIALIZE - ADDS AIRPORTS TO DROPLIST (READ IN FROM XML FILE)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XMLLoader xmlLoader = XMLLoader.getInstance();
        for (Airport airport : xmlLoader.getAirportList()) {
            airportDroplist.getItems().add(airport.getName());
        }
    }

    /**
     * WHEN CLICK ON addAirportLabel (SELECTED AIRPORT) - OPENS ConfigureRunwayController
     */
    public void openAirportConfigurationScreen(MouseEvent mouseEvent) {
        try {
            Stage stage = (Stage) addAirportLabel.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/config/airportCreate.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Airport Configuration");
            stage.setScene(new Scene(root1));
            stage.show();


            AirportCreationController airportController = fxmlLoader.getController();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    /**
     * OPENS Dashboard
     */
    public void openDashboard(ActionEvent actionEvent) {
        if (airportDroplist.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select an airport to continue!").showAndWait();
        } else {
            try {
                Stage stage = (Stage) selectButton.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
                Parent root1 = fxmlLoader.load();
                stage = new Stage();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(root1));
                stage.show();
              
                XMLLoader xmlLoader = XMLLoader.getInstance();
                Airport airport = xmlLoader.getAirportByName(airportDroplist.getValue().toString());

                DashboardController dashboardController = fxmlLoader.getController();
                dashboardController.setAirport(airport);
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
            }
        }
    }

    public void configureAirports(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) addAirportLabel.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/config/airportConfig.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Configure Airports");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }


    /**
     * HOVER OVER UNDERLINES LIKE HYPERLINK
     */
    public void underlineText(MouseEvent mouseEvent) {
        addAirportLabel.setUnderline(true);
    }

    /**
     * STOP HOVERING - STOPS UNDERLINING
     */
    public void stopUnderlineText(MouseEvent mouseEvent) {
        addAirportLabel.setUnderline(false);
    }

}
