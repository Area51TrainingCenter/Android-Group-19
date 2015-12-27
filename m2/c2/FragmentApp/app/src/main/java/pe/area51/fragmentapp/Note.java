package pe.area51.fragmentapp;

public class Note {

    private final long id;
    private final String title;
    private final String content;
    private final long timestamp;

    public Note(final long id, final String title, final String content, final long timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
