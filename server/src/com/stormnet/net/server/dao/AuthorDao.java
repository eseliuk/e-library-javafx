package com.stormnet.net.server.dao;

import com.stormnet.net.data.author.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> readAllAuthors();

    Author readAuthor(Long id);

    Long saveAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(Long id);
}
