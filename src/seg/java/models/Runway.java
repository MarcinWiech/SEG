package seg.java.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Runway {

    private SimpleDoubleProperty tora;
    private SimpleDoubleProperty asda;
    private SimpleStringProperty designator;
    private SimpleDoubleProperty lda;
    private SimpleDoubleProperty threshold; // Displaced Threshold
    private SimpleDoubleProperty toda;
    private Runway reciprocalRunway;

    public Runway(String designator, Runway reciprocalRunway, Double tora, Double asda, Double lda, Double threshold, Double toda){
        this.designator = new SimpleStringProperty(designator);
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

    public SimpleDoubleProperty toraProperty() {
        return tora;
    }

    public void setTora(double tora) {
        this.tora.set(tora);
    }

    public double getAsda() {
        return asda.get();
    }

    public SimpleDoubleProperty asdaProperty() {
        return asda;
    }

    public void setAsda(double asda) {
        this.asda.set(asda);
    }

    public String getDesignator() {
        return designator.get();
    }

    public SimpleStringProperty designatorProperty() {
        return designator;
    }

    public void setDesignator(String designator) {
        this.designator.set(designator);
    }

    public double getLda() {
        return lda.get();
    }

    public SimpleDoubleProperty ldaProperty() {
        return lda;
    }

    public void setLda(double lda) {
        this.lda.set(lda);
    }

    public double getThreshold() {
        return threshold.get();
    }

    public SimpleDoubleProperty thresholdProperty() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold.set(threshold);
    }

    public double getToda() {
        return toda.get();
    }

    public SimpleDoubleProperty todaProperty() {
        return toda;
    }

    public void setToda(double toda) {
        this.toda.set(toda);
    }

    public Runway getReciprocalRunway() {
        return reciprocalRunway;
    }

    public void setReciprocalRunway(Runway reciprocalRunway) {
        this.reciprocalRunway = reciprocalRunway;
    }
}
