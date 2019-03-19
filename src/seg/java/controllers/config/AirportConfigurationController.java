package seg.java.controllers.config;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seg.java.controllers.DashboardController;
import seg.java.models.Airport;
import seg.java.models.Runway;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The new airport configuration controller.
 */
public class AirportConfigurationController implements Initializable {

    @FXML public TableView<Runway> tableView;
    @FXML public TableColumn<Runway, String> name;
    @FXML public TableColumn<Runway, Double> tora;
    @FXML public TableColumn<Runway, Double> asda;
    @FXML public TableColumn<Runway, Double> lda;
    @FXML public TableColumn<Runway, Double> threshold;
    @FXML public TableColumn<Runway, Double> toda;

    @FXML private Text airportName;
    @FXML private Button backButton;

    private Airport airport;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tora.setCellValueFactory(new PropertyValueFactory<>("tora"));
        asda.setCellValueFactory(new PropertyValueFactory<>("asda"));
        lda.setCellValueFactory(new PropertyValueFactory<>("lda"));
        threshold.setCellValueFactory(new PropertyValueFactory<>("threshold"));
        toda.setCellValueFactory(new PropertyValueFactory<>("toda"));

        tableView.setItems(airport.getRunways());
    }

    public void setAirport(Airport airport){
        this.airport = airport;
        airportName.setText(airport.getName());
    }

    public Runway getSelectedRunway(){
        return tableView.getSelectionModel().getSelectedItem();
    }

    // Actions

    public void backButtonPressed(ActionEvent actionEvent){
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/dashboard.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root1));
            stage.show();

            DashboardController dashboardController = fxmlLoader.getController();
            dashboardController.setAirport(airport);
        } catch(IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    public void newRunwayButtonPressed(ActionEvent ae){
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/config/runwayConfig.fxml"));
            Parent root1 = fxmlLoader.load();

            RunwayCreationController controller = fxmlLoader.getController();

            controller.setAirport(airport);

            stage = new Stage();
            stage.setTitle("Runway Configuration");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }

    public void editRunwayButtonPressed(ActionEvent ae){
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/seg/resources/views/config/runwayConfig.fxml"));
            Parent root1 = fxmlLoader.load();

            RunwayCreationController controller = fxmlLoader.getController();

            controller.setAirport(airport);
            controller.setRunway(getSelectedRunway());

            stage = new Stage();
            stage.setTitle("Runway Configuration");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
        }
    }
}
