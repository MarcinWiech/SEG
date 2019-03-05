package seg.java;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;

import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

public class WriteToXML {

    String filepath;
    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    Document doc;
    Node root;

    public WriteToXML() {

        filepath = "./src/seg/resources/views/airportsXML.xml";
        docFactory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(filepath);
            root=doc.getFirstChild();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAirport(String airportName) throws Exception {



        Node firstAirport = doc.getElementsByTagName("Airport").item(1);
        System.out.println(firstAirport.getAttributes().getNamedItem("name"));


        Element newAirport = doc.createElement("Airport");
        newAirport.setAttribute("name", airportName);
        Element newElement = doc.createElement("edition");
        newAirport.appendChild(newElement);
        root.appendChild(newAirport);

        addRunwayToAirport("Airport 2", "RUNWAY NAME");


        //                NodeList nl = doc.getElementsByTagName("*");
        //                for (int i = 0; i < nl.getLength(); i++)
        //                {
        //                    System.out.println("name is : "+nl.item(i).getNodeName());
        //                }
        //

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);
    }

    public void addRunwayToAirport(String airportName, String runwayName){

        /*
         <Runway>
            <runwayName>North</runwayName>
            <tora>1001</tora>
            <toda>2001</toda>
            <asda>3001</asda>
            <lda>4001</lda>
            <threshold>5001</threshold>
        </Runway>
         */

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = null;
        NodeList nl;
        Node nodeAriport;
        try {
            ///Airports/Airport[@name='Airport 2']
            expr = xpath.compile("/Airports/Airport[@name='" + airportName + "']");
            nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            System.out.println(nl.item(0).getAttributes().getNamedItem("name"));
            nodeAriport = nl.item(0);

            Element runway = doc.createElement("Runway");
            Element runwayNameElement = doc.createElement("runwayName");
            runwayNameElement.setNodeValue(runwayName);

            nodeAriport.appendChild(runway);
            root.appendChild(nodeAriport);

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }


        /*
         <Airport name="Airport 2">
        <Runway>
            <runwayName>North</runwayName>
            <tora>1001</tora>
            <toda>2001</toda>
            <asda>3001</asda>
            <lda>4001</lda>
            <threshold>5001</threshold>
        </Runway>
        <Runway>
            <runwayName>South</runwayName>
            <tora>1002</tora>
            <toda>2002</toda>
            <asda>3002</asda>
            <lda>4002</lda>
            <threshold>5002</threshold>
        </Runway>
    </Airport>
         */

    }
}