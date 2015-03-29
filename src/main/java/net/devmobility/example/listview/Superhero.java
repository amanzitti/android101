package net.devmobility.example.listview;

public class Superhero {
    private String name;
    private String ability;
    private int costumeColor;

    public Superhero(String name, String ability, int costumeColor) {
        setName(name);
        setAbility(ability);
        setCostume(costumeColor);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbility() {
           return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public int getCostumeColor(){
        return costumeColor;
    }

    public void setCostume(int costumeColor) {
        this.costumeColor = costumeColor;
    }
}
