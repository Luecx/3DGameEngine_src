package engine.linear.gui;

import engine.core.sourceelements.RawModel;
import engine.linear.gui.font.CharData;
import engine.linear.gui.font.Font;
import engine.linear.loading.Loader;
import engine.linear.material.GuiElement;
import org.lwjgl.opengl.Display;

/**
 * Created by Luecx on 17.03.2017.
 */
public class FontPanel extends GuiElement{

    private Font font;
    private String content = "Text";
    private String backup = "Text";

    private RawModel rawModel;

    public FontPanel(int colorMap, Font fontMaterial) {
        super(colorMap);
        this.font = fontMaterial;
        this.update();
    }

    public FontPanel(String colorMap, Font fontMaterial) {
        super(colorMap);
        this.font = fontMaterial;
        this.update();
    }

    public FontPanel(int colorMap, int displayMode, Font fontMaterial) {
        super(colorMap, displayMode);
        this.font = fontMaterial;
        this.update();
    }

    public FontPanel(String colorMap, int displayMode, Font fontMaterial) {
        super(colorMap, displayMode);
        this.font = fontMaterial;
        this.update();
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void update() {
        if(this.font == null) return;
        if(content.equals(backup) == false || rawModel == null){
            backup = content;
            reworkVbo();
        }
    }

    private void reworkVbo() {


        if(this.rawModel != null){
            Loader.cleanVAO(rawModel);
        }
        float aspectRatio = (float)Display.getDisplayMode().getWidth() / (float)Display.getDisplayMode().getHeight();
        float totalWidth = 0;
        for(char c:content.toCharArray()){
            totalWidth += font.getChar(c).getAdvanceX();
        }

        float cursor = - totalWidth / 2;
        int index = 0;
        float[] vertices = new float[content.length() * 6 * 2];
        float[] tex = new float[content.length() * 6 * 2];

        for(char c:content.toCharArray()){

            CharData d = font.getChar(c);
            vertices[index * 12 + 0] = cursor - d.getOffsetX();                                     tex[index * 12 + 0] = d.getPositionX();
            vertices[index * 12 + 1] = font.getLineOffset() - d.getOffsetY()  * aspectRatio;                             tex[index * 12 + 1] = d.getPositionY();

            vertices[index * 12 + 2] = vertices[index * 12 + 0];                                    tex[index * 12 + 2] = d.getPositionX();
            vertices[index * 12 + 3] = vertices[index * 12 + 1] - d.getHeight() * aspectRatio;      tex[index * 12 + 3] = d.getPositionY() + d.getHeight();

            vertices[index * 12 + 4] = vertices[index * 12 + 0] + d.getWidth();                     tex[index * 12 + 4] = d.getPositionX() + d.getWidth();
            vertices[index * 12 + 5] = vertices[index * 12 + 1];                                    tex[index * 12 + 5] = d.getPositionY();

            vertices[index * 12 + 6] = vertices[index * 12 + 2];                                    tex[index * 12 + 6] = tex[index * 12 + 2];
            vertices[index * 12 + 7] = vertices[index * 12 + 3];                                    tex[index * 12 + 7] = tex[index * 12 + 3];

            vertices[index * 12 + 8] = vertices[index * 12 + 4];                                    tex[index * 12 + 8] = tex[index * 12 + 4];
            vertices[index * 12 + 9] = vertices[index * 12 + 3];                                    tex[index * 12 + 9] = tex[index * 12 + 3];

            vertices[index * 12 + 10] = vertices[index * 12 + 4];                                   tex[index * 12 + 10] = tex[index * 12 + 4];
            vertices[index * 12 + 11] = vertices[index * 12 + 5];                                   tex[index * 12 + 11] = tex[index * 12 + 5];

            cursor += d.getAdvanceX();
            index++;
        }

        this.rawModel = Loader.loadToVao2D(vertices, tex);
        System.out.println(this.rawModel.getVertexCount());
    }


    @Override
    public RawModel getRawModel() {
        return this.rawModel;
    }
}
