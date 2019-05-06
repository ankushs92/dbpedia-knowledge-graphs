package model;

public class PaperInVolume {

    private final String paperName;
    private final String volumeName;

    public PaperInVolume(final String paperName, final String volumeName) {
        this.paperName = paperName;
        this.volumeName = volumeName;

    }

    public String getPaperName() {
        return paperName;
    }

    public String getVolumeName() {
        return volumeName;
    }
}
