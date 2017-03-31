package org.ronny.renderEngine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.ronny.models.RawModel;

public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	public Loader() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * return information about a VAO from the vertex positions.
	 */
	public RawModel loadToVAO(float[] positions, float[] textures, int[] indices) {
		int vaoId = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textures);
		unbindVAO();
		return new RawModel(vaoId, indices.length);
	}
	
	/**
	 * Load a texture from a file. <br>
	 * textures are square pngs found in the rsc/ dir.
	 */
	public int loadTexture(String textureName) {
		try { // create a new texture.
			String resource = String.format("/%s.png", textureName);
			Texture texture = TextureLoader.getTexture("PNG", getClass().getResourceAsStream(resource));
			int textureId = texture.getTextureID();
			textures.add(textureId);
			return textureId;
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // error.
		}
	}
	
	/**
	 * Create a VAO, then return its id.
	 */
	private int createVAO() {
		int vaoId = GL30.glGenVertexArrays();
		vaos.add(vaoId);
		GL30.glBindVertexArray(vaoId);
		return vaoId;
	}
	
	public void clean() {
		for (int vaoId : vaos) {
			GL30.glDeleteVertexArrays(vaoId);
		}
		for (int vboId : vaos) {
			GL15.glDeleteBuffers(vboId);
		}
		for (int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int vboId = GL15.glGenBuffers();
		vbos.add(vboId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	/** need to unbind the VAO */
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * so triangles can share vertices. <br>
	 */
	private void bindIndicesBuffer(int[] indices) {
		int vboId = GL15.glGenBuffers();
		vbos.add(vboId);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
}
