package com.stormnet.net.server.commands.user;

import com.stormnet.net.data.users.ServerResponse;
import com.stormnet.net.data.users.User;
import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.DaoFactory;
import com.stormnet.net.server.dao.UserDao;
import com.stormnet.net.utils.date.DateUtils;
import com.stormnet.net.utils.numbers.NumbersUtils;
import org.json.JSONObject;
import org.json.JSONWriter;
import java.util.Date;

public class SaveUserCommand implements ServerCommand {
    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {

        Long userId = NumbersUtils.parseLong(object.getString("userId"));
        String email = object.getString("email");
        String password = object.getString("password");
        String firstName = object.getString("firstName");
        String lastName = object.getString("lastName");
        String dateOfBirthStr = object.getString("dateOfBirth");
        Date dateOfBirth = DateUtils.dateFromString(dateOfBirthStr);

        User user = new User(email, password, firstName, lastName, dateOfBirth);
        user.setId(userId);

        DaoFactory factory = DaoFactory.getCurrentDaoFactory();
        UserDao userDao = factory.getUserDao();

        if (userId == null) {
            userId = userDao.saveUser(user);
        } else {
            userDao.updateUser(user);
        }

        ServerResponse response = new ServerResponse(200, "OK");

        jsonWriter.object();
            jsonWriter.key("response-code").value(response.getResponseCode());
            jsonWriter.key("response-message").value(response.getResponseMessage());
            jsonWriter.key("response-data").object();
                jsonWriter.key("userId").value(userId);
            jsonWriter.endObject();
        jsonWriter.endObject();
    }
}
