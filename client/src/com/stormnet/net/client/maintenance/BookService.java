package com.stormnet.net.client.maintenance;

import com.stormnet.net.data.books.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book readBookById(Long bookId);

    Long saveBook(Book book);

    void delBook(Long bookId);
}
