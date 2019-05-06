package model;

import java.time.LocalDate;

public class Review {

    private final String content;
    private final LocalDate date;

    public Review(final String content, final LocalDate date) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date.toString();
    }

}
