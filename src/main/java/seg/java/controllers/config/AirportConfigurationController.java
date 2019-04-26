package seg.java.controllers.config;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seg.java.models.Airport;
import seg.java.models.Runway;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The new airport configuration controller.
 */
public class AirportConfigurationController implements Initializable {

    @FXML
    public TableView<Runway> tableView;
    @FXML
    public TableColumn<Runway, String> name;
    @FXML
    public TableColumn<Runway, Double> tora;
    @FXML
    public TableColumn<Runway, Double> asda;
    @FXML
    public TableColumn<Runway, Double> lda;
    @FXML
    public TableColumn<Runway, Double> threshold;
    @FXML
    public TableColumn<Runway, Double> toda;

    @FXML
    private Text airportName;
    @FXML
    private Button backButton;

    private Airport airport;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tora.setCellValueFactory(new PropertyValueFactory<>("tora"));
        asda.setCellValueFactory(new PropertyValueFactory<>("asda"));
        lda.setCellValueFactory(new PropertyValueFactory<>("lda"));
        threshold.setCellValueFactory(new PropertyValueFactory<>("threshold"));
        toda.setCellValueFactory(new PropertyValueFactory<>("toda"));
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
        airportName.setText(airport.getName());
        tableView.setItems(airport.getRunways());
    }

    public Runway getSelectedRunway() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    // Actions

    public void backButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    public void newRunwayButtonPressed(ActionEvent ae) {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/config/runwayConfig.fxml"));
            Parent root1 = fxmlLoader.load();

            RunwayConfigurationController controller = fxmlLoader.getController();

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

    public void editRunwayButtonPressed(ActionEvent ae) {
        if (getSelectedRunway() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a runway to edit.").showAndWait();
        } else {
            try {
                Stage stage = (Stage) backButton.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/config/runwayConfig.fxml"));
                Parent root1 = fxmlLoader.load();

                RunwayConfigurationController controller = fxmlLoader.getController();

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

    public void deleteRunwayButtonPressed(ActionEvent actionEvent) {
        if (getSelectedRunway() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a runway to delete.").showAndWait();
        } else {
            airport.deleteRunway(getSelectedRunway());
            tableView.setItems(airport.getRunways());
        }
    }

    private void saveToFile(File file){
        try{
            FileWriter fs = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fs);

            pw.println("<?xml version=\"1.0\"?>");
            pw.println(String.format("<Airports>"));
            pw.println(String.format("<Airport name=\"%s\">", airport.getName()));

            ObservableList<Runway> list = tableView.getItems();
            for(Runway r: list){
                pw.println(String.format("<Runway>"));
                pw.println(String.format("<runwayName>%s</runwayName>", r.getName()));

                String name = "";
                if (r.getReciprocalRunway() != null) {
                    name = r.getReciprocalRunway().getName();
                }

                pw.println(String.format("<reciprocalName>%s</reciprocalName>", name));
                pw.println(String.format("<tora>%f</tora>", r.getTora()));
                pw.println(String.format("<toda>%f</toda>", r.getToda()));
                pw.println(String.format("<asda>%f</asda>", r.getAsda()));
                pw.println(String.format("<lda>%f</lda>", r.getLda()));
                pw.println(String.format("<threshold>%f</threshold>", r.getThreshold()));
                pw.println(String.format("</Runway>"));
            }
            pw.println(String.format("</Airport>"));
            pw.println(String.format("</Airports>"));
            pw.close();
            new Alert(Alert.AlertType.INFORMATION, "Successfully exported airport information.").showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Unable to save.").showAndWait();
        }
    }

    public void saveButtonPressed(ActionEvent actionEvent){
        saveToFile(new File("airports.xml"));
    }

    public void saveAsButtonPressed(ActionEvent actionEvent){
        FileChooser fc = new FileChooser();
        fc.setTitle("Select save location.");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Airport Database", "xml"));
        File f = fc.showSaveDialog(airportName.getScene().getWindow());
        saveToFile(f);
    }
}
