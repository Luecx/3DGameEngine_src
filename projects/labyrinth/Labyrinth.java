package projects.labyrinth;

import engine.linear.entities.Entity;

import java.util.ArrayList;

public abstract class Labyrinth {

    public abstract void generate();

    public abstract boolean collides(float x,float y);

    public abstract ArrayList<Entity> getModel();

}
