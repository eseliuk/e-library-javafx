package com.stormnet.net.server.dao;

import com.stormnet.net.server.dao.xml.XmlDaoFactory;

public abstract class DaoFactory {

    public abstract UserDao getUserDao();

    public abstract BookDao getBookDao();

    public abstract DescriptionDao getDescriptionDao();

    public abstract AuthorDao getAuthorDao();

    public static DaoFactory getCurrentDaoFactory() {
        return new XmlDaoFactory();
    }
}
