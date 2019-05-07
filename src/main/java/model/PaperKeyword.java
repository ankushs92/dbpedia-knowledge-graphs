package model;

public class PaperKeyword {

    private final String keyword;
    private final String paperName;

    public PaperKeyword(final String keyword, final String paperName) {
        this.keyword = keyword;
        this.paperName = paperName;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getPaperName() {
        return paperName;
    }

}
