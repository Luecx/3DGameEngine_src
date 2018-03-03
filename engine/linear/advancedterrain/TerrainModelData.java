package engine.linear.advancedterrain;

import engine.core.sourceelements.RawModel;
import engine.linear.loading.Loader;

public class TerrainModelData {
	private float[] vertices;
	private float[] textureCoords;
	private float[] normals;
	private float[] blending;
	private float[] tangents;
	private int[] indices;

	public TerrainModelData(float[] vertices, float[] textureCoords, float[] normals, float[] blending, float[] tangents, int[] indices) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.blending = blending;
		this.tangents = tangents;
		this.indices = indices;
	}

	public TerrainModelData(float[] vertices, float[] textureCoords, float[] normals, float[] tangents, int[] indices) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.tangents = tangents;
		this.indices = indices;
	}

	public TerrainModelData() {
		super();
	}

	public float[] getVertices() {
		return vertices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public float[] getNormals() {
		return normals;
	}

	public float[] getBlending() {
		return blending;
	}

	public int[] getIndices() {
		return indices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}

	public void setTextureCoords(float[] textureCoords) {
		this.textureCoords = textureCoords;
	}

	public void setNormals(float[] normals) {
		this.normals = normals;
	}

	public void setBlending(float[] blending) {
		this.blending = blending;
	}

	public void setIndices(int[] indices) {
		this.indices = indices;
	}

	public float[] getTangents() {
		return tangents;
	}

	public void setTangents(float[] tangents) {
		this.tangents = tangents;
	}

	public RawModel createRawModel() {
		if (vertices == null || textureCoords == null || normals == null || blending == null || indices == null || tangents == null) {
			return null;
		}
		try {
			return Loader.loadTerrainToVao(vertices, textureCoords, normals, blending, tangents, indices);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}
}
