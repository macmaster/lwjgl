/** Camera.java
 * @author ronny
 * 
 * TODO: Document <br>
 */
package org.ronny.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/** Camera <br>
 * @author ronny
 */
public class Camera {
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch, yaw, roll; // tilt up/down, tilt left/right, rotate cw,ccw.
	
	/** Camera() <br>
	 * 
	 * Constructs a new Camera. <br>
	 */
	public Camera() {
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		final float dt = 0.02f;
		if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)){
			position.z -= dt;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			position.z += dt;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			position.x -= dt;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			position.x += dt;
		}

		// camera roll
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			roll -= 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			roll += 1;
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getRoll() {
		return roll;
	}
	
}
