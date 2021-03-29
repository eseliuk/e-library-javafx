package com.stormnet.net.server.commands.user;

import com.stormnet.net.data.users.ServerResponse;
import com.stormnet.net.data.users.User;
import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.DaoFactory;
import com.stormnet.net.server.dao.UserDao;
import com.stormnet.net.utils.date.DateUtils;
import org.json.JSONObject;
import org.json.JSONWriter;
import java.util.List;

public class ReadAllUsersCommand implements ServerCommand {

    @Override
    public void processCommand(JSONObject clientData, JSONWriter jsonWriter) {
        DaoFactory factory = DaoFactory.getCurrentDaoFactory();
        UserDao userDao = factory.getUserDao();
        List<User> dbUsers = userDao.readAllUsers();

        ServerResponse response = new ServerResponse(200, "OK");

        jsonWriter.object();
            jsonWriter.key("response-code").value(response.getResponseCode());
            jsonWriter.key("response-message").value(response.getResponseMessage());

            jsonWriter.key("response-data").array();

                for (User user: dbUsers) {
                    jsonWriter.object();
                    jsonWriter.key("id").value(user.getId());
                    jsonWriter.key("email").value(user.getEmail());
                    jsonWriter.key("password").value(user.getPassword());
                    jsonWriter.key("firstName").value(user.getFirstName());
                    jsonWriter.key("lastName").value(user.getLastName());
                    String dateOfBirthStr = DateUtils.stringFromDate(user.getDateOfBirth());
                    jsonWriter.key("dateOfBirth").value(dateOfBirthStr);
                    jsonWriter.endObject();
                }
            jsonWriter.endArray();
        jsonWriter.endObject();
    }
}
