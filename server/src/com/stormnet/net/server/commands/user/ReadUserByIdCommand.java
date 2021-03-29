package com.stormnet.net.server.commands.user;

import com.stormnet.net.data.users.User;
import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.DaoFactory;
import com.stormnet.net.server.dao.UserDao;
import com.stormnet.net.utils.date.DateUtils;
import org.json.JSONObject;
import org.json.JSONWriter;

public class ReadUserByIdCommand implements ServerCommand {

    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {
        Long userId = object.getLong("userId");

        UserDao userDao = DaoFactory.getCurrentDaoFactory().getUserDao();
        User user = userDao.readUser(userId);

        jsonWriter.object();
            jsonWriter.key("response-code").value(200);
            jsonWriter.key("response-message").value("OK");

            jsonWriter.key("response-data").object();
                jsonWriter.key("id").value(user.getId());
                jsonWriter.key("email").value(user.getEmail());
                jsonWriter.key("password").value(user.getPassword());
                jsonWriter.key("firstName").value(user.getFirstName());
                jsonWriter.key("lastName").value(user.getLastName());
                String dateOfBirthStr = DateUtils.stringFromDate(user.getDateOfBirth());
                jsonWriter.key("dateOfBirth").value(dateOfBirthStr);
            jsonWriter.endObject();
        jsonWriter.endObject();

    }
}
