package hkust.comp3111h.ballcraft.graphics;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Plane implements Drawable {
	
	private float size = 100f;
	private float [] vertices = {
				-size,  size, 0.0f,  // 0, Top Left
				-size, -size, 0.0f,  // 1, Bottom Left
				size, -size, 0.0f,  // 2, Bottom Right
				size,  size, 0.0f,  // 3, Top Right
	};
	
	private short [] indices = { 0, 1, 2, 0, 2, 3 };
	
	private FloatBuffer fBuffer = null;
	private ShortBuffer sBuffer = null;
	
	public Plane() {
		fBuffer = makeFloatBuffer();
		sBuffer = makeShortBuffer();
	}
	
	@Override
	public void draw(GL10 gl) {
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, sBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}
	
	@Override
	public FloatBuffer makeFloatBuffer() {
		ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = bb.asFloatBuffer();
		buffer.put(vertices);
		buffer.position(0);
		return buffer;
	}
	
	@Override
	public ShortBuffer makeShortBuffer() {
		ByteBuffer bb = ByteBuffer.allocateDirect(indices.length * 2);
		bb.order(ByteOrder.nativeOrder());
		ShortBuffer buffer = bb.asShortBuffer();
		buffer.put(indices);
		buffer.position(0);
		return buffer;
	}
}
