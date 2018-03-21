package projects.mediavle_game.player.Inventory;

import java.util.ArrayList;

public enum ItemIdentifier {

    NOTHING(0,0), WOOD(1,0), STONE(5,0), GEAR(2,0);

    private int weight, type;
    static ArrayList<ItemIdentifier> itemList = new ArrayList<>();
    static{
        itemList.add(WOOD);
        itemList.add(STONE);
        itemList.add(GEAR);
    }
    public static ItemIdentifier getItem(int index){
        return itemList.get(index);
    }

    ItemIdentifier(int weight, int type) {
        this.weight = weight;
        this.type = type;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
