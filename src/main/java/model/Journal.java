package model;

import lombok.Data;


public class Journal {

    private final String name;

    public Journal(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
