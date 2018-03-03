package projects.game.logic;

import java.util.HashMap;

/**
 * Created by Luecx on 26.01.2017.
 */
public enum ConstructionPlan {

    X_12_MISSILE("X-12 Missile", "high impact, low cooldown", new Ressource[]{Ressource.CARBON_RESSOURCE, Ressource.PLATIN_RESSOURCE, Ressource.URANIUM_RESSOUCE, Ressource.SILVER_RESSOURCE},
            new Integer[]{12,2,8,1}, Construction.X_12_MISSILE);

    private final String name;
    private final String description;

    private final HashMap<Ressource, Integer> neededRessources;
    private final Construction result;

    ConstructionPlan(String name, String description, Ressource[] ressource, Integer[] amounts, Construction result) {
        this.neededRessources = new HashMap<>();
        for(int i = 0; i < Math.min(ressource.length, amounts.length); i++){
            neededRessources.put(ressource[i], amounts[i]);
        }
        this.result = result;
        this.name = name;
        this.description = description;
    }

    public HashMap<Ressource, Integer> getNeededRessources() {
        return neededRessources;
    }

    public Construction getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        String res = "Technology{ \n   Name: "+ name + "\n   Description: "+ description + "\n   Result '"+result.getName() +"'";
        for(Ressource r:neededRessources.keySet()){
            res += "  "+r.getName() + "["+neededRessources.get(r) + "]";
        }
        return res + "\n}";
    }


    public static void main(String[] args){
        System.out.println(Technology.SUPER_LIGHT_MATERIALS.toString());
        System.out.println(ConstructionPlan.X_12_MISSILE.toString());
    }

}
