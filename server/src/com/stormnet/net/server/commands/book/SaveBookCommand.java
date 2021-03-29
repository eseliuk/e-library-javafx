package com.stormnet.net.server.commands.book;

import com.stormnet.net.data.books.Book;
import com.stormnet.net.data.users.ServerResponse;
import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.BookDao;
import com.stormnet.net.server.dao.DaoFactory;
import com.stormnet.net.utils.numbers.NumbersUtils;
import org.json.JSONObject;
import org.json.JSONWriter;

public class SaveBookCommand implements ServerCommand {

    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {

        Long bookId = NumbersUtils.parseLong(object.getString("bookId"));
        String name = object.getString("name");
        String author = object.getString("author");
        String genre = object.getString("genre");
        String description = object.getString("description");

        Book book = new Book (name, author,genre,description);
        book.setId(bookId);

        DaoFactory factory = DaoFactory.getCurrentDaoFactory();
        BookDao bookDao = factory.getBookDao();

        if (bookId == null) {
            bookId = bookDao.saveBook(book);
        } else {
            bookDao.updateBook(book);
        }

        ServerResponse response = new ServerResponse(200, "OK");

        jsonWriter.object();
            jsonWriter.key("response-code").value(response.getResponseCode());
            jsonWriter.key("response-message").value(response.getResponseMessage());
            jsonWriter.key("response-data").object();
                jsonWriter.key("bookId").value(bookId);
            jsonWriter.endObject();
        jsonWriter.endObject();

    }
}
