package engine.render.terrainsystem;

import engine.core.master.MasterRenderer;
import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.system.AbstractRenderer;
import engine.linear.entities.TexturedModel;
import engine.linear.terrain.Terrain;
import engine.linear.terrain.TerrainMultimapTexturePack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

/**
 * Created by Luecx on 12.01.2017.
 */
public class TerrainRenderer extends AbstractRenderer<TerrainShader>{


    TerrainRenderer(TerrainShader shader){
        super(shader);
    }

    private void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        MasterRenderer.disableCulling();
    }
    void render(ArrayList<Terrain> data){

        shader.loadFog(RenderSettings.terrain_fog_gradient,
                RenderSettings.terrain_fog ? RenderSettings.terrain_fog_density: 0,
                RenderSettings.terrain_fog_color_red,
                RenderSettings.terrain_fog_color_green,
                RenderSettings.terrain_fog_color_blue);

        prepare();
        for(Terrain terrain:data){
            prepareTerrain(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbindTerrain();
        }
    }

    public void prepareTerrain(Terrain t) {
        RawModel model = t.getRawModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(7);

        TerrainMultimapTexturePack tex = t.getTexturesPack();
        shader.loadShineVariables(tex.getShineDamper(), tex.getReflectivity());
        bindTextures(tex);
    }

    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
    }

    private void unbindTerrain() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(7);
        GL30.glBindVertexArray(0);
    }


    private void bindTextures(TerrainMultimapTexturePack tex){

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getRedMaterial().getColorMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getGreenMaterial().getColorMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getBlueMaterial().getColorMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getBlackMaterial().getColorMap());

        shader.loadTextureStretch(
                tex.getRedMaterial().getTextureStretch(),
                tex.getGreenMaterial().getTextureStretch(),
                tex.getBlueMaterial().getTextureStretch(),
                tex.getBlackMaterial().getTextureStretch());
    }
}
