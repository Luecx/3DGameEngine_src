package projects.mediavle_game.map;

import engine.core.sourceelements.RawModel;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;

/**
 * Created by finne on 20.03.2018.
 */
public class GroundMap extends UniqueGameEntity {

    Field[][] fields;

    public GroundMap(int fields_x, int fields_y) {
        super(0,0,fields_x,fields_y);
        fields = new Field[fields_x][fields_y];

        for(int i = 0; i < fields_x; i++) {
            for(int n = 0;n < fields_y; n++) {
                fields[i][n] = new Field();
            }
        }
        initModels();
        initGame();
    }

    private void initModels() {
        this.generateTexturedModel();
        this.generateEntity(0,0);
        this.entity.setScale(this.width,1, this.getHeight());


        /**
         * Alle GameEntities müssen hier ihre TexturedModels erstellen:
         * new UniqueGameEntity(0,0,0,0).generateTexturedModel();
         *
         **/
    }

    @Override
    public void generateTexturedModel() {
        RawModel model = OBJLoader.loadOBJ("models/env", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("textures/colormaps/colorized"));
        texturedModel = new TexturedModel(model, material);
        texturedModel.setTextureStretch(this.fields.length);
    }

    public void initGame(){
        /* TODO */
    }




}
