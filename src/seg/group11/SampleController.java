package seg.group11;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable {
    /**
     *  Dashboard tab bar
     **/
    public Tab redeclarationTab;
    public Tab calculationTab;
    public Tab viewTab;

    /**
     *  'Edit' menu list
     **/
    public MenuItem editRunwayMenuItem;
    public MenuItem editAirportMenuItem;

    /**
     *  'File' menu list
     **/
    public MenuItem closeMenuItem;
    public MenuItem switchAirportMenuItem;

    public ComboBox runwayDroplist;
    private Main main;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void openAirportSelectScreen(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) runwayDroplist.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("airportSelection.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Airport Selection");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Uh oh, something went wrong :(");
        }

    }

    public void setAirportArrayList(Main main) {
        this.main = main;
    }
}
