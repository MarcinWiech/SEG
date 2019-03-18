package seg.java.models;

import java.util.HashMap;

public class AirportOld {
    private String name;
    private Integer numberOfRunways;
    private HashMap<String, RunwayOld> runwayHashMap;

    public AirportOld() {
        runwayHashMap = new HashMap<>();
        numberOfRunways = 0;
    }

    public AirportOld(String name) {
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

    public HashMap<String, RunwayOld> getRunwayHashMap() {
        return this.runwayHashMap;
    }

    public void addRunway(String runwayDesignator, String reciprocalName, Double tora, Double toda, Double asda, Double lda, Double displacedThreshold) {
        RunwayOld newRunwayOld = new RunwayOld(runwayDesignator, reciprocalName, tora, toda, asda, lda, displacedThreshold);
        runwayHashMap.put(newRunwayOld.getRunwayName(), newRunwayOld);
    }
}
