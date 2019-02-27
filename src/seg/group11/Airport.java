package seg.group11;

import java.util.ArrayList;

public class Airport {
    private String airportName;
    private Integer airportNumberOfRunways;
    private ArrayList<Runway> runwayArrayList;

    public Airport(String airportName, Integer airportRunway) {
        this.airportName = airportName;
        this.airportNumberOfRunways = airportRunway;
    }

    /**
     * GETTERS & SETTERS
     * @return
     */
    public Integer getAirportNumberOfRunways() {
        return airportNumberOfRunways;
    }

    public void setAirportNumberOfRunways(Integer airportRunway) {
        this.airportNumberOfRunways = airportRunway;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public ArrayList<Runway> getRunwayArrayList() {
        return runwayArrayList;
    }

    public void addToRunwayArrayList(Runway newRunway) {
        this.runwayArrayList.add(newRunway);
    }
}
