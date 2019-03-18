package seg.java.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Airport {

    private String name;
    private ObservableList<Runway> runways;

    public Airport(String name){
        this.name = name;
        runways = FXCollections.observableArrayList();
    }

    public void addRunway(Runway runway){
        runways.add(runway);
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableList<Runway> getRunways() {
        return runways;
    }

    public void setRunways(ObservableList<Runway> runways) {
        this.runways = runways;
    }
}
