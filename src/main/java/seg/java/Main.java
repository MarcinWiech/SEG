package seg.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/views/airportSelection.fxml"));
//        primaryStage.setTitle("Select Airport");
//        primaryStage.setScene(new Scene(root, 264, 138));  // v: width v1: height
//        To use login screen comment three lines above and uncomment three lines below
        Parent root = FXMLLoader.load(getClass().getResource("/views/loginView.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 385, 230));  // v: width v1: height
        primaryStage.show();
    }
}
