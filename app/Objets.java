public class Objets {
    public String id;
    public String description;
    public String icon;

    public Objets (){}
    public Objets (String id, String description, String icon) {
        this.id = id;
        this.description = description;
        this.icon = icon;
    }
    public Objets (String description, String icon) {
        this.description = description;
        this.icon = icon;
    }

}
