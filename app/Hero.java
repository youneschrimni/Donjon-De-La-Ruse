public class Hero {
    public String name;
    public int level;
    public int PV;

    Hero() {}
    Hero (String name, int level, int PV) {
        this.name = name;
        this.level = level;
        this.PV = PV;
    }

    public void moveToStage(Stage stageID){}
    public void collectObject(Objets objet){}
    public Stage battle (Enemie opponent){
        Stage stage = null;
        return stage;
    }
}
