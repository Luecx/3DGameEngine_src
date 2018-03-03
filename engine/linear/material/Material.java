package engine.linear.material;


import engine.linear.loading.Loader;

public abstract class Material {
	
	protected int colorMap;
	
	public Material(int colorMap) {
		super();
		this.colorMap = colorMap;
	}

	public Material(String colorMap){
		this.colorMap = Loader.loadTexture(colorMap);
	}
	
	public int getColorMap() {
		return colorMap;
	}

	public void setColorMap(int colorMap) {
		this.colorMap = colorMap;
	}
	
	
	
}
