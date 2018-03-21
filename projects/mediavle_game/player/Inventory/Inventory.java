package projects.mediavle_game.player.Inventory;

public class Inventory {
    int[][] inventory;
    int stacksize = 16;

    public Inventory(int slots, int properties) {
        inventory = new int[slots][properties];
    }

    public int checkId(int slot) {
        return inventory[slot][0];
    }
    public int checkCount(int slot) {
        return inventory[slot][1];
    }

    public void removeItem(int slot, int count) {
        inventory[slot][1] -= count;
        if (inventory[slot][1] <= 0)
            inventory[slot][0] = 0;
        inventory[slot][1] = 0;
        inventory[slot][2] = 0;
        inventory[slot][3] = 0;
    }

    public void giveItem(int slot, int count, int id) {
        if (inventory[slot][0] == id && inventory[slot][1] + count <= stacksize)
            inventory[slot][1] += count;
        if (inventory[slot][0] == 0 && inventory[slot][1] + count <= stacksize) {
            inventory[slot][0] = id;
            inventory[slot][1] = count;
            inventory[slot][2] = ItemIdentifier.getItem(id).getWeight();
            inventory[slot][2] = ItemIdentifier.getItem(id).getType();
        }
    }

}
