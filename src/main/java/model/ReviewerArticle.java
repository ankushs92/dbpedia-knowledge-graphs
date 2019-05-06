package model;

public class ReviewerArticle {

    private final String authorName;
    private final String paperName;

    public ReviewerArticle(final String authorName, final String paperName) {
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
