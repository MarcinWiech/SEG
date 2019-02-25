package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AirportSelectionController {
    public Label addAirportLabel;
    public ChoiceBox airportDroplist;
    public Button selectButton;
    private ArrayList<Airport> airportArrayList;
    private Main main;

    /**
     * Opens Airport Configuration screen
     * @param mouseEvent - when click on addAirportLabel
     */
    public void openAirportConfigurationScreen(MouseEvent mouseEvent) {
        try {
            Stage stage = (Stage) addAirportLabel.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("configureAirport.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Airport Configuration");
            stage.setScene(new Scene(root1));
            stage.show();


            ConfigureAirportController controller = fxmlLoader.getController();
            controller.setAirportArrayList(main);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    /**
     * Opens Dashboard screen
     * @param  - when click on select
     * NEED TO ADD A CHECK FOR IF NO AIRPORT IS SELECTED DON'T LET PROGRESS
     */
    public void openDashboard(ActionEvent actionEvent) {
        if (airportDroplist.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select an airport to continue!").showAndWait();
        } else {
            try {
                Stage stage = (Stage) addAirportLabel.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                Parent root1 = fxmlLoader.load();
                stage = new Stage();
                stage.setTitle("Airport Selection");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
            }
        }
    }

    /**
     * Hover over underlines like hyperlink
     * @param mouseEvent
     */
    public void underlineText(MouseEvent mouseEvent) {
        addAirportLabel.setUnderline(true);
    }

    /**
     * Stop hovering - stops underline
     * @param mouseEvent
     */
    public void stopUnderlineText(MouseEvent mouseEvent) {
        addAirportLabel.setUnderline(false);
    }

    public void setAirportArrayList(Main main) {
        this.main = main;
    }

}
