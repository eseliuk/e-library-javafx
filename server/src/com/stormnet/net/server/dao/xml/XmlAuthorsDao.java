package com.stormnet.net.server.dao.xml;

import com.stormnet.net.data.author.Author;
import com.stormnet.net.server.dao.AuthorDao;
import com.stormnet.net.server.dao.xml.db.DomHelper;
import com.stormnet.net.server.dao.xml.db.XmlDataBase;
import com.stormnet.net.utils.numbers.NumbersUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;

public class XmlAuthorsDao implements AuthorDao {
    @Override
    public List<Author> readAllAuthors() {
        List<Author> allAuthors = new ArrayList<>();

        try {
            XmlDataBase dataBase = XmlDataBase.getDatabase();
            Document document = dataBase.getDbTableDocument("authors");

            NodeList allAuthorTags = document.getElementsByTagName("author");
            int tagCount = allAuthorTags.getLength();

            for (int i = 0; i < tagCount; i++) {
                Element authorTag = (Element) allAuthorTags.item(i);
                Author author = getAuthorDataFromTag(authorTag);
                allAuthors.add(author);
            }

        } catch (Exception e) {
            throw new DaoException(e);
        }
        return allAuthors;
    }

    @Override
    public Author readAuthor(Long id) {
        try {
            Element userTag = getAuthorTagById(id);
            if (userTag == null) {
                return null;
            }

            Author author = getAuthorDataFromTag(userTag);
            return author;

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Long saveAuthor(Author author) {
        try{
            XmlDataBase dataBase = XmlDataBase.getDatabase();
            Document document = dataBase.getDbTableDocument("authors");

            Element rootTag = document.getDocumentElement();

            Element authorTag = document.createElement("author");

            Long id = dataBase.getNextIdForTable("authors");

            DomHelper.addChildTag (document, authorTag, "id", id.toString());
            DomHelper.addChildTag (document, authorTag, "fullName", author.getFullName());
            DomHelper.addChildTag (document, authorTag, "profile", author.getProfile());


            rootTag.appendChild(authorTag);
            dataBase.saveDbTableDocument("authors");

            return id;

        } catch (Exception e){
            throw new DaoException(e);
        }
    }

    @Override
    public void updateAuthor(Author author) {

        try {
            XmlDataBase dataBase = XmlDataBase.getDatabase();
            Long authorId = author.getId();
            Element authorTag = getAuthorTagById(authorId);
            if (authorTag == null) {
                return;
            }

            DomHelper.updChildTagContent(authorTag, "fullName", author.getFullName());
            DomHelper.updChildTagContent(authorTag, "profile", author.getProfile());

            dataBase.saveDbTableDocument("authors");

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteAuthor(Long id) {
        XmlDataBase dataBase = XmlDataBase.getDatabase();
        Document document = dataBase.getDbTableDocument("authors");

        Element allAuthorsParentTag = (Element) document.getElementsByTagName("all-authors").item(0);
        Element authorTag = getAuthorTagById(id);
        if (authorTag != null) {
            allAuthorsParentTag.removeChild(authorTag);
            try {
                dataBase.saveDbTableDocument("authors");
            } catch (TransformerException e) {
                throw new DaoException(e);
            }
        }
    }

    private Element getAuthorTagById(Long userId) {
        XmlDataBase dataBase = XmlDataBase.getDatabase();
        Document document = dataBase.getDbTableDocument("authors");

        NodeList allAuthorTags = document.getElementsByTagName("author");
        int tagCount = allAuthorTags.getLength();

        for (int i = 0; i < tagCount; i++) {
            Element authorTag = (Element) allAuthorTags.item(i);

            Element idTag = (Element) authorTag.getElementsByTagName("id").item(0);
            String idStr = idTag.getTextContent();
            Long dbId = Long.parseLong(idStr);

            if (dbId.equals(userId)) {
                return authorTag;
            }
        }
        return null;
    }

    private Author getAuthorDataFromTag(Element authorTag) {
        Element idTag = (Element) authorTag.getElementsByTagName("id").item(0);
        Long id = NumbersUtils.parseLong(idTag.getTextContent());

        Element fullNameTag = (Element) authorTag.getElementsByTagName("fullName").item(0);
        String fullName = fullNameTag.getTextContent();

        Element profileTag = (Element) authorTag.getElementsByTagName("profile").item(0);
        String profile = profileTag.getTextContent();

        Author author = new Author(fullName, profile);
        author.setId(id);

        return author;
    }
}
