package com.stormnet.net.server.commands.book;

import com.stormnet.net.data.books.Book;
import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.BookDao;
import com.stormnet.net.server.dao.DaoFactory;
import org.json.JSONObject;
import org.json.JSONWriter;

public class ReadBookByIdCommand implements ServerCommand {

    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {

        Long bookId = object.getLong("bookId");

        BookDao bookDao = DaoFactory.getCurrentDaoFactory().getBookDao();
        Book book = bookDao.readBook(bookId);

        jsonWriter.object();
        jsonWriter.key("response-code").value(200);
        jsonWriter.key("response-message").value("OK");
        jsonWriter.key("response-data").object();
            jsonWriter.key("id").value(book.getId());
            jsonWriter.key("name").value(book.getName());
            jsonWriter.key("author").value(book.getAuthor());
            jsonWriter.key("genre").value(book.getGenre());
            jsonWriter.key("description").value(book.getDescription());
        jsonWriter.endObject();
        jsonWriter.endObject();
    }
}
