package model;

public class VolumeOfJournal {

    private final String volume;
    private final String journalName;

    public VolumeOfJournal(final String volume, final String journalName) {
        this.volume = volume;
        this.journalName = journalName;
    }

    public String getVolume() {
        return volume;
    }

    public String getJournalName() {
        return journalName;
    }
}
