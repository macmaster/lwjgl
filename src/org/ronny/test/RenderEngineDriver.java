package org.ronny.test;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.ronny.entities.Camera;
import org.ronny.entities.Entity;
import org.ronny.models.RawModel;
import org.ronny.models.TexturedModel;
import org.ronny.renderEngine.DisplayManager;
import org.ronny.renderEngine.Loader;
import org.ronny.renderEngine.Renderer;
import org.ronny.shaders.StaticShader;
import org.ronny.textures.ModelTexture;

public class RenderEngineDriver {
	
	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		// vbos for the model vao. it's a cube...
		float[] vertices = {
				// face 1
				-0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
				
				// face 2
				-0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
				
				// face 3
				0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
				
				// face 4
				-0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
				
				// face 5
				-0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
				
				// face 6
				-0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f
		
		};
		
		float[] textures = {
				// face 1
				0, 0, 0, 1, 1, 1, 1, 0,
				
				// face 2
				0, 0, 0, 1, 1, 1, 1, 0,
				
				// face 3
				0, 0, 0, 1, 1, 1, 1, 0,
				
				// face 4
				0, 0, 0, 1, 1, 1, 1, 0,
				
				// face 5
				0, 0, 0, 1, 1, 1, 1, 0,
				
				// face 6
				0, 0, 0, 1, 1, 1, 1, 0
		};
		
		int[] indices = {
				0, 1, 3, // triangle 1
				3, 1, 2, // triangle 2
				4, 5, 7, // triangle 3
				
				7, 5, 6, // triangle 4
				8, 9, 11, // triangle 5
				11, 9, 10, // triangle 6
				
				12, 13, 15, // triangle 7
				15, 13, 14, // triangle 8
				16, 17, 19, // triangle 9
				
				19, 17, 18, // triangle 10
				20, 21, 23, // triangle 11
				23, 21, 22// triangle 12
		};
		
		RawModel model = loader.loadToVAO(vertices, textures, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("grass"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -5f), 0, 0, 0, 1);
		
		// camera for view shifting.
		Camera camera = new Camera();
		
		while (!Display.isCloseRequested()) {
			// entity.increasePosition(0, 0, 0);
			entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			// game logic and render
			// update display
			DisplayManager.updateDisplay();
		}
		shader.clean();
		loader.clean();
		DisplayManager.closeDisplay();
	}
}
