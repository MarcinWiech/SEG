package seg.java.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.control.cell.PropertyValueFactory;
import seg.java.models.Airport;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The new airport configuration controller.
 */
public class AirportConfigurationController implements Initializable {

    @FXML private TableView<RunwayModel> tableView;
    @FXML public TableColumn<RunwayModel, String> designator;
    @FXML public TableColumn<RunwayModel, Integer> tora;
    @FXML public TableColumn<RunwayModel, Integer> asda;
    @FXML public TableColumn<RunwayModel, Integer> lda;
    @FXML public TableColumn<RunwayModel, Integer> threshold;
    @FXML public TableColumn<RunwayModel, Integer> toda;

    @FXML private Text airportName;

    protected Airport airport;

    private class RunwayModel {
        private SimpleStringProperty designator;
        private SimpleIntegerProperty tora;
        private SimpleIntegerProperty asda;
        private SimpleIntegerProperty lda;
        private SimpleIntegerProperty threshold;
        private SimpleIntegerProperty toda;

        public RunwayModel(String designator, Integer tora, Integer asda, Integer lda, Integer threshold, Integer toda){
            this.designator = new SimpleStringProperty(designator);
            this.tora = new SimpleIntegerProperty(tora);
            this.asda = new SimpleIntegerProperty(asda);
            this.lda = new SimpleIntegerProperty(lda);
            this.threshold = new SimpleIntegerProperty(threshold);
            this.toda = new SimpleIntegerProperty(toda);
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
    }

    private ObservableList<RunwayModel> runwayModels = FXCollections.observableArrayList(
        new RunwayModel("Test1", 1 ,2, 3, 4, 5),
        new RunwayModel("Test2", 1 ,2, 3, 4, 5),
        new RunwayModel("Test3", 1 ,2, 3, 4, 5)
    );

    public void setAirport(Airport airport){
        this.airport = airport;
        airportName.setText(airport.getName());

        tableView.setItems(runwayModels);
    }
}
