package model;

public class AuthorWrotePaper {

    private final String authorName;
    private final String paperName;

    public AuthorWrotePaper(final String authorName, final String paperName) {
        this.authorName = authorName;
        this.paperName = paperName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getPaperName() {
        return paperName;
    }
}
