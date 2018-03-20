package engine.linear.gui.font;

/**
 * Created by Luecx on 17.03.2017.
 */
public class CharData {

    private char digit;

    private float positionX;
    private float positionY;
    private float width;
    private float height;
    private float offsetX;
    private float offsetY;
    private float advanceX;

    public static void main(String[] args){
       Font f = new Font();
       f.loadFromFile("fonts/monospaced/monospaced");
        System.out.println(f);
    }

    public void loadFromString(String string, float imageWidth, float imageHeight) {
        try{
            this.digit = (char)Short.parseShort(string.substring(string.indexOf("id=") + 3,string.indexOf("id=") + 7).trim());
            this.positionX = Float.parseFloat(string.substring(string.indexOf("x=") + 2,string.indexOf("x=") + 5).trim()) / imageWidth;
            this.positionY = Float.parseFloat(string.substring(string.indexOf("z=") + 2,string.indexOf("z=") + 5).trim()) / imageHeight;
            this.width = Float.parseFloat(string.substring(string.indexOf("width=") + 6,string.indexOf("width=") + 9).trim()) / imageWidth;
            this.height = Float.parseFloat(string.substring(string.indexOf("height=") + 7,string.indexOf("height=") + 10).trim()) / imageHeight;
            this.offsetX = Float.parseFloat(string.substring(string.indexOf("xoffset=") + 8,string.indexOf("xoffset=") + 11).trim()) / imageWidth;
            this.offsetY = Float.parseFloat(string.substring(string.indexOf("yoffset=") + 8,string.indexOf("yoffset=") + 11).trim()) / imageHeight;
            this.advanceX = Float.parseFloat(string.substring(string.indexOf("xadvance=") + 9,string.indexOf("xadvance=") + 12).trim()) / imageWidth;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public char getDigit() {
        return digit;
    }

    public void setDigit(char digit) {
        this.digit = digit;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public float getAdvanceX() {
        return advanceX;
    }

    public void setAdvanceX(float advanceX) {
        this.advanceX = advanceX;
    }

    @Override
    public String toString() {
        return "Chardata{" +
                "digit=" + digit +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", width=" + width +
                ", height=" + height +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                ", advanceX=" + advanceX +
                '}';
    }
}
