package com.stormnet.net.server.dao.xml;

import com.stormnet.net.data.users.User;
import com.stormnet.net.server.dao.UserDao;
import com.stormnet.net.server.dao.xml.db.DomHelper;
import com.stormnet.net.server.dao.xml.db.XmlDataBase;
import com.stormnet.net.utils.date.DateUtils;
import com.stormnet.net.utils.numbers.NumbersUtils;
import org.w3c.dom.*;
import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class XmlUserDao implements UserDao {

    @Override
    public List<User> readAllUsers() {
        List<User> allUsers = new ArrayList<>();

        try {
            XmlDataBase dataBase = XmlDataBase.getDatabase();
            Document document = dataBase.getDbTableDocument("users");

            NodeList allUserTags = document.getElementsByTagName("user");
            int tagCount = allUserTags.getLength();

            for (int i = 0; i < tagCount; i++) {
                Element userTag = (Element) allUserTags.item(i);
                User user = getUserDataFromTag(userTag);
               allUsers.add(user);
            }

        } catch (Exception e) {
            throw new DaoException(e);
        }
        return allUsers;
    }

    @Override
    public User readUser(Long id) {

        try {
            Element userTag = getUserTagById(id);
            if (userTag == null) {
                return null;
            }

            User user = getUserDataFromTag(userTag);
            return user;

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public Long saveUser(User user) {
        try{
            XmlDataBase dataBase = XmlDataBase.getDatabase();
            Document document = dataBase.getDbTableDocument("users");

            Element rootTag = document.getDocumentElement();

            Element userTag = document.createElement("user");

            Long id = dataBase.getNextIdForTable("users");

            DomHelper.addChildTag (document, userTag, "id", id.toString());
            DomHelper.addChildTag (document, userTag, "email", user.getEmail());
            DomHelper.addChildTag (document, userTag, "password", user.getPassword());
            DomHelper.addChildTag (document, userTag, "firstName", user.getFirstName());
            DomHelper.addChildTag (document, userTag, "lastName", user.getFirstName());
            DomHelper.addChildTag (document, userTag, "dateOfBirth", user.getDateOfBirthStr());

            rootTag.appendChild(userTag);
            dataBase.saveDbTableDocument("users");

            return id;

        } catch (Exception e){
            throw new DaoException(e);
        }
    }

    @Override
    public void updateUser(User user) {

        try {
            XmlDataBase dataBase = XmlDataBase.getDatabase();
            Long userId = user.getId();
            Element userTag = getUserTagById(userId);
            if (userTag == null) {
                return;
            }

        DomHelper.updChildTagContent(userTag, "email", user.getEmail());
        DomHelper.updChildTagContent(userTag, "password", user.getPassword());
        DomHelper.updChildTagContent(userTag, "firstName", user.getFirstName());
        DomHelper.updChildTagContent(userTag, "lastName", user.getLastName());
        DomHelper.updChildTagContent(userTag, "dateOfBirth", user.getDateOfBirthStr());

        dataBase.saveDbTableDocument("users");

        } catch (Exception e) {
            throw new DaoException(e);
        }
   }

    @Override
    public void deleteUser(Long id) {
        XmlDataBase dataBase = XmlDataBase.getDatabase();
        Document document = dataBase.getDbTableDocument("users");
        Element allUsersParentTag = (Element) document.getElementsByTagName("all-users").item(0);
        Element userTag = getUserTagById(id);
        if (userTag != null) {
            allUsersParentTag.removeChild(userTag);
            try {
                dataBase.saveDbTableDocument("users");
            } catch (TransformerException e) {
                throw new DaoException(e);
            }
        }
    }

    private Element getUserTagById(Long userId) {
        XmlDataBase dataBase = XmlDataBase.getDatabase();
        Document document = dataBase.getDbTableDocument("users");

        NodeList allUserTags = document.getElementsByTagName("user");
        int tagCount = allUserTags.getLength();

        for (int i = 0; i < tagCount; i++) {
            Element userTag = (Element) allUserTags.item(i);

            Element idTag = (Element) userTag.getElementsByTagName("id").item(0);
            String idStr = idTag.getTextContent();
            Long dbId = Long.parseLong(idStr);

            if (dbId.equals(userId)) {
                return userTag;
            }
        }
        return null;
    }
    private User getUserDataFromTag (Element userTag) {
        Element idTag = (Element) userTag.getElementsByTagName("id").item(0);
        Long id = NumbersUtils.parseLong(idTag.getTextContent());

        Element emailTag = (Element) userTag.getElementsByTagName("email").item(0);
        String email = emailTag.getTextContent();

        Element passwordTag = (Element) userTag.getElementsByTagName("password").item(0);
        String password = passwordTag.getTextContent();

        Element firstNameTag = (Element) userTag.getElementsByTagName("firstName").item(0);
        String firstName = firstNameTag.getTextContent();

        Element lastNameTag = (Element) userTag.getElementsByTagName("lastName").item(0);
        String lastName = lastNameTag.getTextContent();

        Element dateOfBirthTag = (Element) userTag.getElementsByTagName("dateOfBirth").item(0);
        String dateOfBirthStr = dateOfBirthTag.getTextContent();
        Date dateOfBirth = DateUtils.dateFromString(dateOfBirthStr);


        User user = new User(email, password, firstName, lastName, dateOfBirth);
        user.setId(id);

        return user;
    }
}
