package com.stormnet.net.server.dao;

import com.stormnet.net.data.books.Book;

import java.util.List;

public interface BookDao {

    List<Book> readAllBooks();

    Book readBook(Long id);

    Long saveBook(Book book);

    void updateBook(Book book);

    void deleteBook(Long id);
}
