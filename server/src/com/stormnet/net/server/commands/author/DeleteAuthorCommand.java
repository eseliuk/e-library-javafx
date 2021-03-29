package com.stormnet.net.server.commands.author;

import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.AuthorDao;
import com.stormnet.net.server.dao.DaoFactory;
import org.json.JSONObject;
import org.json.JSONWriter;

public class DeleteAuthorCommand implements ServerCommand {
    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {
        Long authorId = object.getLong("authorId");

        AuthorDao authorDao = DaoFactory.getCurrentDaoFactory().getAuthorDao();
        authorDao.deleteAuthor(authorId);

        jsonWriter.object();
        jsonWriter.key("response-code").value(200);
        jsonWriter.key("response-message").value("OK");
        jsonWriter.endObject();
    }
}
