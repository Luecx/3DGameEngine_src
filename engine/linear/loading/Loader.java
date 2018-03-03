/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.linear.loading;

import engine.core.sourceelements.RawModel;
import engine.core.sourceelements.Signature;
import engine.core.sourceelements.VAOIdentifier;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Luecx
 */
public class Loader {

	public static final String DEFAULT_BUILD_PATH = new File("").getAbsolutePath();

	public static List<Integer> vaos = new ArrayList<Integer>();
	public static List<Integer> vbos = new ArrayList<Integer>();
	public static List<Integer> textures = new ArrayList<Integer>();

	public static void cleanUp() {
		for (int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for (int t : textures) {
			GL11.glDeleteTextures(t);
		}
	}

	public static void cleanVAO(RawModel rawModel) {
		GL30.glDeleteVertexArrays(rawModel.getVaoID());
		vaos.remove(new Integer(rawModel.getVaoID()));
		for(int i:rawModel.getVboIDs()){
			GL15.glDeleteBuffers(i);
			vbos.remove(new Integer(i));
		}
	}

	public static void cleanTexture(int id){
		GL11.glDeleteTextures(id);
		textures.remove(new Integer(id));
	}

	public static RawModel loadToVao(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		int[] h = new int[]{
				storeDataInAttributeList(0, 3, positions),
				storeDataInAttributeList(1, 2, textureCoords),
				storeDataInAttributeList(2, 3, normals)};
		unbindVAO();
		return new RawModel(vaoID, indices.length, VAOIdentifier.D3_MODEL, h);
	}

	public static RawModel loadBonedVao(float[] positions, float[] textureCoords, float[] normals, int[] indices, int[] bone_indices, float[] bone_weights){
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		int[] h = new int[]{
			storeDataInAttributeList(0, 3, positions),
			storeDataInAttributeList(1, 2, textureCoords),
			storeDataInAttributeList(2, 3, normals),
			storeDataInAttributeList(9, 4, bone_indices),
			storeDataInAttributeList(10, 4, bone_weights)};
		unbindVAO();
		return new RawModel(vaoID, indices.length, VAOIdentifier.D3_BONED_MODEL, h);
	}

	public static RawModel loadTerrainToVao(float[] positions, float[] textureCoords, float[] normals, float[] blending, float[] tangents, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		int[] h = new int[]{
				storeDataInAttributeList(0, 3, positions),
				storeDataInAttributeList(1, 2, textureCoords),
				storeDataInAttributeList(2, 3, normals),
				storeDataInAttributeList(3, 3, tangents),
				storeDataInAttributeList(7, 4, blending)};
		unbindVAO();
		return new RawModel(vaoID, indices.length, VAOIdentifier.D3_ADVANCED_TERRAIN_MODEL, h);
	}

	public static RawModel loadTerrainToVao(float[] positions, float[] textureCoords, float[] normals, float[] blending, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		int[] h = new int[]{
				storeDataInAttributeList(0, 3, positions),
				storeDataInAttributeList(1, 2, textureCoords),
				storeDataInAttributeList(2, 3, normals),
				storeDataInAttributeList(7, 4, blending)};
		unbindVAO();
		return new RawModel(vaoID, indices.length, VAOIdentifier.D3_TERRAIN_MODEL, h);
	}

	public static RawModel loadToVao2D(float[] positions, float[] textureCoords){
		int vaoID = createVAO();
		int[] h = new int[]{
				storeDataInAttributeList(0, 2, positions),
				storeDataInAttributeList(1, 2, textureCoords)};
		unbindVAO();
		return new RawModel(vaoID, positions.length, VAOIdentifier.D2_TEXTURED_MODEL, h);
	}

	public static RawModel loadToVao(float[] positions, float[] textureCoords, float[] normals, float[] tangents, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		int[] h = new int[]{
			storeDataInAttributeList(0, 3, positions),
			storeDataInAttributeList(1, 2, textureCoords),
			storeDataInAttributeList(2, 3, normals),
			storeDataInAttributeList(3, 3, tangents)};
		unbindVAO();
		return new RawModel(vaoID, indices.length, VAOIdentifier.D3_NORMAL_MODEL, h);
	}

	public static RawModel loadToVao(OBJModelData modelData) {
		System.out.println(modelData.getIdentifier());
		if(modelData.getIdentifier().validate(VAOIdentifier.D3_MODEL)){
			return loadToVao(modelData.getVertices(), modelData.getTextureCoords(), modelData.getNormals(), modelData.getIndices());
		}else{
			return loadToVao(modelData.getVertices(), modelData.getTextureCoords(), modelData.getNormals(), modelData.getTangents(), modelData.getIndices());
		}
	}

	public static RawModel loadToVao(float[] positions, int dimensions) {
		int vaoID = createVAO();
		int[] h = new int[]{
			storeDataInAttributeList(0, dimensions, positions)};
		unbindVAO();
		return new RawModel(vaoID, positions.length / dimensions, new VAOIdentifier(Signature.EMPTY_SIGNATURE, dimensions,0),h);
	}

	public static int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);

		} catch (Exception ex) {
			System.err.println("cannot load texture!");
		}
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}

	public static int loadTexture(String t1, String t2, String t3, String t4) {
		try{
			BufferedImage b1 = ImageIO.read(new File("res/"+ t1 + ".png"));
			BufferedImage b2 = ImageIO.read(new File("res/"+ t2 + ".png"));
			BufferedImage b3 = ImageIO.read(new File("res/"+ t3 + ".png"));
			BufferedImage b4 = ImageIO.read(new File("res/"+ t4 + ".png"));
			int textureID = loadAndMergeTextures(b1,b2,b3,b4);
			textures.add(textureID);
			return textureID;
		}catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("cannot load texture!");
		}
		return -1;
	}

	private static int loadAndMergeTextures(BufferedImage b1, BufferedImage b2, BufferedImage b3, BufferedImage b4) {
		int[] pixels1 = new int[b1.getWidth() * b1.getHeight()];
		b1.getRGB(0, 0, b1.getWidth(), b1.getHeight(), pixels1, 0, b1.getWidth());
		int[] pixels2 = new int[b2.getWidth() * b2.getHeight()];
		b2.getRGB(0, 0, b2.getWidth(), b2.getHeight(), pixels2, 0, b2.getWidth());
		int[] pixels3 = new int[b3.getWidth() * b3.getHeight()];
		b3.getRGB(0, 0, b3.getWidth(), b3.getHeight(), pixels3, 0, b3.getWidth());
		int[] pixels4 = new int[b4.getWidth() * b4.getHeight()];
		b4.getRGB(0, 0, b4.getWidth(), b4.getHeight(), pixels4, 0, b4.getWidth());

		int[][] pixels = new int[][]{pixels1, pixels2, pixels3, pixels4};

		ByteBuffer buffer = BufferUtils.createByteBuffer(b1.getWidth() * b1.getHeight() * 4 * 4);

		for(int y = 0; y < b1.getHeight() * 2; y++) {
			for(int x = 0; x < b1.getWidth() * 2; x++) {
				int pixel = pixels
						[(y >= b1.getHeight() ? 2:0) + (x >= b1.getWidth() ? 1:0)]
						[(y * b1.getWidth()+x) % (b1.getWidth() * b1.getHeight())];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte) (pixel & 0xFF)); // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component.
			}
		}

		buffer.flip();
		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);


		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR );
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR );
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, b1.getWidth() * 2, b1.getHeight() * 2, 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, buffer);

		return textureID;
	}

	public static int loadTexture(BufferedImage image) {

		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); // 4
																									// for
																									// RGBA,
																									// 3
																									// for
																									// RGB
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte) (pixel & 0xFF)); // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component.
															// Only for RGBA
			}
		}

		buffer.flip();
		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);


		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, buffer);

		textures.add(textureID);
		return textureID;
	}

	public static int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	public static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	public static void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	public static int storeDataInAttributeList(int attributeNumber, int dimensions, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, dimensions, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	public static int storeDataInAttributeList(int attributeNumber, int dimensions, int[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, dimensions, GL11.GL_INT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	public static int createEmptyVbo(int floatCount) {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatCount * 4, GL15.GL_STREAM_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vbo;
	}

	public static void updateVbo(int vbo, float[] data, FloatBuffer buffer) {
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * 4, GL15.GL_STREAM_DRAW);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public static void addInstancedAttribute(int vao, int vbo, int attribute, int dataSize, int instanceDataLength, int offset) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL30.glBindVertexArray(vao);
		GL20.glVertexAttribPointer(attribute, dataSize, GL11.GL_FLOAT, false, 0, offset * 4);
		GL33.glVertexAttribDivisor(attribute, 1);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}

	public static void addDynamicAttribute(int vao, int vbo, int attribute, int dataSize) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL30.glBindVertexArray(vao);
		GL20.glVertexAttribPointer(attribute, dataSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}

	public static void storeDataInIntBuffer(int[] data, IntBuffer buffer) {
		buffer.clear();
		buffer.put(data);
		buffer.flip();
	}

	public static void storeDataInFloatBuffer(float[] data, FloatBuffer buffer) {
		buffer.clear();
		buffer.put(data);
		buffer.flip();
	}

	public static IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public static FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;

	}
}
