package engine.linear.material;

import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.sourceelements.SourceElement;
import engine.core.system.Sys;

/**
 * Created by Luecx on 18.01.2017.
 */
public class SkydomeElement extends Material implements SourceElement{



    public SkydomeElement(int colorMap) {
        super(colorMap);
    }

    public SkydomeElement(String colorMap) {
        super(colorMap);
    }

    @Override
    public RawModel getRawModel() {
        return RenderSettings.skydome_use_skysphere ? Sys.SKYDOME_SYSTEM.skysphere_model:Sys.SKYDOME_SYSTEM.skydome_model;
    }

}
