package com.stormnet.net.server.commands.auth;

import com.stormnet.net.data.users.ServerResponse;
import com.stormnet.net.data.users.User;
import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.DaoFactory;
import com.stormnet.net.server.dao.UserDao;
import com.stormnet.net.utils.date.DateUtils;
import org.json.JSONObject;
import org.json.JSONWriter;
import java.util.Date;

public class RegistrationCommand implements ServerCommand {
    @Override
    public void processCommand(JSONObject clientData, JSONWriter jsonWriter) {

        String email = clientData.getString("email");
        String password = clientData.getString("password");
        String repeatPassword = clientData.getString("repeatPassword");
        String firstName = clientData.getString("firstName");
        String lastName = clientData.getString("lastName");
        String dateOfBirthStr = clientData.getString("dateOfBirth");
        Date dateOfBirth = DateUtils.dateFromString(dateOfBirthStr);

        if(password.equals(repeatPassword)) {
            ServerResponse response = new ServerResponse(200, "OK");

            User user = new User(email, password, firstName, lastName, dateOfBirth);

            DaoFactory factory = DaoFactory.getCurrentDaoFactory();
            UserDao userDao = factory.getUserDao();
            userDao.saveUser(user);

            jsonWriter.object();
            jsonWriter.key("response-code").value(response.getResponseCode());
            jsonWriter.key("response-message").value(response.getResponseMessage());
            jsonWriter.endObject();

        } else {
            ServerResponse response = new ServerResponse(400, "Bad Request");

            jsonWriter.object();
            jsonWriter.key("response-code").value(response.getResponseCode());
            jsonWriter.key("response-message").value(response.getResponseMessage());
            jsonWriter.endObject();
        }
    }
}
