package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    private AirportSelectionController airportSelectionController;
    private ArrayList<Airport> airportArrayList;

    @Override
    public void start(Stage primaryStage) throws Exception{
        /**
         *  Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
         *  primaryStage.setTitle("Runway Redeclaration");
         *  primaryStage.setScene(new Scene(root, 1000, 600));  /* v: width v1: height
         *  primaryStage.show();
         **/


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("airportSelection.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Select Airport");
        stage.setScene(new Scene(root1));
        stage.show();

        airportSelectionController = fxmlLoader.getController();
        airportSelectionController.setAirportArrayList(this);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public ArrayList<Airport> getAirportArrayList() {
        return this.airportArrayList;
    }
}
