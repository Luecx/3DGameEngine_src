package engine.render.shadowsystem;

import engine.core.datastructs.DSHashMap;
import engine.core.master.DisplayManager;
import engine.core.sourceelements.Signature;
import engine.core.sourceelements.VAOIdentifier;
import engine.core.system.RenderSystem;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class ShadowSystem extends RenderSystem<ShadowShader, ShadowRenderer, Entity, DSHashMap<TexturedModel, List<Entity>>>{

	private static ShadowShader shader = new ShadowShader();
	private static ShadowData shadowData;

	public ShadowSystem() {
		super(new ShadowRenderer(shader), shader, new VAOIdentifier(Signature.EMPTY_SIGNATURE, 3, 0,1));
		shadowData = new ShadowData();
	}

	public static ShadowData getShadowData() {
		return shadowData;
	}

	public static void setShadowData(ShadowData shadowData) {
		ShadowSystem.shadowData = shadowData;
	}

	@Override
	protected void addToCollection(Entity element) {
		TexturedModel entityModel = element.getModel();
		List<Entity> batch = data.get(entityModel);
		if(batch!=null){
			batch.add(element);
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(element);
			data.put(entityModel, newBatch);
		}
	}

	@Override
	protected void initData() {
		this.data = new DSHashMap<>();
	}

	@Override
	public void removeElement(Entity e) {
		if(!enabled)
			return;
		List<Entity> list = data.get(e.getModel());
		list.remove(e);
	}

	public void render(ShadowCamera camera) {
		if(!enabled)
			return;

		camera.update();
		shadowData.getFrameBuffer().bindFrameBuffer();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		DisplayManager.clearBuffer();

		shader.start();
		Matrix4f proView = camera.getProViewMatrix();
		ShadowSystem.shadowData.setShadowMatrix(proView);
		shader.loadProView(proView);
		renderer.render(data);
		shader.stop();

		shadowData.setActive(true);
		shadowData.getFrameBuffer().unbindFrameBuffer();
	}


	

	
}
