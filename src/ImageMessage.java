import java.sql.Timestamp;
import java.awt.*;

public class ImageMessage implements Message {
    private String author;
    private Timestamp timestamp;
    private Image img;

    public ImageMessage(String author ,Image img){
        this.img = img;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.author = author;

    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }

    public Timestamp getTimestamp(){
        return timestamp;
    }

    public Image getContent(){
        return img;
    }
    public void setContent(Image img){
        this.img = img;
    }
}
