package pe.area51.listadapter;

public class Note {

    private final String title;
    private final String content;
    private final long creationTimestamp;

    public Note(String title, String content, long creationTimestamp) {
        this.title = title;
        this.content = content;
        this.creationTimestamp = creationTimestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

}
