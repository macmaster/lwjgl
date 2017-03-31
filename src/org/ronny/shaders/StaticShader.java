package org.ronny.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.ronny.entities.Camera;
import org.ronny.toolbox.GraphicsMath;

/** StaticShader
 * @author ronny <br>
 *
 * TODO: Document <br>
 */
public class StaticShader extends ShaderProgram {
	
	// shader locations.
	private static final String VERTEX_FILE = "/org/ronny/shaders/vertex.txt";
	private static final String FRAGMENT_FILE = "/org/ronny/shaders/fragment.txt";
	
	// projection, view, and transformation matrices
	private int pmatrixId, vmatrixId, tmatrixId;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	/** StaticShader <br>
	 * 
	 * Constructs a new StaticShader Object. <br>
	 * @param vertexFile
	 * @param fragmentFile
	 */
	public StaticShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
	}
	
	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "textures");
	}
	
	@Override
	protected void getAllUniformLocations() {
		pmatrixId = super.getUniformLocation("projectionMatrix");
		vmatrixId = super.getUniformLocation("viewMatrix");
		tmatrixId = super.getUniformLocation("transformationMatrix");
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(pmatrixId, matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f matrix = GraphicsMath.createViewMatrix(camera);
		super.loadMatrix(vmatrixId, matrix);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(tmatrixId, matrix);
	}
	
}
