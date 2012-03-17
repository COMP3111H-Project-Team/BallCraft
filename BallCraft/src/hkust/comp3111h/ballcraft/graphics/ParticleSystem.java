package hkust.comp3111h.ballcraft.graphics;

import javax.microedition.khronos.opengles.GL10;

public abstract class ParticleSystem implements Drawable {
	public abstract void move();
	public abstract void draw(GL10 gl);
}
