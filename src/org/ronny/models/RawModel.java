package org.ronny.models;

/** RawModel
 * @author ronny <br>
 *
 * TODO: Document <br>
 */
public class RawModel {
	
	private int vaoId;
	private int vertexCount;
	
	/** RawModel <br>
	 * 
	 * Constructs a new RawModel Object. <br>
	 */
	public RawModel(int vaoId, int vertexCount) {
		super();
		this.vaoId = vaoId;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoId() {
		return vaoId;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
}
