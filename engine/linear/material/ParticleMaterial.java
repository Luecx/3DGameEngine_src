package engine.linear.material;

/**
 * Created by Luecx on 15.01.2017.
 */
public class ParticleMaterial extends Material{
    private int numberOfRows;

    public ParticleMaterial(int textureID, int numberOfRows) {
        super(textureID);
        this.numberOfRows = numberOfRows;
    }
    public ParticleMaterial(String textureFile, int numberOfRows) {
        super(textureFile);
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }
}
