public class Affrontation {
    public String description;
    public Enemie enemie;
    public ResultatAffrontation results;

    public Affrontation (){}
    public  Affrontation (String description, Enemie enemie, ResultatAffrontation results){

    }
    public Stage engage (Hero hero) {
        return new Stage();
    }
}
