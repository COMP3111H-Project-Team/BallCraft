package hkust.comp3111h.ballcraft.graphics;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Plane {
	
	private float size = 200f;
	
	private float [] vertices = {
			-size, -size, 0.0f,  // 0, Top Left
			-size, size, 0.0f,  // 1, Bottom Left
			size, -size, 0.0f,  // 2, Bottom Right
			size, size, 0.0f,  // 3, Top Right
	};
	
	private float [] texture = {
			0.0f, size, 
			0.0f, 0.0f,
			size, size,
			1.0f, 1.0f
	};
	
	private FloatBuffer vertexBuffer = null;
	private FloatBuffer textureBuffer = null;
	
	private int [] textures = new int[1];
	
	public Plane() {
		vertexBuffer = makeVertexBuffer();
		textureBuffer = makeTextureBuffer();
	}
	
	public void draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
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
	
	public FloatBuffer makeTextureBuffer() {
		ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = bb.asFloatBuffer();
		buffer.put(texture);
		buffer.position(0);
		return buffer;
	}
	
	public void loadTexture(GL10 gl, Context context) {
	}
}
