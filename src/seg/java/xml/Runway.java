
package seg.java.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}runwayName"/>
 *         &lt;element ref="{}tora"/>
 *         &lt;element ref="{}toda"/>
 *         &lt;element ref="{}asda"/>
 *         &lt;element ref="{}lda"/>
 *         &lt;element ref="{}threshold"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "runwayName",
    "tora",
    "toda",
    "asda",
    "lda",
    "threshold"
})
@XmlRootElement(name = "Runway")
public class Runway {

    @XmlElement(required = true)
    protected String runwayName;
    @XmlElement(required = true)
    protected BigInteger tora;
    @XmlElement(required = true)
    protected BigInteger toda;
    @XmlElement(required = true)
    protected BigInteger asda;
    @XmlElement(required = true)
    protected BigInteger lda;
    @XmlElement(required = true)
    protected BigInteger threshold;

    /**
     * Gets the value of the runwayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunwayName() {
        return runwayName;
    }

    /**
     * Sets the value of the runwayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunwayName(String value) {
        this.runwayName = value;
    }

    /**
     * Gets the value of the tora property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTora() {
        return tora;
    }

    /**
     * Sets the value of the tora property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTora(BigInteger value) {
        this.tora = value;
    }

    /**
     * Gets the value of the toda property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getToda() {
        return toda;
    }

    /**
     * Sets the value of the toda property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setToda(BigInteger value) {
        this.toda = value;
    }

    /**
     * Gets the value of the asda property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAsda() {
        return asda;
    }

    /**
     * Sets the value of the asda property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAsda(BigInteger value) {
        this.asda = value;
    }

    /**
     * Gets the value of the lda property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLda() {
        return lda;
    }

    /**
     * Sets the value of the lda property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLda(BigInteger value) {
        this.lda = value;
    }

    /**
     * Gets the value of the threshold property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getThreshold() {
        return threshold;
    }

    /**
     * Sets the value of the threshold property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setThreshold(BigInteger value) {
        this.threshold = value;
    }

}
