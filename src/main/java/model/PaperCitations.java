package model;

public class PaperCitations {

    private final String paperName;
    private final String citedPaperName;

    public PaperCitations(final String paperName, final String citedPaperName) {
        this.paperName = paperName;
        this.citedPaperName = citedPaperName;
    }

    public String getPaperName() {
        return paperName;
    }

    public String getCitedPaperName() {
        return citedPaperName;
    }
}
