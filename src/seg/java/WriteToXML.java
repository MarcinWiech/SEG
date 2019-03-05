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

public class WriteToXML {

        public void addAirport(String airportName) throws Exception {

//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//            Document document = documentBuilder.parse("seg/resources/views/airportsXML.xml");
//            Element root = document.getDocumentElement();
//
//            Collection<Server> servers = new ArrayList<Server>();
//            servers.add(new Server());
//
//            for (Server server : servers) {
//                // server elements
//                Element newServer = document.createElement("server");
//
//                Element name = document.createElement("name");
//                name.appendChild(document.createTextNode(server.getName()));
//                newServer.appendChild(name);
//
//                Element port = document.createElement("port");
//                port.appendChild(document.createTextNode(Integer.toString(server.getPort())));
//                newServer.appendChild(port);
//
//                root.appendChild(newServer);
//            }
//
//            DOMSource source = new DOMSource(document);
//
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            StreamResult result = new StreamResult("server.xml");
//            transformer.transform(source, result);
//
//            Document doc = docBuilder.parse(is);
//            Node root=doc.getFirstChild();
//            Element newserver=doc.createElement("new_server");
//            root.appendChild(newserver);


            try {
                String filepath = "D:\\Soton II\\COMP2211 Software Eng Group Project\\SEG\\src\\seg\\resources\\views\\airportsXML.xml";
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(filepath);

                // Get the root element
                Node airports = doc.getFirstChild();

                // Get the staff element , it may not working if tag has spaces, or
                // whatever weird characters in front...it's better to use
                // getElementsByTagName() to get it directly.
                // Node staff = company.getFirstChild();

                // Get the staff element by tag name directly
                Node staff = doc.getElementsByTagName("airport").item(0);

                System.out.println(staff.getNodeName());

//                // update staff attribute
//                NamedNodeMap attr = staff.getAttributes();
//                Node nodeAttr = attr.getNamedItem("id");
//                nodeAttr.setTextContent("2");
//
//                // append a new node to staff
//                Element age = doc.createElement("age");
//                age.appendChild(doc.createTextNode("28"));
//                staff.appendChild(age);
//
//                // loop the staff child node
//                NodeList list = staff.getChildNodes();
//
//                for (int i = 0; i < list.getLength(); i++) {
//
//                    Node node = list.item(i);
//
//                    // get the salary element, and update the value
//                    if ("salary".equals(node.getNodeName())) {
//                        node.setTextContent("2000000");
//                    }
//
//                    //remove firstname
//                    if ("firstname".equals(node.getNodeName())) {
//                        staff.removeChild(node);
//                    }
//
//                }

                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(filepath));
                transformer.transform(source, result);

                System.out.println("Done");

            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
}

