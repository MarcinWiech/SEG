package seg.java.controllers.config;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seg.java.XMLReaderDOM;
import seg.java.controllers.DashboardController;
import seg.java.models.Airport;
import seg.java.models.Runway;

public class RunwayCreationController {
    public Button addRunwayButton;
    public Button backButton;
    public TextField ldaTextbox;
    public TextField asdaTextbox;
    public TextField todaTextbox;
    public TextField toraTextbox;
    public TextField runwayDesignatorTextbox;
    public TextField thresholdTextbox;

    private XMLReaderDOM xmlReaderDOM;
    private Airport airport;

    /**
     * WHEN BACK BUTTON IS PRESSED - GOES BACK TO ConfigureAirport
     **/
    public void backToConfigureAirport(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) asdaTextbox.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/config/airportConfig.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Configure Airport");
            stage.setScene(new Scene(root1));
            stage.show();


            AirportConfigurationController controller = fxmlLoader.getController();
            controller.setAirport(this.airport);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    /**
     * WHEN ADD BUTTON IS PRESSED
     **/
    public void addRunway(ActionEvent actionEvent) {
        if (runwayDesignatorTextbox.getText().isEmpty() || toraTextbox.getText().isEmpty() || todaTextbox.getText().isEmpty()
                || asdaTextbox.getText().isEmpty() || ldaTextbox.getText().isEmpty() || thresholdTextbox.getText().isEmpty()) {

            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are filled!").showAndWait();
        } else {
            airport.addRunway("null", runwayDesignatorTextbox.getText(), Double.parseDouble(toraTextbox.getText()), Double.parseDouble(todaTextbox.getText()), Double.parseDouble(asdaTextbox.getText()), Double.parseDouble(ldaTextbox.getText()), Double.parseDouble(thresholdTextbox.getText()));
            runwayDesignatorTextbox.clear();
            toraTextbox.clear();
            todaTextbox.clear();
            asdaTextbox.clear();
            ldaTextbox.clear();
            thresholdTextbox.clear();
        }
    }

    public void onFinish(ActionEvent actionEvent) {
        addRunway(actionEvent);
        try {
            Stage stage = (Stage) asdaTextbox.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/config/airportConfig.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Aiport Configuration");
            stage.setScene(new Scene(root1));
            stage.show();

            AirportConfigurationController controller = fxmlLoader.getController();
            controller.setAirport(this.airport);
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    public void setAirportArrayList(XMLReaderDOM xmlReaderDOM) {
        this.xmlReaderDOM = xmlReaderDOM;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public void setRunway(Runway runway){
        // Todo: Runway editing.
    }
}