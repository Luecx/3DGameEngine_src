package engine.core.sourceelements;

/**
 * Created by Luecx on 03.01.2017.
 */
public class RawModel {

    private final int vaoID;
    private final int vertexCount;

    private final int[] vboIDs;
    private final VAOIdentifier vaoIdentifier;


    public RawModel(int vaoID, int vertexCount, VAOIdentifier vaoIdentifier, int[] vboIDs) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.vboIDs = vboIDs;
        this.vaoIdentifier = vaoIdentifier;
    }

    public int[] getVboIDs() {
        return vboIDs;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public VAOIdentifier getVaoIdentifier() {
        return vaoIdentifier;
    }
}
