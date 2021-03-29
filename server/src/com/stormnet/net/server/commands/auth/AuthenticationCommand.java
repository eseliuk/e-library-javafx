package com.stormnet.net.server.commands.auth;

import com.stormnet.net.data.users.Account;
import com.stormnet.net.data.users.ServerResponse;
import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.xml.db.XmlDataBase;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class AuthenticationCommand implements ServerCommand {

    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {

        String login = object.getString("login");
        String password = object.getString("password");

        Account account = new Account(login, password);
        System.out.println(account);

        XmlDataBase dataBase = XmlDataBase.getDatabase();
        Document document = dataBase.getDbTableDocument("users");

        NodeList allUserTags = document.getElementsByTagName("user");
        int tagCount = allUserTags.getLength();


        ServerResponse response = null;
        for (int i = 0; i < tagCount; i++) {
            Element userTag = (Element) allUserTags.item(i);

            Element emailTag = (Element) userTag.getElementsByTagName("email").item(0);
            String authEmail = emailTag.getTextContent();

            Element passwordTag = (Element) userTag.getElementsByTagName("password").item(0);
            String authPassword = passwordTag.getTextContent();

            if (authEmail.equals(login) & authPassword.equals(password)) {
                Account userAccount = new Account(login, password);
                System.out.println(userAccount);
                response = new ServerResponse(200, "OK");
                break;

            } else if(login.equals("admin") & password.equals("admin")) {
                Account adminAccount = new Account(login, password);
                System.out.println(adminAccount);
                response = new ServerResponse(100, "OK");

            } else if(login.equals("moder") & password.equals("moder")) {
                Account moderAccount = new Account(login, password);
                System.out.println(moderAccount);
                response = new ServerResponse(300, "OK");
            }
            else {
                Account errorAccount = new Account(login, password);
                System.out.println(errorAccount);
                response = new ServerResponse(401, "Error");
            }
        }

        jsonWriter.object();
        jsonWriter.key("response-code").value(response.getResponseCode());
        jsonWriter.key("response-message").value(response.getResponseMessage());
        jsonWriter.endObject();
    }

}
