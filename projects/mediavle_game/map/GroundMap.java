package projects.mediavle_game.map;

import engine.core.exceptions.CoreException;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import projects.mediavle_game.map.entities.abs.GameEntity;
import projects.mediavle_game.map.entities.abs.UniqueGameEntity;
import projects.mediavle_game.map.entities.obs.Ground;
import projects.mediavle_game.map.entities.obs.Tree;

import java.util.ArrayList;

/**
 * Created by finne on 20.03.2018.
 */
public class GroundMap extends Ground {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    Field[][] fields;

    private ArrayList<GameEntity> gameEntities = new ArrayList<>();

    public GroundMap() {
        super(0, 0, WIDTH, HEIGHT);
        fields = new Field[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i++) {
            for (int n = 0; n < HEIGHT; n++) {
                fields[i][n] = new Field();
            }
        }
        initGame();
    }



    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public boolean isRigidBody(float x, float z) {
        if (x > 0 && z > 0 && x < this.width && z < this.height && fields[(int) x][(int) z].getGameEntity() != null) {
            if (x > 0 && z > 0 && x < this.width && z < this.height) {
                if (fields[(int) x][(int) z].getGameEntity().isRigidBody()) return true;
            }
        }
        return false;
    }

    public boolean couldPlace(int x, int y, int w, int h){
        for(int i = x; i < x + w; i++) {
            for(int n = y; n < y + h; n++) {
                if(fields[i][n].getGameEntity() != null){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean couldPlace(GameEntity gameEntity) {
        return couldPlace(gameEntity.getX(),gameEntity.getY(),gameEntity.getWidth(),gameEntity.getHeight());
    }

    public boolean couldPlace(GameEntity gameEntity, int x, int y) {
        return couldPlace(x,y,gameEntity.getWidth(),gameEntity.getHeight());
    }

    public void place(GameEntity entity) {
        if(couldPlace(entity.getX(),entity.getY(), entity.getWidth(), entity.getHeight())){
            for(int i = entity.getX(); i < entity.getX() + entity.getWidth(); i++) {
                for(int n = entity.getY(); n < entity.getY() + entity.getHeight(); n++) {
                    fields[i][n].setUniqueGameEntity(entity);
                }
            }
            entity.generateEntity();
            gameEntities.add(entity);
        }
    }

    public void removeGameEntity(int x, int y) {
        if(fields[x][y].getGameEntity() != null) {
            GameEntity entity = fields[x][y].getGameEntity();
            for(int i = entity.getX(); i < entity.getX() + entity.getWidth(); i++) {
                for(int n = entity.getY(); n < entity.getY() + entity.getHeight(); n++) {
                    fields[i][n].setUniqueGameEntity(null);
                }
            }
            entity.destroyEntity();
            this.gameEntities.remove(entity);
        }
    }

    public void initGame() {
        for(int i = 0; i < 25000; i++) {
            int x = (int) (Math.random() * (WIDTH - 5) + 2);
            int y = (int) (Math.random() * (HEIGHT - 5) + 2);
            Tree tree = new Tree(x,y);
            this.place(tree);
        }
    }

}
