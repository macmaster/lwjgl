package org.ronny.shaders;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/** ShaderProgram
 * @author ronny <br>
 *
 * TODO: Document <br>
 */
public abstract class ShaderProgram {
	
	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	/** ShaderProgram <br>
	 * 
	 * Constructs a new ShaderProgram Object. <br>
	 */
	public ShaderProgram(String vertexFile, String fragmentFile) {
		// read and create the shaders
		vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		
		// create the shader programs.
		programId = GL20.glCreateProgram();
		GL20.glAttachShader(programId, vertexShaderId);
		GL20.glAttachShader(programId, fragmentShaderId);
		bindAttributes();
		GL20.glLinkProgram(programId);
		GL20.glValidateProgram(programId);
		getAllUniformLocations();
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programId, uniformName);
	}
	
	public void start() {
		GL20.glUseProgram(programId);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void clean() {
		stop(); // clean up and free resources.
		GL20.glDetachShader(programId, vertexShaderId);
		GL20.glDetachShader(programId, fragmentShaderId);
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(fragmentShaderId);
		GL20.glDeleteProgram(programId);
	}
	
	protected void bindAttribute(int attribute, String variableName) {
		GL20.glBindAttribLocation(programId, attribute, variableName);
	}
	
	protected abstract void bindAttributes();
	
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void loadBoolean(int location, boolean value) {
		if (value) { // true
			GL20.glUniform1f(location, 1f);
		} else { // false
			GL20.glUniform1f(location, 0f);
		}
	}
	
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	/**
	 * load a gl shader from a file. <br>
	 * exits upon failure.
	 */
	private static int loadShader(String filename, int type) {
		
		// read in shader source from file.
		StringBuilder shaderSource = new StringBuilder();
		try ( InputStreamReader freader = new InputStreamReader(ShaderProgram.class.getResourceAsStream(filename));
			BufferedReader reader = new BufferedReader(freader); ) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
		} catch (Exception err) {
			err.printStackTrace(); // exit with -1 on failure.
			System.err.println("Could not read shader file: " + filename);
			System.exit(-1);
		}
		
		// compile the shader.
		int shaderId = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderId, shaderSource);
		GL20.glCompileShader(shaderId);
		
		// check if compilation succeeded.
		if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Could not compile the shader. error: " + filename);
			System.err.println(GL20.glGetShaderInfoLog(shaderId, 500));
			System.exit(-1);
		}
		
		return shaderId;
	}
	
}
