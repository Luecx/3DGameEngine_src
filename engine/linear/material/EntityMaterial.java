package engine.linear.material;

import org.lwjgl.util.vector.Vector3f;

public class EntityMaterial extends Material {

	private int displacementMap;
	private int specularMap;
	private int normalMap;

	private boolean useDisplacementMap = false;
	private boolean useSpecularMap = false;
	private boolean useNormalMap = false;

	private boolean supportsNormalMapping = false;
	private boolean supportsSpecularMapping = false;
	private boolean supportsDisplacementMapping = false;

	private float reflectivity = 0.3f;
	private float shineDamper = 10;
	private float discardLimit = 0.5f;
	private float displaceFactor = 1;
	private boolean useLighting = true;
	private boolean transparency = false;

	public void setDisplacementMap(int id) {
		this.displacementMap = id;
		if (id > 0) {
			this.supportsDisplacementMapping = true;
		} else {
			this.supportsDisplacementMapping = false;
			this.useDisplacementMap = false;
		}
	}

	public void setSpecularMap(int id) {
		this.specularMap = id;
		if (id > 0) {
			this.supportsSpecularMapping = true;
		} else {
			this.supportsSpecularMapping = false;
			this.useSpecularMap = false;
		}
	}

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

	public boolean renderWithDisplacementMap() {
		return this.supportsDisplacementMapping && this.useDisplacementMap;
	}

	public boolean renderWithSpecularMap() {
		return this.supportsSpecularMapping && this.useSpecularMap;
	}

	public Vector3f getRenderWithVector() {
		return new Vector3f(
				(supportsDisplacementMapping && this.useDisplacementMap && this.displaceFactor != 0) ? 1 : 0,
				(supportsSpecularMapping && this.useSpecularMap) ? 1 : 0,
				(supportsNormalMapping && this.useNormalMap) ? 1 : 0);
	}

	public int getDisplacementMap() {
		return displacementMap;
	}

	public int getSpecularMap() {
		return specularMap;
	}

	public int getNormalMap() {
		return normalMap;
	}

	public boolean useDisplacementMap() {
		return useDisplacementMap;
	}

	public boolean useSpecularMap() {
		return useSpecularMap;
	}

	public boolean useNormalMap() {
		return useNormalMap;
	}

	public boolean supportsNormalMapping() {
		return supportsNormalMapping;
	}

	public boolean supportsSpecularMapping() {
		return supportsSpecularMapping;
	}

	public boolean supportsDisplacementMapping() {
		return supportsDisplacementMapping;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public boolean useLighting() {
		return useLighting;
	}

	public void setUseLighting(boolean useLighting) {
		this.useLighting = useLighting;
	}

	public float getDisplaceFactor() {
		return displaceFactor;
	}

	public void setDisplaceFactor(float displaceFactor) {
		this.displaceFactor = displaceFactor;
	}

	public boolean hasTransparency() {
		return transparency;
	}

	public void setTransparency(boolean transparency) {
		this.transparency = transparency;
	}

	public EntityMaterial(int colorMap) {
		super(colorMap);
	}

	public void setUseDisplacementMap(boolean useDisplacementMap) {
		this.useDisplacementMap = useDisplacementMap;
	}

	public void setUseSpecularMap(boolean useSpecularMap) {
		this.useSpecularMap = useSpecularMap;
	}

	public void setUseNormalMap(boolean useNormalMap) {
		this.useNormalMap = useNormalMap;
	}

	public float getDiscardLimit() {
		return discardLimit;
	}

	public void setDiscardLimit(float discardLimit) {
		this.discardLimit = discardLimit;
	}
}
