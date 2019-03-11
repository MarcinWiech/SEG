package seg.java.models;

import java.util.HashMap;

public class Airport {
    private String name;
    private Integer numberOfRunways;
    private HashMap<String, Runway> runwayHashMap;

    public Airport() {
        runwayHashMap = new HashMap<>();
        numberOfRunways = 0;
    }

    public Airport(String name){
        runwayHashMap = new HashMap<>();
        numberOfRunways = 0;
        this.name = name;
    }

    /**
     * GETTERS & SETTERS
     *
     * @return
     */
    public void setNumberOfRunways(Integer airportRunway) {
        this.numberOfRunways = airportRunway;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String airportName) {
        this.name = airportName;
    }

    public HashMap<String, Runway> getRunwayHashMap() {
        return this.runwayHashMap;
    }

    public void addRunway(String reciprocalName, String runwayDesignator, Integer tora, Integer toda, Integer asda,Integer lda,Integer displacedThreshold) {
        Runway newRunway = new Runway(reciprocalName, runwayDesignator, tora, toda, asda, lda, displacedThreshold );
        runwayHashMap.put(newRunway.getRunwayName(), newRunway);
    }
}
