package seg.java;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import seg.java.models.Airport;
import seg.java.models.Runway;
import seg.java.models.Obstacle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class XMLLoader {
    private static XMLLoader instance = null;
    private ArrayList<Airport> airportList;
    private ArrayList<Obstacle> obstacleArrayList;

    private XMLLoader() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream airportFile = classloader.getResourceAsStream("airports.xml");
        InputStream obstacleFile = classloader.getResourceAsStream("obstacles.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(airportFile);
            doc.getDocumentElement().normalize();

            NodeList airportNodeList = doc.getElementsByTagName("Airport");

            /** LOOPS THROUGH AIRPORTS TO ADD TO ARRAYLIST **/
            airportList = new ArrayList<>();
            for (int i = 0; i < airportNodeList.getLength(); i++) {
                Airport airport = getAirportFromNode(airportNodeList.item(i));
                airportList.add(airport);

                /** LOOPS THROUGH RUNWAYS TO ADD TO AIRPORT **/
                NodeList runwayNodeList = airportNodeList.item(i).getChildNodes();
                for (int j = 0; j < runwayNodeList.getLength(); j++) {
                    Node childNode = runwayNodeList.item(j);
                    if ("Runway".equals(childNode.getNodeName())) {
                        Runway runway = getRunwayFromNode(childNode, airport);
                        airport.addRunway(runway);
                    }
                }

            }

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(obstacleFile);
            doc.getDocumentElement().normalize();

            NodeList obstacleNodeList = doc.getElementsByTagName("obstacle");
            obstacleArrayList = new ArrayList<>();

            for (int i = 0; i < obstacleNodeList.getLength(); i++){
                Obstacle obstacle = getObstacleFromNode(obstacleNodeList.item(i));
                obstacleArrayList.add(obstacle);
            }

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
    }

    private static Obstacle getObstacleFromNode(Node node){
        Element element = (Element) node;
        String name = element.getAttribute("name");
        float xl = Float.valueOf(element.getAttribute("xl"));
        float xr = Float.valueOf(element.getAttribute("xr"));
        float height = Float.valueOf(element.getAttribute("height"));
        float y = Float.valueOf(element.getAttribute("y"));
        return new Obstacle(name, xl, xr, height, y);
    }

    private static Airport getAirportFromNode(Node node) {
        String name = "Airport";
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            name = element.getAttribute("name");
        }
        return new Airport(name);
    }

    private static Runway getRunwayFromNode(Node node, Airport airport) {
        String name = "Runway";
        String reciprocalName = "Reciprocal Runway";
        Double tora = 100.0;
        Double toda = 100.0;
        Double asda = 100.0;
        Double lda = 100.0;
        Double threshold = 100.0;

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            name = getTagValue(element, "runwayName");
            reciprocalName = getTagValue(element, "reciprocalName");
            tora = Double.valueOf(getTagValue(element, "tora"));
            toda = Double.valueOf(getTagValue(element, "toda"));
            asda = Double.valueOf(getTagValue(element, "asda"));
            lda = Double.valueOf(getTagValue(element, "lda"));
            threshold = Double.valueOf(getTagValue(element, "threshold"));
        }

        Runway runway = new Runway(name, null, tora, toda, asda, lda, threshold);

        if (!runway.getName().equals("null")) {
            try {
                Runway reciprocal = airport.getRunwayByName(reciprocalName);
                runway.setReciprocalRunway(reciprocal);
                reciprocal.setReciprocalRunway(runway);
            } catch (Exception e) {
                // No reciprocal found yet.
            }
        }

        return runway;
    }

    private static String getTagValue(Element element, String tag) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    // Public Methods

    // Singleton
    public static XMLLoader getInstance() {
        if (instance == null) {
            instance = new XMLLoader();
        }

        return instance;
    }

    public ArrayList<Airport> getAirportList() {
        return airportList;
    }

    public ArrayList<Obstacle> getObstacleArrayList() {
        return obstacleArrayList;
    }

    public Airport getAirportByName(String name) throws Exception {
        for (Airport airport : getAirportList()) {
            if (airport.getName().equals(name)) {
                return airport;
            }
        }
        throw new Exception("Unable to find airport with name " + name);
    }

}