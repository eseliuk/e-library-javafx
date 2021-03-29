package com.stormnet.net.server.commands.book;

import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.BookDao;
import com.stormnet.net.server.dao.DaoFactory;
import org.json.JSONObject;
import org.json.JSONWriter;

public class DeleteBookCommand implements ServerCommand {

    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {
        Long bookId = object.getLong("bookId");

        BookDao bookDao = DaoFactory.getCurrentDaoFactory().getBookDao();
        bookDao.deleteBook(bookId);

        jsonWriter.object();
        jsonWriter.key("response-code").value(200);
        jsonWriter.key("response-message").value("OK");
        jsonWriter.endObject();

    }
}
