public class Choix {
    public String description;
    public String [] requirements;
    public Stage destination;

    Choix(String description, String [] requirements, Stage destination){
        this.description = description;
        this.requirements = requirements;
        this.destination = destination;
    }
    Choix(){}

    public void performeChoice (Hero hero) {

    }
}
