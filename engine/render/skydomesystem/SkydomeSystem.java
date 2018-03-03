package engine.render.skydomesystem;

import engine.core.components.Camera;
import engine.core.datastructs.DSElement;
import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.sourceelements.Signature;
import engine.core.sourceelements.VAOIdentifier;
import engine.core.system.RenderSystem;
import engine.linear.loading.OBJLoader;
import engine.linear.material.SkydomeElement;

/**
 * Created by Luecx on 12.01.2017.
 */
public class SkydomeSystem extends RenderSystem<SkydomeShader, SkydomeRenderer, SkydomeElement, DSElement<SkydomeElement>>{

    private static SkydomeShader absShader = new SkydomeShader();

    public static RawModel skydome_model;
    public static RawModel skysphere_model;


    public SkydomeSystem() {
        super(new SkydomeRenderer(absShader), absShader, new VAOIdentifier(Signature.EMPTY_SIGNATURE, 3, 0,1));
        skydome_model = OBJLoader.loadOBJ("sys/skydome", false);
        skysphere_model = OBJLoader.loadOBJ("sys/skysphere", false);
    }

    public void render(Camera camera) {
        if(!enabled || this.data.getElement() == null)
            return;
        shader.start();
        shader.loadProjectionMatrix(camera.getProjectionMatrix());
        shader.loadViewMatrix(camera.getViewMatrix());
        renderer.render(camera, RenderSettings.skydome_use_skysphere ? skysphere_model : skydome_model, this.data.getElement());
        shader.stop();
    }
    @Override
    protected void initData() {
        this.data = new DSElement<>();
    }
    @Override
    protected void addToCollection(SkydomeElement element) {
        data.setElement(element);
    }
    @Override
    public void removeElement(SkydomeElement e) {
        if(!enabled)
            return;
        data.clear();
    }


}
