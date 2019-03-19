package seg.java;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import seg.java.models.Airport;
import seg.java.models.Runway;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLLoader {
    private static XMLLoader instance = null;
    private ArrayList<Airport> airportList;

    private XMLLoader() {
        String filePath = "./src/airports.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
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

        if (runway.getName().equals("null") == false) {
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

    public Airport getAirportByName(String name) throws Exception {
        for (Airport airport : getAirportList()) {
            if (airport.getName().equals(name)) {
                return airport;
            }
        }
        throw new Exception("Unable to find airport with name " + name);
    }

}