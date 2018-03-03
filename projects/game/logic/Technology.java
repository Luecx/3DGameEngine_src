package projects.game.logic;

import java.util.ArrayList;

/**
 * Created by Luecx on 26.01.2017.
 */
public enum Technology {
    SUPER_LIGHT_MATERIALS("Super light materials", "Decreses the weight of constructions and makes them more efficient", new Technology[]{}, new ConstructionPlan[]{});

    private final ArrayList<ConstructionPlan> unlockedConstructionPlans = new ArrayList<>();

    private final ArrayList<Technology> neededTechnologies = new ArrayList<>();
    private final ArrayList<Technology> followingTechnologies = new ArrayList<>();

    private final String name;
    private final String description;

    Technology(String name, String description, Technology[] needed,  ConstructionPlan[] plans) {
        this.name = name;
        this.description = description;
        for(Technology t: needed){
            this.neededTechnologies.add(t);
            t.followingTechnologies.add(this);
        }
    }

    public ArrayList<ConstructionPlan> getUnlockedConstructionPlans() {
        return unlockedConstructionPlans;
    }

    public ArrayList<Technology> getNeededTechnologies() {
        return neededTechnologies;
    }

    public ArrayList<Technology> getFollowingTechnologies() {
        return followingTechnologies;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        String res = "Technology{ \n   Name: "+ name + "\n   Description: "+ description + "\n   Unlocked Plans: ";
        for(ConstructionPlan p:unlockedConstructionPlans){
            res += p.toString() + " | ";
        }res += "\n   Needed Technologies: ";for(Technology p:neededTechnologies){
            res += p.getName() + " | ";
        }res += "\n   Unlocked Technologies: ";for(Technology p:followingTechnologies){
            res += p.getName() + " | ";
        }



        return res + "\n}";
    }
}
