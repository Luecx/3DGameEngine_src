package engine.linear.material;


public class TerrainMaterial extends Material{

	private float textureStretch = 1200;

	private int normalMap;

	private boolean useNormalMap = false;
	private boolean supportsNormalMapping = false;

	private float reflectivity = 0.3f;
	private float shineDamper = 10;

	public void setNormalMap(int id) {
		this.normalMap = id;
		if (id > 0) {
			this.supportsNormalMapping = true;
		} else {
			this.supportsNormalMapping = false;
			this.useNormalMap = false;
		}
	}

	public boolean renderWithNormalMap() {
		return this.supportsNormalMapping && this.useNormalMap;
	}

	public int getNormalMap() {
		return normalMap;
	}

	public boolean useNormalMap() {
		return useNormalMap;
	}

	public boolean supportsNormalMapping() {
		return supportsNormalMapping;
	}

	public void setUseNormalMap(boolean useNormalMap) {
		this.useNormalMap = useNormalMap;
	}

	public TerrainMaterial(int colorMap, int normalMap, float reflectivity, float shineDamper) {
		super(colorMap);
		this.normalMap = normalMap;
		this.reflectivity = reflectivity;
		this.shineDamper = shineDamper;
	}

	public TerrainMaterial(String colorMap, int normalMap, float reflectivity, float shineDamper) {
		super(colorMap);
		this.normalMap = normalMap;
		this.reflectivity = reflectivity;
		this.shineDamper = shineDamper;
	}

	public TerrainMaterial(int colorMap, int normalMap) {
		super(colorMap);
		this.normalMap = normalMap;
	}

	public float getTextureStretch() {
		return textureStretch;
	}

	public void setTextureStretch(float textureStretch) {
		this.textureStretch = textureStretch;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}
}
