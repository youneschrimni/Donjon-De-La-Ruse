public class Stage {
    public String id;
    public String  description;
    public Choix [] choix;
    public Objets [] objetcollece;
    public Affrontation [] affrontations;

    public Stage(String id, String description, Choix [] choix, Affrontation [] affrontations){
        this.id = id;
        this.description = description;
        this.choix = choix;
        this.affrontations = affrontations;
    }

    public Stage(String description, Choix [] choix, Affrontation [] affrontations){
        this.description = description;
        this.choix = choix;
        this.affrontations = affrontations;
    }

    public Stage(){}




    public void entry(Hero hero) {

    }


}
