package hkust.comp3111h.ballcraft.ui;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import hkust.comp3111h.ballcraft.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Tet {
	
	private float size = 80f;
	
	private float [] vertices = {
			size, size, size, // 0
			-size, -size, size, // 1
			-size, size, -size,  // 2
			size, -size, -size,  // 3
			size, size, size, // 0
			-size, size, -size,  // 2
			size, size, size, // 0
			size, -size, -size,  // 3
			-size, -size, size, // 1
			-size, size, -size,  // 2
			-size, -size, size, // 1
			size, -size, -size,  // 3
	};
	
	private FloatBuffer vertexBuffer = null;
	
	public Tet() {
		vertexBuffer = makeVertexBuffer();
	}
	
	public void draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawArrays(GL10.GL_LINES, 0, vertices.length / 3);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public FloatBuffer makeVertexBuffer() {
		ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = bb.asFloatBuffer();
		buffer.put(vertices);
		buffer.position(0);
		return buffer;
	}
	
}
