package seg.java.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Runway {

    private SimpleDoubleProperty tora;
    private SimpleDoubleProperty asda;
    private SimpleStringProperty name;
    private SimpleDoubleProperty lda;
    private SimpleDoubleProperty threshold; // Displaced Threshold
    private SimpleDoubleProperty toda;
    private Runway reciprocalRunway;

    public Runway(String name, Runway reciprocalRunway, Double tora, Double asda, Double lda, Double toda, Double threshold) {
        this.name = new SimpleStringProperty(name);
        this.tora = new SimpleDoubleProperty(tora);
        this.asda = new SimpleDoubleProperty(asda);
        this.lda = new SimpleDoubleProperty(lda);
        this.threshold = new SimpleDoubleProperty(threshold);
        this.toda = new SimpleDoubleProperty(toda);
        this.reciprocalRunway = reciprocalRunway;
    }


    // Getters and Setters

    public double getTora() {
        return tora.get();
    }

    public void setTora(double tora) {
        this.tora.set(tora);
    }

    public SimpleDoubleProperty toraProperty() {
        return tora;
    }

    public double getAsda() {
        return asda.get();
    }

    public void setAsda(double asda) {
        this.asda.set(asda);
    }

    public SimpleDoubleProperty asdaProperty() {
        return asda;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public double getLda() {
        return lda.get();
    }

    public void setLda(double lda) {
        this.lda.set(lda);
    }

    public SimpleDoubleProperty ldaProperty() {
        return lda;
    }

    public double getThreshold() {
        return threshold.get();
    }

    public void setThreshold(double threshold) {
        this.threshold.set(threshold);
    }

    public SimpleDoubleProperty thresholdProperty() {
        return threshold;
    }

    public double getToda() {
        return toda.get();
    }

    public void setToda(double toda) {
        this.toda.set(toda);
    }

    public SimpleDoubleProperty todaProperty() {
        return toda;
    }

    public Runway getReciprocalRunway() {
        return reciprocalRunway;
    }

    public void setReciprocalRunway(Runway reciprocalRunway) {
        this.reciprocalRunway = reciprocalRunway;
    }
}
