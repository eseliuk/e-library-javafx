package com.stormnet.net.server.dao.xml.db;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XmlDataBase {

    private static final String ALL_DB_TABLES_FILE_PATH = "d:/e-library-db/xml-db-tables.xml";

    private static final XmlDataBase database = new XmlDataBase();

    public static XmlDataBase getDatabase() {
        return database;
    }

    private Map<String, XmlTable> allDbTables = new HashMap<>();

    private Document allTablesDocument;

    private XmlDataBase()  {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            allTablesDocument = docBuilder.parse(ALL_DB_TABLES_FILE_PATH);

            NodeList allDbTablesTag = allTablesDocument.getElementsByTagName("db-table");
            int tagCount = allDbTablesTag.getLength();

            for (int i = 0; i < tagCount; i++) {
                Element dbTableTag = (Element) allDbTablesTag.item(i);

                String tableName = dbTableTag.getAttribute("name");
                String tableFile = dbTableTag.getAttribute("table-file");

                Element maxIdValueTag = (Element) dbTableTag.getElementsByTagName("max-id-value").item(0);
                String maxIdStr = maxIdValueTag.getTextContent();
                long maxId = Long.parseLong(maxIdStr);

                Document tableDocument = docBuilder.parse(tableFile);

                XmlTable xmlTable = new XmlTable(tableName, tableFile, maxId, tableDocument);

                allDbTables.put(tableName,xmlTable);
            }
        } catch (Exception e) {
            throw new XmlDbException(e);
        }

    }

    public Document getDbTableDocument(String tableName) {
        XmlTable table = allDbTables.get(tableName);
        return table.getDocument();
    }

    public void saveDbTableDocument(String tableName) throws TransformerException {
        XmlTable table = allDbTables.get(tableName);
        Document document = table.getDocument();
        saveXmlDocument(document, table.getXmlFilePath());
    }

    public Long getNextIdForTable(String tableName) throws TransformerException {
        XmlTable table = allDbTables.get(tableName);
        Long nextId = table.getNextId();

        NodeList allTablesTags = allTablesDocument.getElementsByTagName("db-table");
        int tableCount = allTablesTags.getLength();
        for (int i = 0; i < tableCount; i++) {
            Element dbTableTag = (Element) allTablesTags.item(i);
            String nameFromXml = dbTableTag.getAttribute("name");

            if(nameFromXml.equals(tableName)) {
                Element maxIdTag = (Element) dbTableTag.getElementsByTagName("max-id-value").item(0);
                maxIdTag.setTextContent(nextId.toString());

                saveXmlDocument (allTablesDocument, ALL_DB_TABLES_FILE_PATH);
                break;
            }
        }

        return nextId;
    }

    private void saveXmlDocument(Document document, String filePath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);


    }
}
