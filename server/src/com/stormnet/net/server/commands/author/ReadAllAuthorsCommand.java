package com.stormnet.net.server.commands.author;

import com.stormnet.net.data.author.Author;
import com.stormnet.net.data.users.ServerResponse;
import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.AuthorDao;
import com.stormnet.net.server.dao.DaoFactory;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.util.List;

public class ReadAllAuthorsCommand implements ServerCommand {
    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {
        DaoFactory factory = DaoFactory.getCurrentDaoFactory();
        AuthorDao authorDao = factory.getAuthorDao();
        List<Author> dbAuthors = authorDao.readAllAuthors();

        ServerResponse response = new ServerResponse(200, "OK");

        jsonWriter.object();
        jsonWriter.key("response-code").value(response.getResponseCode());
        jsonWriter.key("response-message").value(response.getResponseMessage());

        jsonWriter.key("response-data").array();

        for (Author author: dbAuthors) {
            jsonWriter.object();
            jsonWriter.key("id").value(author.getId());
            jsonWriter.key("fullName").value(author.getFullName());
            jsonWriter.key("profile").value(author.getProfile());
            jsonWriter.endObject();
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
    }
}
