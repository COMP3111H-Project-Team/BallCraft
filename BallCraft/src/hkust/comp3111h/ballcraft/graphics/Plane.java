package hkust.comp3111h.ballcraft.graphics;


import hkust.comp3111h.ballcraft.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Plane implements Drawable {
	
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
			size, 1.0f
	};
	
	private FloatBuffer vertexBuffer = null;
	private FloatBuffer textureBuffer = null;
	private FloatBuffer normalBuffer = null;
	
	private int [] textures = new int[1];
	
	public Plane() {
		vertexBuffer = makeVertexBuffer();
		textureBuffer = makeTextureBuffer();
	}
	
	public void draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glEnable(GL10.GL_NORMALIZE);
		gl.glEnable(GL10.GL_RESCALE_NORMAL);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		// gl.glNormalPointer(3, GL10.GL_FLOAT, pointer)
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
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
		ByteBuffer bb = ByteBuffer.allocateDirect(texture.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = bb.asFloatBuffer();
		buffer.put(texture);
		buffer.position(0);
		return buffer;
	}
	
	public void loadTexture(GL10 gl, Context context) {
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.texture01);
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
		bmp.recycle();
	}
	
}
