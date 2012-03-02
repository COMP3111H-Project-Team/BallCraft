package hkust.comp3111h.ballcraft;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public interface Drawable {
	public void draw(GL10 gl);
	public FloatBuffer makeFloatBuffer();
	public ShortBuffer makeShortBuffer();
}
