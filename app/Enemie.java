import java.util.HashMap;

public class Enemie {
    public String name;
    public String image;
    public HashMap stats;

    public Enemie (){}
    public Enemie (String name, String image, HashMap stats) {
        this.name = name;
        this.image = image;
        this.stats = stats;
    }
}
