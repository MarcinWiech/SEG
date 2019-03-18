package seg.java;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import seg.java.models.AirportOld;
import seg.java.models.RunwayOld;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class XMLReaderDOM {
    private HashMap<String, AirportOld> airportHashMap;

    public XMLReaderDOM() {
        String filePath = "./src/airportsXML.xml";
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
                AirportOld newAirportOld = setAirport(airportNodeList.item(i));
                airportHashMap.put(newAirportOld.getName(), newAirportOld);

                /** LOOPS THROUGH RUNWAYS TO ADD TO AIRPORT **/
                NodeList runwayNodeList = airportNodeList.item(i).getChildNodes();
                for (int j = 0; j < runwayNodeList.getLength(); j++) {
                    Node childNode = runwayNodeList.item(j);
                    if ("Runway".equals(childNode.getNodeName())) {
                        addRunway(childNode, newAirportOld);
                    }
                }

                //  We set the reciprocal runways
                for (String runwayName : newAirportOld.getRunwayHashMap().keySet()) {
                    RunwayOld runwayOld = newAirportOld.getRunwayHashMap().get(runwayName);
                    String reciprocalName = runwayOld.getReciprocalName();

                    if (runwayName.equals("null") == false) {
                        runwayOld.setReciprocal(newAirportOld.getRunwayHashMap().get(reciprocalName));
                    } else {
                        runwayOld.setReciprocal(null);
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
    private static AirportOld setAirport(Node node) {
        AirportOld airportOld = new AirportOld();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            airportOld.setName(element.getAttribute("name"));
            airportOld.setNumberOfRunways(element.getChildNodes().getLength());
        }

        return airportOld;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    /**
     * Creates & adds the runway to the Airport (runwayArrayList)
     **/
    private void addRunway(Node runwayNode, AirportOld airportOld) {
        if (runwayNode.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) runwayNode;
            airportOld.addRunway(getTagValue("runwayName", element), getTagValue("reciprocalName", element),
                    Double.valueOf(getTagValue("tora", element)), Double.valueOf(getTagValue("toda", element)),
                    Double.valueOf(getTagValue("asda", element)), Double.valueOf(getTagValue("lda", element)),
                    Double.valueOf(getTagValue("threshold", element)));
        }
    }

    /**
     * Used in AirportSelectionController
     **/
    public HashMap<String, AirportOld> getAirportArraylist() {
        return this.airportHashMap;
    }


}