package com.stormnet.net.client.maintenance.impl;

import com.stormnet.net.client.exceptions.ServiceException;
import com.stormnet.net.client.maintenance.UserService;
import com.stormnet.net.client.services.SocketConnection;
import com.stormnet.net.data.users.User;
import com.stormnet.net.utils.date.DateUtils;
import com.stormnet.net.utils.numbers.NumbersUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {


    @Override
    public List<User> getAllUsers()  {

        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("read-all-users-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
            jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            List<User> allUsers = new ArrayList<>();
            int responseCode = responseJson.getInt("response-code");

            if (responseCode == 200) {
                JSONArray allUsersJson = responseJson.getJSONArray("response-data");
                int usersCount = allUsersJson.length();

                for (int i = 0; i < usersCount; i++) {
                    JSONObject userJson = allUsersJson.getJSONObject(i);

                    Long id = userJson.getLong("id");
                    String email = userJson.getString("email");
                    String password = userJson.getString("password");
                    String firstName = userJson.getString("firstName");
                    String lastName = userJson.getString("lastName");
                    String dateOfBirthStr = userJson.getString("dateOfBirth");
                    Date dateOfBirth = DateUtils.dateFromString(dateOfBirthStr);

                    User user = new User(email, password, firstName, lastName, dateOfBirth);
                    user.setId(id);

                    allUsers.add(user);
                }
            }
            connection.close();

            return allUsers;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User readUserById(Long userId) {

        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("read-users-by-id-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
            jsonWriter.key("userId").value(userId);
            jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            List<User> allUsers = new ArrayList<>();
            int responseCode = responseJson.getInt("response-code");


            User user = null;
            if (responseCode == 200) {
                JSONObject userJson = responseJson.getJSONObject("response-data");

                Long id = userJson.getLong("id");
                String email = userJson.getString("email");
                String password = userJson.getString("password");
                String firstName = userJson.getString("firstName");
                String lastName = userJson.getString("lastName");
                String dateOfBirthStr = userJson.getString("dateOfBirth");
                Date dateOfBirth = DateUtils.dateFromString(dateOfBirthStr);

                user = new User(email, password, firstName, lastName, dateOfBirth);
                user.setId(id);

                allUsers.add(user);

            }
            connection.close();

            return user;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long saveUser(User user) {
        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("save-user-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
                jsonWriter.key("userId").value(NumbersUtils.toString(user.getId()));
                jsonWriter.key("email").value(user.getEmail());
                jsonWriter.key("password").value(user.getPassword());
                jsonWriter.key("firstName").value(user.getFirstName());
                jsonWriter.key("lastName").value(user.getLastName());
                jsonWriter.key("dateOfBirth").value(user.getDateOfBirthStr());
            jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            int responseCode = responseJson.getInt("response-code");
            if (responseCode == 200) {
                JSONObject responseData = responseJson.getJSONObject("response-data");
                Long userId = responseData.getLong("userId");

                return userId;
            }
            connection.close();

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        throw new RuntimeException("Can not Save User!");
    }

    @Override
    public void delUser(Long userId) {
        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
                jsonWriter.key("request-header").object();
                jsonWriter.key("command-name").value("delete-user-command");
            jsonWriter.endObject();

                jsonWriter.key("request-data").object();
                jsonWriter.key("userId").value(userId.toString());
            jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();
            int responseCode = responseJson.getInt("response-code");

            if (responseCode != 200) {
                throw new RuntimeException("Can't delete user by id:" + userId);
            }
            connection.close();

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
