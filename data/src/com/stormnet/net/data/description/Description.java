package com.stormnet.net.data.description;

import java.util.Objects;

public class Description {

    private Long id;
    private String fullDescription;

    public Description(String fullDescription) {
        this.id = id;
        this.fullDescription = fullDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return fullDescription.equals(that.fullDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullDescription);
    }
}
