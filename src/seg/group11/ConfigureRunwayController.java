package seg.group11;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigureRunwayController implements Initializable {
    public Button addRunwayButton;
    public Button backButton;
    public TextArea remarkTextbox;
    public TextField ldaTextbox;
    public TextField asdaTextbox;
    public TextField todaTextbox;
    public TextField toraTextbox;
    public TextField runwayDesignatorTextbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void backToConfigureAirport(ActionEvent actionEvent) {
    }

    public void addRunway(ActionEvent actionEvent) {
        if (runwayDesignatorTextbox.getText().isEmpty() || toraTextbox.getText().isEmpty() || todaTextbox.getText().isEmpty()
                || asdaTextbox.getText().isEmpty() || ldaTextbox.getText().isEmpty() || remarkTextbox.getText().isEmpty()) {

            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are filled!").showAndWait();
        } else {
            try {
                Stage stage = (Stage) asdaTextbox.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("configureRunway.fxml"));
                Parent root1 = fxmlLoader.load();
                stage = new Stage();
                stage.setTitle("Configure Runway");
                stage.setScene(new Scene(root1));
                stage.show();

                createRunway();

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Uh oh, something went wrong :(").showAndWait();
            }
        }
    }

    public void createRunway() {
        Runway newRunway = new Runway(runwayDesignatorTextbox.getText(), Integer.valueOf(toraTextbox.getText()),
                Integer.valueOf(todaTextbox.getText()), Integer.valueOf(asdaTextbox.getText()),Integer.valueOf(ldaTextbox.getText()), remarkTextbox.getText());


    }
}
