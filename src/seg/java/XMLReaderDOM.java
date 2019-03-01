package seg.java;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class XMLReaderDOM {
    private HashMap<String, Airport> airportHashMap;

    public XMLReaderDOM() {
        String filePath = "airports.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList airportNodeList = doc.getElementsByTagName("Airport");

            /** LOOPS THROUGH AIRPORTS TO ADD TO ARRAYLIST **/
            airportHashMap = new HashMap<>();
            for (int i = 0; i < airportNodeList.getLength(); i++) {
                Airport newAirport = setAirport(airportNodeList.item(i));
                airportHashMap.put(newAirport.getName(), newAirport);

                /** LOOPS THROUGH RUNWAYS TO ADD TO AIRPORT **/
                NodeList runwayNodeList = airportNodeList.item(i).getChildNodes();
                for (int j = 0; j < runwayNodeList.getLength(); j++) {
                    Node childNode = runwayNodeList.item(j);
                    if ("Runway".equals(childNode.getNodeName())) {
                        addRunway(childNode, newAirport);
                    }
                }
            }

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Creates Airport object
     **/
    private static Airport setAirport(Node node) {
        Airport airport = new Airport();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            airport.setName(element.getAttribute("name"));
        }

        return airport;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    /**
     * Creates & adds the runway to the Airport (runwayArrayList)
     **/
    private void addRunway(Node runwayNode, Airport airport) {
        if (runwayNode.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) runwayNode;
            airport.addRunway(getTagValue("runwayName", element), Integer.valueOf(getTagValue("tora", element)),
                    Integer.valueOf(getTagValue("toda", element)), Integer.valueOf(getTagValue("asda", element)),
                    Integer.valueOf(getTagValue("lda", element)), Integer.valueOf(getTagValue("threshold", element)));
        }
    }

    /**
     * Used in AirportSelectionController
     **/
    public HashMap<String, Airport> getAirportArraylist() {
        return this.airportHashMap;
    }


}