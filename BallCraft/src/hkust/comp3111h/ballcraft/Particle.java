package hkust.comp3111h.ballcraft;

import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;

/**
 * This class is an abstract super class for particles.
 * The particles are used to simulate the visual effects like explosion, glow and so on
 * 
 * @author guanlun
 */
public abstract class Particle {
	private float x;
	private float y;
	private float z;
	private RGBColor color;
	
	public Particle(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Particle(SimpleVector vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	SimpleVector getPos() {
		return new SimpleVector(x, y, z);
	}
	
	float getX() {
		return this.x;
	}
	
	float getY() {
		return this.y;
	}
	
	float getZ() {
		return this.z;
	}
	
	RGBColor getColor() {
		return this.color;
	}
	
	void setPos(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	void setPos(SimpleVector vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	void setColor(RGBColor color) {
		this.color = color;
	}
	
	void move(SimpleVector vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
	}
}
