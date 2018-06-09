package projects.vier_gewinnt_v2.visual;

import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;

public enum Stones {

    STONE_1("models/icosphere", "textures/colormaps/greenPng"),
    STONE_2("models/cube", "textures/colormaps/redpng"),
    STONE_3("models/sphere", "textures/colormaps/bluePng"),
    STONE_4("models/cube", "textures/colormaps/blackpng");


    TexturedModel texturedModel;

    Stones(String obj, String texture) {
        this.texturedModel = new TexturedModel(
                OBJLoader.loadOBJ(obj, true),
                new EntityMaterial(Loader.loadTexture(texture))
        );
    }

    public static Stones getStone(int index) {
        switch (index + 1) {
            case 1:
                return STONE_1;
            case 2:
                return STONE_2;
            case 3:
                return STONE_3;
            case 4:
                return STONE_4;
        }
        return null;
    }

    public Entity generateEntity(float x, float y, float z, float scale) {
        Entity e = new Entity(texturedModel, x, y, z);
        e.setScale(scale, scale, scale);
        return e;
    }
}
