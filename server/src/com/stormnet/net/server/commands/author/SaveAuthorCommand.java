package com.stormnet.net.server.commands.author;

import com.stormnet.net.data.author.Author;
import com.stormnet.net.data.users.ServerResponse;
import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.AuthorDao;
import com.stormnet.net.server.dao.DaoFactory;
import com.stormnet.net.utils.numbers.NumbersUtils;
import org.json.JSONObject;
import org.json.JSONWriter;


public class SaveAuthorCommand implements ServerCommand {
    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {

        Long authorId = NumbersUtils.parseLong(object.getString("authorId"));
        String fullName = object.getString("fullName");
        String profile = object.getString("profile");

        Author author = new Author(fullName,profile);
        author.setId(authorId);

        DaoFactory factory = DaoFactory.getCurrentDaoFactory();
        AuthorDao authorDao = factory.getAuthorDao();

        if (authorId == null) {
            authorId = authorDao.saveAuthor(author);
        } else {
            authorDao.updateAuthor(author);
        }

        ServerResponse response = new ServerResponse(200, "OK");

        jsonWriter.object();
        jsonWriter.key("response-code").value(response.getResponseCode());
        jsonWriter.key("response-message").value(response.getResponseMessage());
        jsonWriter.key("response-data").object();
            jsonWriter.key("authorId").value(authorId);
        jsonWriter.endObject();
        jsonWriter.endObject();
    }
}
