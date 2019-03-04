
package seg.java.xml;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the seg.java.xml package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Asda_QNAME = new QName("", "asda");
    private final static QName _Lda_QNAME = new QName("", "lda");
    private final static QName _RunwayName_QNAME = new QName("", "runwayName");
    private final static QName _Threshold_QNAME = new QName("", "threshold");
    private final static QName _Tora_QNAME = new QName("", "tora");
    private final static QName _Toda_QNAME = new QName("", "toda");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: seg.java.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Runway }
     * 
     */
    public Runway createRunway() {
        return new Runway();
    }

    /**
     * Create an instance of {@link Airport }
     * 
     */
    public Airport createAirport() {
        return new Airport();
    }

    /**
     * Create an instance of {@link Airports }
     * 
     */
    public Airports createAirports() {
        return new Airports();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "asda")
    public JAXBElement<BigInteger> createAsda(BigInteger value) {
        return new JAXBElement<BigInteger>(_Asda_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "lda")
    public JAXBElement<BigInteger> createLda(BigInteger value) {
        return new JAXBElement<BigInteger>(_Lda_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "runwayName")
    public JAXBElement<String> createRunwayName(String value) {
        return new JAXBElement<String>(_RunwayName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "threshold")
    public JAXBElement<BigInteger> createThreshold(BigInteger value) {
        return new JAXBElement<BigInteger>(_Threshold_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tora")
    public JAXBElement<BigInteger> createTora(BigInteger value) {
        return new JAXBElement<BigInteger>(_Tora_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "toda")
    public JAXBElement<BigInteger> createToda(BigInteger value) {
        return new JAXBElement<BigInteger>(_Toda_QNAME, BigInteger.class, null, value);
    }

}
