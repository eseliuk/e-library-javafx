package com.stormnet.net.client.maintenance;

import com.stormnet.net.data.author.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    Author readAuthorById(Long authorId);

    Long saveAuthor(Author author);

    void delAuthor(Long authorId);
}
