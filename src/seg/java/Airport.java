package seg.java;

import org.w3c.dom.Element;
import seg.java.xml.XMLEntityInterface;

import java.util.HashMap;

public class Airport implements XMLEntityInterface {
    private String name;
    private HashMap<String, Runway> runwayHashMap;

    public Airport(String name) {
        runwayHashMap = new HashMap<>();
        this.name = name;
    }

    /**
     * Load an airport from its xml element
     *
     * @param airportElement The element containing the airport.
     * @return The loaded airport.
     */
    public static Airport loadFromXMLElement(Element airportElement){
        Airport airport = new Airport(airportElement.getAttribute("name"));
        return airport;
    }

    /**
     * Add a runway to the airport.
     * @param runway The runway to add.
     */
    public void addRunway(Runway runway){
        runwayHashMap.put(runway.getRunwayName(), runway);
    }

    /**
     * GETTERS & SETTERS
     *
     * @return
     */

    public String getName() {
        return this.name;
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
