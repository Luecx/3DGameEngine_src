package engine.linear.gui.font;

import java.io.*;

/**
 * Created by Luecx on 17.03.2017.
 */
public class Font {

    private static BufferedReader BUFFERED_READER;

    private String fontName = "";
    private CharData[] charData = new CharData[256];

    private float lineOffset = 0.2f;

    public static Font loadFromFile(String file){

        Font f = new Font();

        try {
            BUFFERED_READER = new BufferedReader(new InputStreamReader(new FileInputStream(new File("res/"+file+".fnt"))));
            String s;
            float w = 512,h = 512;
            while((s = BUFFERED_READER.readLine()) != null){
                if(s.startsWith("info")) {
                    int a = 0;
                    f.fontName = "";
                    for (char c : s.toCharArray()) {
                        if (c == '"') {
                            a++;
                            if (a == 2) {
                                break;
                            }
                            continue;
                        }
                        if (a == 1) f.fontName += c;
                    }
                }else if(s.startsWith("common")){
                    w = Float.parseFloat(s.substring(s.indexOf("scaleW=") + 7, s.indexOf("scaleW=") + 10).trim());
                    h = Float.parseFloat(s.substring(s.indexOf("scaleH=") + 7, s.indexOf("scaleH=") + 10).trim());
                }else if(s.startsWith("char ")){
                    CharData d = new CharData();
                    d.loadFromString(s, w, h);
                    f.charData[d.getDigit()] = d;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(); return null;
        } catch (IOException e) {
            e.printStackTrace(); return null;
        }
        return f;
    }

    public CharData getChar(char c){
        if(charData[c] == null) return  charData[68];
        return charData[c];
    }

    public CharData[] getCharData() {
        return charData;
    }

    public float getLineOffset() {
        return lineOffset;
    }

    public void setLineOffset(float lineOffset) {
        this.lineOffset = lineOffset;
    }

    @Override
    public String toString() {
       String s = "Font [" + fontName + "]";
       for(CharData d:charData){
           s += "\n   " +d.toString();
       }
       return s;
    }
}
