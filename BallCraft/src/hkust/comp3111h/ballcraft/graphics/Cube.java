package hkust.comp3111h.ballcraft.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube implements Drawable {
	
	private float size = 15;
	private float [] vertices = {
			-size, -size, -size, // 0
			size, -size, -size, // 1
			size,  size, -size, // 2
			-size,  size, -size, // 3
			-size, -size,  size, // 4
			size, -size,  size, // 5
			size,  size,  size, // 6
			-size,  size,  size, // 7
	};
	
	private short [] indices = { 
			0, 4, 5,
            0, 5, 1,
            1, 5, 6,
            1, 6, 2,
            2, 6, 7,
            2, 7, 3,
            3, 7, 4,
            3, 4, 0,
            4, 7, 6,
            4, 6, 5,
            3, 0, 1,
            3, 1, 2,
	};
	
	private FloatBuffer fBuffer = null;
	private ShortBuffer sBuffer = null;
	
	public Cube() {
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

