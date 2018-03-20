package engine.render.instancedsystem;

import engine.core.sourceelements.RawModel;
import engine.core.sourceelements.SourceElement;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class InstanceSet implements SourceElement{
	

	private int vboID;
	private Vector3f randomRotation = new Vector3f();
	private TexturedModel model;
	
	private static final int MAX_INSTANCES = 400000;
	private static final int INSTANCE_DATA_LENGTH = 3; 
	
	private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_INSTANCES * INSTANCE_DATA_LENGTH);
	private ArrayList<float[]> positions = new ArrayList<>();
	private boolean outdated = true;

	public InstanceSet(TexturedModel model) {
		super();
		this.model = model;
		vboID = Loader.createEmptyVbo(InstanceSet.MAX_INSTANCES * InstanceSet.INSTANCE_DATA_LENGTH);
		Loader.addInstancedAttribute(model.getRawModel().getVaoID(), vboID, 4, 3, INSTANCE_DATA_LENGTH,0);
	}

	public synchronized TexturedModel getModel() {
		return model;
	}

	public synchronized int size() {
		return positions.size();
	}
	
	public synchronized void addInstance(float x, float y, float z) {
		outdated = true;
		positions.add(new float[]{x,y,z});
	}

	public synchronized float[] getInstance(int index){
		outdated = true;
		return this.positions.get(index);
	}

	public synchronized void removeInstance(float x, float y, float z){
		for(int i = 0; i < positions.size(); i++){
			if(positions.get(i)[0] == x && positions.get(i)[1] == y && positions.get(i)[2] == z){
				positions.remove(i);
			}
		}outdated = true;
	}

	public synchronized void removeInstance(int index) {
		if(index >= 0 && index < positions.size()) {
			positions.remove(index);
			outdated = true;
		}
	}

	public synchronized ArrayList<float[]> getPositions() {
		outdated = true;
		return positions;
	}

	public synchronized void setPositions(ArrayList<float[]> positions) {
		outdated = true;
		this.positions = positions;
	}

	public synchronized void updateVbo() {
		if(!outdated) return;
		outdated = false;
		float[] vboData = new float[positions.size() * INSTANCE_DATA_LENGTH];
		for(int i = 0; i < positions.size();i ++){
			for(int n = 0; n < INSTANCE_DATA_LENGTH; n++){
				vboData[i * INSTANCE_DATA_LENGTH +n] = positions.get(i)[n];
			}
		}
		Loader.updateVbo(vboID, vboData, buffer);
	}

	public void setRandomRotationX(boolean x){
		this.randomRotation.x = x ? 1:0;
	}

	public void setRandomRotationY(boolean y){
		this.randomRotation.y = y ? 1:0;
	}

	public void setRandomRotationZ(boolean z){
		this.randomRotation.z = z ? 1:0;
	}

	public void setRandomRotation(boolean x, boolean y, boolean z){
		this.randomRotation.x = x ? 1:0;
		this.randomRotation.y = y ? 1:0;
		this.randomRotation.z = z ? 1:0;
	}

	public Vector3f getRandomRotation() {
		return randomRotation;
	}

	@Override
	public RawModel getRawModel() {
		return this.model.getRawModel();
	}
}
