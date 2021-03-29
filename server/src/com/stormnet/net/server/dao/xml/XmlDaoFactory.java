package com.stormnet.net.server.dao.xml;

import com.stormnet.net.server.dao.*;

public class XmlDaoFactory extends DaoFactory {


    @Override
    public UserDao getUserDao() {
        return new XmlUserDao();
    }

    @Override
    public BookDao getBookDao() {
        return new XmlBookDao();
    }

    @Override
    public DescriptionDao getDescriptionDao() {
        return new XmlDescriptionDao();
    }

    @Override
    public AuthorDao getAuthorDao() {
        return new XmlAuthorsDao();
    }
}
