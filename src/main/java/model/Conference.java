package model;

import lombok.Data;

public class Conference {

    private final String name;

    public Conference(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
