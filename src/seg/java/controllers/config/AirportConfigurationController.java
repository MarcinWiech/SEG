package seg.java.controllers.config;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    @FXML public TableView<RunwayModel> tableView;
    @FXML public TableColumn<RunwayModel, String> designator;
    @FXML public TableColumn<RunwayModel, Double> tora;
    @FXML public TableColumn<RunwayModel, Double> asda;
    @FXML public TableColumn<RunwayModel, Double> lda;
    @FXML public TableColumn<RunwayModel, Double> threshold;
    @FXML public TableColumn<RunwayModel, Double> toda;

    @FXML private Text airportName;
    @FXML private Button backButton;

    protected Airport airport;

    public class RunwayModel {
        private SimpleDoubleProperty tora;
        private SimpleDoubleProperty asda;
        private SimpleStringProperty designator;
        private SimpleDoubleProperty lda;
        private SimpleDoubleProperty threshold;
        private SimpleDoubleProperty toda;

        public RunwayModel(String designator, Double tora, Double asda, Double lda, Double threshold, Double toda){
            this.designator = new SimpleStringProperty(designator);
            this.tora = new SimpleDoubleProperty(tora);
            this.asda = new SimpleDoubleProperty(asda);
            this.lda = new SimpleDoubleProperty(lda);
            this.threshold = new SimpleDoubleProperty(threshold);
            this.toda = new SimpleDoubleProperty(toda);
        }

        public String getDesignator() {
            return designator.get();
        }

        public SimpleStringProperty designatorProperty() {
            return designator;
        }

        public double getTora() {
            return tora.get();
        }

        public SimpleDoubleProperty toraProperty() {
            return tora;
        }

        public double getAsda() {
            return asda.get();
        }

        public SimpleDoubleProperty asdaProperty() {
            return asda;
        }

        public double getLda() {
            return lda.get();
        }

        public SimpleDoubleProperty ldaProperty() {
            return lda;
        }

        public double getThreshold() {
            return threshold.get();
        }

        public SimpleDoubleProperty thresholdProperty() {
            return threshold;
        }

        public double getToda() {
            return toda.get();
        }

        public SimpleDoubleProperty todaProperty() {
            return toda;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        designator.setCellValueFactory(new PropertyValueFactory<>("designator"));
        tora.setCellValueFactory(new PropertyValueFactory<>("tora"));
        asda.setCellValueFactory(new PropertyValueFactory<>("asda"));
        lda.setCellValueFactory(new PropertyValueFactory<>("lda"));
        threshold.setCellValueFactory(new PropertyValueFactory<>("threshold"));
        toda.setCellValueFactory(new PropertyValueFactory<>("toda"));

        tableView.setItems(runwayModels);
    }

    private ObservableList<RunwayModel> runwayModels = FXCollections.observableArrayList(
        new RunwayModel("Test1", 1.0 ,2.0, 3.0, 4.0, 5.0),
        new RunwayModel("Test2", 1.0 ,2.0, 3.0, 4.0, 5.0),
        new RunwayModel("Test3", 1.0 ,2.0, 3.0, 4.0, 5.0)
    );

    public void setAirport(Airport airport){
        this.airport = airport;
        airportName.setText(airport.getName());
    }

    public Runway getSelectedRunway(){
        //TODO: Fix me.
        return new Runway("A", "B",1.0,2.0,3.0,4.0,4.0);
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
            dashboardController.setValues(airport);
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
