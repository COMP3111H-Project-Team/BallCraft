package hkust.comp3111h.ballcraft;

import java.util.Random;

/**
 * This class is an super class for particles.
 * The particles are used to simulate the visual effects like explosion, glow and so on
 * 
 * @author guanlun
 */
class Particle {
	public float x;
	public float y;
	public float z;
	
	public float xSpeed, ySpeed, zSpeed;
	
	public Particle(float x, float y, float z, Random gen) {
		this.x = gen.nextFloat();
		this.y = gen.nextFloat();
		this.z = gen.nextFloat();
		
		this.xSpeed = gen.nextFloat() - 0.5f;
		this.ySpeed = gen.nextFloat() - 0.5f;
		this.zSpeed = gen.nextFloat();
	}
	
	public void move(long elapse) {
		zSpeed -= 0.001f;
		this.x += xSpeed;
		this.y += ySpeed;
		this.z += zSpeed;
	}
}