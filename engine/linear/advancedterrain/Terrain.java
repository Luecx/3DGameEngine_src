package engine.linear.advancedterrain;

import engine.core.sourceelements.RawModel;
import engine.core.sourceelements.SourceElement;
import engine.core.sourceelements.VAOIdentifier;
import engine.linear.terrain.TerrainMultimapTexturePack;

import java.util.ArrayList;

/**
 * Created by Luecx on 22.03.2017.
 */
public class Terrain implements SourceElement {
    private final RawModel empty = new RawModel(-10,0, VAOIdentifier.D3_ADVANCED_TERRAIN_MODEL, null);

    private TerrainMultimapTexturePack terrainMaterial;

    private ArrayList<Chunk> chunks = new ArrayList<>();

    private boolean outdated = true;

    public Terrain() {

    }

    public TerrainMultimapTexturePack getTerrainMaterial() {
        return terrainMaterial;
    }

    public void setTerrainMaterial(TerrainMultimapTexturePack terrainMaterial) {
        this.terrainMaterial = terrainMaterial;
        this.outdated = true;
    }

    public ArrayList<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(ArrayList<Chunk> chunks) {
        this.chunks = chunks;
    }

    public boolean isOutdated() {
        return outdated;
    }

    public void setOutdated(boolean outdated) {
        this.outdated = outdated;
    }

    public RawModel getRawModel() {
        return empty;
    }
}
