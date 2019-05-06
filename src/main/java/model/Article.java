package model;

import lombok.Data;

public class Article {

    private final String name;

    public Article(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
