package com.stormnet.net.server.dao.xml.db;

public class XmlDbException extends RuntimeException {
    public XmlDbException(Throwable cause) {
        super("Errors occurred while accessing the database: ", cause);
    }
}
