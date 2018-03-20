package projects.mediavle_game.map;

import engine.core.sourceelements.RawModel;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import projects.mediavle_game.map.entities.abs.UniqueGameEntity;

/**
 * Created by finne on 20.03.2018.
 */
public class GroundMap extends UniqueGameEntity {

    Field[][] fields;

    public GroundMap(int fields_x, int fields_y) {
        super(0, 0, fields_x, fields_y);
        fields = new Field[fields_x][fields_y];

        for (int i = 0; i < fields_x; i++) {
            for (int n = 0; n < fields_y; n++) {
                fields[i][n] = new Field();
            }
        }
        initModels();
        initGame();
    }

    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    private void initModels() {
        this.generateTexturedModel();
        this.generateEntity();
        this.entity.setScale(this.width, 1, this.getHeight());
        this.entity.setPosition(0, 0, this.width);


        /**
         * Alle GameEntities müssen hier ihre TexturedModels erstellen:
         * new UniqueGameEntity(0,0,0,0).generateTexturedModel();
         *
         **/
    }

    public boolean rigidBody(float x, float z) {
        if (x > 0 && z > 0 && x < this.width && z < this.height && fields[(int) x][(int) z].getGameEntity() != null) {
            if (x > 0 && z > 0 && x < this.width && z < this.height) {
                if (fields[(int) x][(int) z].getGameEntity().isRigidBody()) return true;
            }
        }
        return false;
    }

    @Override
    public void generateTexturedModel() {
        RawModel model = OBJLoader.loadOBJ("ground", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("raw"));
        texturedModel = new TexturedModel(model, material);
        texturedModel.setTextureStretch(this.fields.length);
    }

    public void initGame() {
        /* TODO */
    }


}
