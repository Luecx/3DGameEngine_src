package projects.labyrinth;

import engine.core.exceptions.CoreException;
import engine.core.master.RenderCore;
import engine.core.system.Sys;
import engine.linear.entities.Entity;

public class Game extends RenderCore {

    private Labyrinth labyrinth;

    public Game(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @Override
    protected void onEnable() {
        Sys.enableAll();

        labyrinth.generate();
        for(Entity e:labyrinth.getModel()){
            try {
                Sys.ENTITY_SYSTEM.addElement(e);
            } catch (CoreException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void render() {

    }


    public static void main(String[] args){
        Game g = new Game();
    }
}
