package hkust.comp3111h.ballcraft.server;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.common.Vec2;

public class Wall extends Unit {

	public Vec2 start;
	public Vec2 end;
	
	private float [] vertices;
	
	private FloatBuffer vertexBuffer = null;
	
	public Wall(Vec2 start, Vec2 end) {
		this.start = start;
		this.end = end;
		vertices = new float [12];
		vertices[0] = start.x;
		vertices[1] = start.y;
		vertices[2] = 0f;
		vertices[3] = start.x;
		vertices[4] = start.y;
		vertices[5] = 50f;
		vertices[6] = end.x;
		vertices[7] = end.y;
		vertices[8] = 0f;
		vertices[9] = end.x;
		vertices[10] = end.y;
		vertices[11] = 50f;
		
		vertexBuffer = makeVertexBuffer();
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
	
	@Override
	public String toSerializedString() {
		String serialized = "";
		return null;
	}
}
