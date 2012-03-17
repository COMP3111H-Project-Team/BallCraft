package hkust.comp3111h.ballcraft.graphics;


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
	
	/*
	public Particle(float x, float y, float z, Random gen) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.xSpeed = gen.nextFloat() - 0.5f;
		this.ySpeed = gen.nextFloat() - 0.5f;
		this.zSpeed = 0f;
		Log.w("xs", "" + xSpeed);
	}
	*/
	
	public Particle(float x, float y, float z, float xs, float ys, float zs) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.xSpeed = xs;
		this.ySpeed = ys;
		this.zSpeed = zs;
	}
	
	public void move(long elapse) {
		this.x += xSpeed;
		this.y += ySpeed;
		this.z += zSpeed;
	}
}