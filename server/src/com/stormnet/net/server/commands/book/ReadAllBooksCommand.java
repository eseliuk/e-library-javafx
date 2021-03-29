package com.stormnet.net.server.commands.book;

import com.stormnet.net.data.books.Book;
import com.stormnet.net.data.users.ServerResponse;
import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.BookDao;
import com.stormnet.net.server.dao.DaoFactory;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.util.List;

public class ReadAllBooksCommand implements ServerCommand {

    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {
        DaoFactory factory = DaoFactory.getCurrentDaoFactory();
        BookDao bookDao = factory.getBookDao();
        List<Book> dbBooks = bookDao.readAllBooks();

        ServerResponse response = new ServerResponse(200, "OK");

        jsonWriter.object();
            jsonWriter.key("response-code").value(response.getResponseCode());
            jsonWriter.key("response-message").value(response.getResponseMessage());

            jsonWriter.key("response-data").array();

                for (Book book:dbBooks) {
                    jsonWriter.object();
                    jsonWriter.key("id").value(book.getId());
                    jsonWriter.key("name").value(book.getName());
                    jsonWriter.key("author").value(book.getAuthor());
                    jsonWriter.key("genre").value(book.getGenre());
                    jsonWriter.key("description").value(book.getDescription());
                    jsonWriter.endObject();
                }
            jsonWriter.endArray();
        jsonWriter.endObject();

    }
}
