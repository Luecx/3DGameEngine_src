package engine.render.advancedTerrainSystem;

import engine.core.master.MasterRenderer;
import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.system.AbstractRenderer;
import engine.linear.advancedterrain.Chunk;
import engine.linear.advancedterrain.Terrain;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by Luecx on 12.01.2017.
 */
public class AdvancedTerrainRenderer extends AbstractRenderer<AdvancedTerrainShader>{


    AdvancedTerrainRenderer(AdvancedTerrainShader shader){
        super(shader);
    }

    private void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        MasterRenderer.disableCulling();
    }

    void render(Terrain terrain){

        if(terrain.isOutdated()){
            bindTextures(terrain);
            terrain.setOutdated(false);
        }

        shader.loadFog(RenderSettings.terrain_fog_gradient,
                RenderSettings.terrain_fog ? RenderSettings.terrain_fog_density: 0,
                RenderSettings.terrain_fog_color_red,
                RenderSettings.terrain_fog_color_green,
                RenderSettings.terrain_fog_color_blue);

        prepare();
        for(Chunk chunk:terrain.getChunks()){
            prepareChunk(chunk);
            GL11.glDrawElements(GL11.GL_TRIANGLES, chunk.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbindChunk();
        }

    }

    public void prepareChunk(Chunk t) {
        RawModel model = t.getRawModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);
        GL20.glEnableVertexAttribArray(7);
    }


    private void unbindChunk() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
        GL20.glDisableVertexAttribArray(7);
        GL30.glBindVertexArray(0);
    }


    private void bindTextures(Terrain tex){

        GL13.glActiveTexture(GL13.GL_TEXTURE10);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTerrainMaterial().getRedMaterial().getColorMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE11);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTerrainMaterial().getRedMaterial().getNormalMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE12);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTerrainMaterial().getGreenMaterial().getColorMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE13);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTerrainMaterial().getGreenMaterial().getNormalMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE14);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTerrainMaterial().getBlueMaterial().getColorMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE15);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTerrainMaterial().getBlueMaterial().getNormalMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE16);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTerrainMaterial().getBlackMaterial().getColorMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE17);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTerrainMaterial().getBlackMaterial().getNormalMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE18);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTerrainMaterial().getColorMap());

        shader.loadMaterials(tex.getTerrainMaterial());
    }
}
