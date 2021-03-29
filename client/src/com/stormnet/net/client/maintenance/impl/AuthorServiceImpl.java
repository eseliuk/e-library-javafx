package com.stormnet.net.client.maintenance.impl;

import com.stormnet.net.client.exceptions.ServiceException;
import com.stormnet.net.client.maintenance.AuthorService;
import com.stormnet.net.client.services.SocketConnection;
import com.stormnet.net.data.author.Author;
import com.stormnet.net.utils.numbers.NumbersUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;
import java.util.ArrayList;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    @Override
    public List<Author> getAllAuthors() {
        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("read-all-authors-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
            jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            List<Author> allAuthors = new ArrayList<>();
            int responseCode = responseJson.getInt("response-code");

            if (responseCode == 200) {
                JSONArray allAuthorsJson = responseJson.getJSONArray("response-data");
                int authorsCount = allAuthorsJson.length();

                for (int i = 0; i < authorsCount; i++) {
                    JSONObject authorJson = allAuthorsJson.getJSONObject(i);

                    Long id = authorJson.getLong("id");
                    String fullName = authorJson.getString("fullName");
                    String profile = authorJson.getString("profile");

                    Author author = new Author(fullName,profile);
                    author.setId(id);

                    allAuthors.add(author);
                }
            }
            connection.close();

            return allAuthors;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Author readAuthorById(Long authorId) {
        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("read-author-by-id-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
            jsonWriter.key("authorId").value(authorId);
            jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            List<Author> allAuthors = new ArrayList<>();
            int responseCode = responseJson.getInt("response-code");


            Author author = null;
            if (responseCode == 200) {
                JSONObject authorJson = responseJson.getJSONObject("response-data");

                Long id = authorJson.getLong("id");
                String fullName = authorJson.getString("fullName");
                String profile = authorJson.getString("profile");

                author = new Author(fullName, profile);
                author.setId(id);

                allAuthors.add(author);

            }
            connection.close();

            return author;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long saveAuthor(Author author) {
        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("save-author-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
                jsonWriter.key("authorId").value(NumbersUtils.toString(author.getId()));
                jsonWriter.key("fullName").value(author.getFullName());
                jsonWriter.key("profile").value(author.getProfile());
             jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            int responseCode = responseJson.getInt("response-code");
            if (responseCode == 200) {
                JSONObject responseData = responseJson.getJSONObject("response-data");
                Long authorId = responseData.getLong("authorId");

                return authorId;
            }
            connection.close();

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        throw new RuntimeException("Can't Save Author!");
    }

    @Override
    public void delAuthor(Long authorId) {
        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("delete-author-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
            jsonWriter.key("authorId").value(authorId.toString());
            jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();
            int responseCode = responseJson.getInt("response-code");

            if (responseCode != 200) {
                throw new RuntimeException("Can't delete user by id:" + authorId);
            }
            connection.close();

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
