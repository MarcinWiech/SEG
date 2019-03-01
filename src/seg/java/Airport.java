package seg.java;

import java.util.HashMap;

public class Airport {
    private String name;
    private HashMap<String, Runway> runwayHashMap;

    public Airport() {
        runwayHashMap = new HashMap<>();
    }

    /**
     * GETTERS & SETTERS
     *
     * @return
     */

    public String getName() {
        return this.name;
    }

    public void setName(String airportName) {
        this.name = airportName;
    }

    public HashMap<String, Runway> getRunwayHashMap() {
        return this.runwayHashMap;
    }

    public void addRunway(String runwayDesignator, Integer tora, Integer toda, Integer asda, Integer lda, Integer displacedThreshold) {
        Runway newRunway = new Runway(runwayDesignator, tora, toda, asda, lda, displacedThreshold);
        runwayHashMap.put(newRunway.getRunwayName(), newRunway);
    }

    /**
     * Get the number of runways for this airport instance.
     *
     * @return number of runways.
     */
    public int getNumberOfRunways() {
        return this.runwayHashMap.size();
    }
}
