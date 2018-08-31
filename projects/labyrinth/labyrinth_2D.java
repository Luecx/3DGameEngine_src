package projects.labyrinth;

import engine.core.exceptions.CoreException;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;

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
        ArrayList<Entity> arrayList = new ArrayList<>();

        RawModel rawModel = OBJLoader.loadOBJ("models/cube", false);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("textures/colormaps/redpng"));
        TexturedModel model = new TexturedModel(rawModel, material);
        model.setTextureStretch(1);

        for(int i = 0; i < size; i++){
            for(int n = 0; n<  size; n++){
                if(wall[i][n]){
                    Entity e = new Entity(model);
                    e.setScale(0.5f,2,0.5f);
                    e.setPosition(i,0,n);
                    arrayList.add(e);
                }
            }
        }

        return arrayList;
    }
}
