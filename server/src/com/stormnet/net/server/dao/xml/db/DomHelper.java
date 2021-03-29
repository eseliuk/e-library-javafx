package com.stormnet.net.server.dao.xml.db;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class DomHelper {

    public static Element getChildTagByName (Element parentTag, String childName) {
        return  (Element) parentTag.getElementsByTagName(childName).item(0);
    }

    public static void updChildTagContent (Element parentTag, String childName, String content) {
        Element childTag = getChildTagByName(parentTag, childName);
        childTag.setTextContent(content);
    }

    public static void addChildTag (Document document, Element parentTag, String childName, String content) {
        Element childTag = document.createElement(childName);
        Text lastNameData = document.createTextNode(content);
        childTag.appendChild(lastNameData);
        parentTag.appendChild(childTag);
    }
}
