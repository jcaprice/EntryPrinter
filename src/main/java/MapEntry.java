import java.io.Serializable;

public class MapEntry implements Serializable {

    int id;
    String text;

    public MapEntry (int id, String text) {

        this.id = id;
        this.text = text;
    }

    public String toString() {

        StringBuilder string = new StringBuilder();
        string.append(this.id).append(": ").append(this.text);
        return string.toString();
    }
}