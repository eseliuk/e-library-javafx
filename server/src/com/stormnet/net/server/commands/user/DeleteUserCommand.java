package com.stormnet.net.server.commands.user;

import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.DaoFactory;
import com.stormnet.net.server.dao.UserDao;
import org.json.JSONObject;
import org.json.JSONWriter;

public class DeleteUserCommand implements ServerCommand {
    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {
        Long userId = object.getLong("userId");

        UserDao userDao = DaoFactory.getCurrentDaoFactory().getUserDao();
        userDao.deleteUser(userId);

        jsonWriter.object();
            jsonWriter.key("response-code").value(200);
            jsonWriter.key("response-message").value("OK");
        jsonWriter.endObject();
    }
}
