import lombok.Data;

@Data
public class Message {
    private int mid;
    private String userid;
    private String touserid;
    private String messagetime;
    private String messagecontent;
    private int isview;
}