package com.stormnet.net.client.maintenance.impl;

import com.stormnet.net.client.exceptions.ServiceException;
import com.stormnet.net.client.maintenance.BookService;
import com.stormnet.net.client.services.SocketConnection;
import com.stormnet.net.data.books.Book;
import com.stormnet.net.utils.numbers.NumbersUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {

    @Override
    public List<Book> getAllBooks() {
        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("read-all-books-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
            jsonWriter.endObject();
            jsonWriter.endObject();


            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            List<Book> allBooks = new ArrayList<>();

            int responseCode = responseJson.getInt("response-code");

            if (responseCode == 200) {
                JSONArray allBooksJson = responseJson.getJSONArray("response-data");
                int booksCount = allBooksJson.length();

                for (int i = 0; i < booksCount; i++) {
                    JSONObject bookJson = allBooksJson.getJSONObject(i);

                    Long id = bookJson.getLong("id");
                    String name = bookJson.getString("name");
                    String author = bookJson.getString("author");
                    String genre = bookJson.getString("genre");
                    String description = bookJson.getString("description");

                    Book book = new Book(name, author, genre, description);
                    book.setId(id);

                    allBooks.add(book);
                }
            }
            connection.close();

            return allBooks;

        } catch (Exception e) {
           throw new ServiceException(e);
        }

    }

    @Override
    public Book readBookById(Long bookId) {
        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("read-book-by-id-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
            jsonWriter.key("bookId").value(bookId);
            jsonWriter.endObject();
            jsonWriter.endObject();


            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            List<Book> allBooks = new ArrayList<>();

            int responseCode = responseJson.getInt("response-code");

            Book book = null;
            if (responseCode == 200) {
                JSONObject bookJson = responseJson.getJSONObject("response-data");

                Long id = bookJson.getLong("id");
                String name = bookJson.getString("name");
                String author = bookJson.getString("author");
                String genre = bookJson.getString("genre");
                String description = bookJson.getString("description");

                book = new Book(name, author, genre, description);
                book.setId(id);

                allBooks.add(book);

            }
            connection.close();

            return book;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long saveBook(Book book) {
        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("save-book-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
                jsonWriter.key("bookId").value(NumbersUtils.toString(book.getId()));
                jsonWriter.key("name").value(book.getName());
                jsonWriter.key("author").value(book.getAuthor());
                jsonWriter.key("genre").value(book.getGenre());
                jsonWriter.key("description").value(book.getDescription());
            jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            int responseCode = responseJson.getInt("response-code");

            if (responseCode == 200) {
                JSONObject responseData = responseJson.getJSONObject("response-data");
                long bookId = responseData.getLong("bookId");

                return bookId;
            }

            connection.close();

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        throw new RuntimeException("Can't Save Book!");
    }

    @Override
    public void delBook(Long bookId) {
        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("delete-book-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
            jsonWriter.key("bookId").value(bookId.toString());
            jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();
            int responseCode = responseJson.getInt("response-code");

            if (responseCode != 200) {
                throw new RuntimeException("Can't delete user by id:" + bookId);
            }
            connection.close();

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
