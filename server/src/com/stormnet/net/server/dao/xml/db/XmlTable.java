package com.stormnet.net.server.dao.xml.db;

import org.w3c.dom.Document;

public class XmlTable {

    private String name;

    private String xmlFilePath;

    private Long currentId;

    private Document document;

    public XmlTable(String name, String xmlFilePath, Long currentId, Document document) {
        this.name = name;
        this.xmlFilePath = xmlFilePath;
        this.currentId = currentId;
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public String getXmlFilePath() {
        return xmlFilePath;
    }

    public Document getDocument() {
        return document;
    }

    public Long getNextId() {
        currentId++;
        return currentId;
    }
}
