package seg.java.xml;

import org.w3c.dom.Element;

public interface XMLEntityInterface<T extends XMLEntityInterface<T>> {

    T loadFromXMLElement(Element element){
        return new T();
    }
}
