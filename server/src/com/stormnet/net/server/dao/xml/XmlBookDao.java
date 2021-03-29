package com.stormnet.net.server.dao.xml;

import com.stormnet.net.data.books.Book;
import com.stormnet.net.server.dao.BookDao;
import com.stormnet.net.server.dao.xml.db.DomHelper;
import com.stormnet.net.server.dao.xml.db.XmlDataBase;
import com.stormnet.net.server.dao.xml.db.XmlDbException;
import com.stormnet.net.utils.numbers.NumbersUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;

class XmlBookDao implements BookDao {
    @Override
    public List<Book> readAllBooks() {

        List<Book> allBooks = new ArrayList<>();

        try {
            XmlDataBase dataBase = XmlDataBase.getDatabase();
            Document document = dataBase.getDbTableDocument("books");

            NodeList allBookTags = document.getElementsByTagName("book");
            int tagCount = allBookTags.getLength();

            for (int i = 0; i < tagCount; i++) {
                Element bookTag = (Element) allBookTags.item(i);
                Book book = getBookDataFromTag(bookTag);
                allBooks.add(book);

            }
        } catch (Exception e) {
            throw new XmlDbException(e);
        }
        return allBooks;
    }

    @Override
    public Book readBook(Long id) {

        try {
            Element bookTag = getBookTagById(id);
            if (bookTag == null) {
                return null;
            }

            Book book = getBookDataFromTag(bookTag);
            return book;

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Long saveBook(Book book) {
        try {
            XmlDataBase dataBase = XmlDataBase.getDatabase();
            Document document = dataBase.getDbTableDocument("books");

            Element rootTag = document.getDocumentElement();
            Element bookTag = document.createElement("book");

            Long id = dataBase.getNextIdForTable("books");

            DomHelper.addChildTag (document, bookTag, "id", id.toString());
            DomHelper.addChildTag (document, bookTag, "name", book.getName());
            DomHelper.addChildTag (document, bookTag, "author", book.getAuthor());
            DomHelper.addChildTag (document, bookTag, "genre", book.getGenre());
            DomHelper.addChildTag (document, bookTag, "description", book.getDescription());

            rootTag.appendChild(bookTag);
            dataBase.saveDbTableDocument("books");

            return id;


        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateBook(Book book) {
        try {
            XmlDataBase dataBase = XmlDataBase.getDatabase();
            Long bookId = book.getId();
            Element bookTag = getBookTagById(bookId);
            if (bookTag == null) {
                return;
            }

            DomHelper.updChildTagContent(bookTag, "name", book.getName());
            DomHelper.updChildTagContent(bookTag, "author", book.getAuthor());
            DomHelper.updChildTagContent(bookTag, "genre", book.getGenre());
            DomHelper.updChildTagContent(bookTag, "description", book.getDescription());

            dataBase.saveDbTableDocument("books");

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteBook(Long id) {
        XmlDataBase dataBase = XmlDataBase.getDatabase();
        Document document = dataBase.getDbTableDocument("books");
        Element allBooksParentTag = (Element) document.getElementsByTagName("all-books").item(0);
        Element bookTag = getBookTagById(id);
        if (bookTag != null) {
            allBooksParentTag.removeChild(bookTag);
            try {
                dataBase.saveDbTableDocument("books");
            } catch (TransformerException e) {
                throw new DaoException(e);
            }
        }
    }

    private Element getBookTagById(Long bookId) {

        XmlDataBase dataBase = XmlDataBase.getDatabase();
        Document document = dataBase.getDbTableDocument("books");

        NodeList allBookTags = document.getElementsByTagName("book");
        int tagCount = allBookTags.getLength();

        for (int i = 0; i < tagCount; i++) {
            Element bookTag = (Element) allBookTags.item(i);

            Element idTag = (Element) bookTag.getElementsByTagName("id").item(0);
            String idStr = idTag.getTextContent();
            Long dbId = Long.parseLong(idStr);

            if (dbId.equals(bookId)) {
                return bookTag;
            }
        }
        return null;
    }

    private Book getBookDataFromTag (Element bookTag) {
        Element idTag = (Element) bookTag.getElementsByTagName("id").item(0);
        Long id = NumbersUtils.parseLong(idTag.getTextContent());

        Element nameTag = (Element) bookTag.getElementsByTagName("name").item(0);
        String name = nameTag.getTextContent();

        Element authorTag = (Element) bookTag.getElementsByTagName("author").item(0);
        String author = authorTag.getTextContent();

        Element genreTag = (Element) bookTag.getElementsByTagName("genre").item(0);
        String genre = genreTag.getTextContent();

        Element descriptionTag = (Element) bookTag.getElementsByTagName("description").item(0);
        String description = descriptionTag.getTextContent();



        Book book = new Book(name, author,  genre, description);
        book.setId(id);

        return book;
    }
}
