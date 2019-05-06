package model;

import lombok.Builder;
import lombok.Data;

public class Edition {

    private final String name;
    private final Integer year;
    private final String city;

    public Edition(
            final String name,
            final Integer year,
            final String city
    )
    {
        this.name = name;
        this.year = year;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public String getCity() {
        return city;
    }
}
