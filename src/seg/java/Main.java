package seg.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private controllers.AirportSelectionController airportSelectionController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/airportSelection.fxml"));
        primaryStage.setTitle("Select Airport");
        primaryStage.setScene(new Scene(root, 385, 151));  // v: width v1: height
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
