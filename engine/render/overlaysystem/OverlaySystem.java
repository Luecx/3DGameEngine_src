package engine.render.overlaysystem;

import engine.core.datastructs.DSArrayList;
import engine.core.sourceelements.Signature;
import engine.core.sourceelements.VAOIdentifier;
import engine.core.system.RenderSystem;
import engine.linear.material.GuiElement;

/**
 * Created by Luecx on 17.01.2017.
 */
public class OverlaySystem extends RenderSystem<OverlayShader, OverlayRenderer, GuiElement, DSArrayList<GuiElement>>{

    private static final OverlayShader absShader = new OverlayShader();

    public OverlaySystem() {
        super(new OverlayRenderer(absShader), absShader, new VAOIdentifier(Signature.EMPTY_SIGNATURE, 2, 0));
    }

    @Override
    protected void addToCollection(GuiElement element) {
        this.data.add(element);
    }

    @Override
    protected void initData() {
        this.data = new DSArrayList<>();
    }

    @Override
    public void removeElement(GuiElement overlayElement) {
        if(!enabled)
            return;
        this.data.remove(overlayElement);
    }

    public void render(){
        if(!enabled) return;
        shader.start();
        renderer.render(data);
        shader.stop();
    }
}
