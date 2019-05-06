package model;

public class Volume {

    private final String name;
    private final Integer year;
    private final String city;

    public Volume(
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
