package engine.render.tesselationTerrainSystem;

import engine.core.datastructs.DSElement;
import engine.core.master.MasterRenderer;
import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.system.AbstractRenderer;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.material.TerrainMultimapTexturePack;
import engine.linear.terrain.Terrain;
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
    void render(DSElement<Entity> data){

        prepare();
        prepareTerrain(data.getElement());

        shader.loadTransformationMatrix(data.getElement().getAbsoluteTransformationMatrix());

        //System.out.println(data.getElement().getAbsoluteTransformationMatrix());

        GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        GL11.glDrawElements(GL11.GL_TRIANGLES, data.getElement().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        unbindTerrain();

    }

    public void prepareTerrain(Entity t) {
        RawModel model = t.getRawModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
    }

    private void unbindTerrain() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }


}
