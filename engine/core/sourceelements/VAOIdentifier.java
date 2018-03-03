package engine.core.sourceelements;

import engine.core.toolbox.ToolboxA;

import java.util.Arrays;

public class VAOIdentifier {
	
	

    public static final VAOIdentifier D3_MODEL = new VAOIdentifier(Signature.EMPTY_SIGNATURE,3, 0,1,2);
    public static final VAOIdentifier D2_TEXTURED_MODEL = new VAOIdentifier(Signature.EMPTY_SIGNATURE,2, 0,1);
    public static final VAOIdentifier D2_MODEL = new VAOIdentifier(Signature.EMPTY_SIGNATURE,2, 0);
    public static final VAOIdentifier D3_NORMAL_MODEL = new VAOIdentifier(Signature.EMPTY_SIGNATURE,3, 0,1,2,3);
    public static final VAOIdentifier D3_BONED_MODEL = new VAOIdentifier(Signature.EMPTY_SIGNATURE,3, 0,1,2,9,10);
    public static final VAOIdentifier D3_ADVANCED_TERRAIN_MODEL = new VAOIdentifier(Signature.EMPTY_SIGNATURE,3, 0,1,2,3,7);
    public static final VAOIdentifier D3_TERRAIN_MODEL = new VAOIdentifier(Signature.EMPTY_SIGNATURE,3, 0,1,2,7);

    private final Signature signature;
    private final int dimensions;
    private final int[] activeElements;

    public VAOIdentifier(VAOIdentifier v){
        this.dimensions = v.dimensions;
        this.signature = v.signature;
        activeElements = new int[v.activeElements.length];
        for(int i = 0; i < v.activeElements.length; i++){
            this.activeElements[i] = v.activeElements[i];
        }
    }

    @Override
    public String toString() {
        return "VAOIdentifier{" +
                "signature=" + signature +
                ", dimensions=" + dimensions +
                ", activeElements=" + Arrays.toString(activeElements) +
                '}';
    }

    public VAOIdentifier(Signature signature, int dimensioons, int... activeElements){
        this.dimensions = dimensioons;
        this.signature = signature;
        this.activeElements = activeElements;
    }

    public Signature getSignature() {
        return signature;
    }

    public int[] getActiveElements() {
        return activeElements;
    }

    public int getDimensions() {
        return dimensions;
    }

    public boolean validate(VAOIdentifier v) {
        if(this.dimensions != v.dimensions) return false;
        if(this.signature.equals(v.signature) == false) return false;
        for(int i:this.activeElements){
            if(ToolboxA.contains(v.activeElements, i) == false) {
                return false;
            }
        }return true;
    }

    public VAOIdentifier clone() {
        return new VAOIdentifier(this);
    }
}
