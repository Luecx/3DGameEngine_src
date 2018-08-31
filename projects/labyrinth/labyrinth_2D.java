package projects.labyrinth;

import engine.linear.entities.Entity;

import java.util.ArrayList;

public class labyrinth_2D extends Labyrinth {

    private int size;
    private boolean[][] wall;

    public labyrinth_2D(int a) {
        this.size = a;
        this.wall = new boolean[a][a];
    }

    public void generate() {
        this.wall[3][3] = true;

    }


    public boolean collides(float x, float y) {
        return false;
    }


    public ArrayList<Entity> getModel() {
        return null;
    }
}
