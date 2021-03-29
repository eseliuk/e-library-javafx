package com.stormnet.net.data.author;

import java.util.Objects;

public class Author {

    private Long id;
    private String fullName;
    private String profile;

    public Author(String fullName, String profile) {
        this.fullName = fullName;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return fullName.equals(author.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }
}
