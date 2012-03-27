package hkust.comp3111h.ballcraft.server;


import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.client.ClientGameState;
import hkust.comp3111h.ballcraft.graphics.GraphicUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Wall extends Unit {
	
	private FloatBuffer vertexBuffer = null;
    private FloatBuffer textureBuffer = null;
	private FloatBuffer normalBuffer = null;
	
	private float [] vertices = {
			0.5f, 0.5f, 0f,
			0.5f, 0.5f, 20f,
			-0.5f, 0.5f, 0f,
			-0.5f, 0.5f, 20f,
			
			-0.5f, 0.5f, 0f,
			-0.5f, 0.5f, 20f,
			-0.5f, -0.5f, 0f,
			-0.5f, -0.5f, 20f,
			
			-0.5f, -0.5f, 0f,
			-0.5f, -0.5f, 20f,
			0.5f, -0.5f, 0f,
			0.5f, -0.5f, 20f,
			
			0.5f, -0.5f, 0f,
			0.5f, -0.5f, 20f,
			0.5f, 0.5f, 0f,
			0.5f, 0.5f, 20f,
			
			0.5f, 0.5f, 20f,
			0.5f, -0.5f, 20f,
			-0.5f, 0.5f, 20f,
			-0.5f, -0.5f, 20f,
	};
	
	private float [] normals = {
			0, 1, 0,
			0, 1, 0,
			0, 1, 0,
			0, 1, 0,
			
			-1, 0, 0,
			-1, 0, 0,
			-1, 0, 0,
			-1, 0, 0,
		
			0, -1, 0,
			0, -1, 0,
			0, -1, 0,
			0, -1, 0,
			
			1, 0, 0,
			1, 0, 0,
			1, 0, 0,
			1, 0, 0,
			
			0, 0, 1,
			0, 0, 1,
			0, 0, 1,
			0, 0, 1,
	};
	
	private float [] texture = {
            0.0f, 1.0f,     // top left     (V2)
            0.0f, 0.0f,     // bottom left  (V1)
            1.0f, 1.0f,     // top right    (V4)
            1.0f, 0.0f      // bottom right (V3)
	};
	
    private int [] textures = new int[1];

	private Vec2 pos; // position of the wall's center
	private float angle;
	private float length;
	private float width;
	
	public Wall(Vec2 start, Vec2 end) {
		this(start, end, true);
	}
	
	public Wall(Vec2 start, Vec2 end, boolean isServer) {
		if (isServer) {
			start = start.mul(1.0f / rate);
			end = end.mul(1.0f / rate);
			
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.STATIC;
			body = ServerGameState.world.createBody(bodyDef);
			PolygonShape shape = new PolygonShape();
			
			Vec2 vector = start.sub(end);
			float length = vector.normalize() / 2;
			Vec2 midPoint = start.add(end).mul(0.5f);
			float angle = (float)Math.acos(Vec2.dot(vector, new Vec2(1, 0)));
			shape.setAsBox(length, 0, midPoint, angle);
			
			body.createFixture(shape, 0); // bind the dense, friction-laden fixture to the body

			vertices = new float [4];
			vertices[0] = start.x;
			vertices[1] = start.y;
			vertices[2] = end.x;
			vertices[3] = end.y;
			
		} else {
			double slope = (end.y - start.y) / (end.x - start.x);
			pos = new Vec2((end.x + start.x) / 2, (end.y + start.y) / 2);
			angle = (float) (Math.atan(slope) * 180 / Math.PI);
			length = (float) Math.sqrt((end.y - start.y) * (end.y - start.y) 
					+ (end.x - start.x) * (end.x - start.x)); 
			width = 5;
			
			vertexBuffer = makeVertexBuffer();
			normalBuffer = makeNormalBuffer();
			
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.STATIC;
			body = ClientGameState.world.createBody(bodyDef);
			PolygonShape shape = new PolygonShape();
			
			Vec2 vector = start.sub(end);
			float length = vector.normalize();
			Vec2 midPoint = start.add(end).mul(0.5f);
			float angle = (float)Math.acos(Vec2.dot(vector, new Vec2(1, 0)));
			shape.setAsBox(length, 0, midPoint, angle);
			
			body.createFixture(shape, 0); // bind the dense, friction-laden fixture to the body
			
			textureBuffer = this.makeTextureBuffer();
		}
	}
	
	public void draw(GL10 gl) {
		gl.glPushMatrix();
		
			GraphicUtils.setMaterialColor(gl, new float [] {1, 0, 0, 1});
				
			gl.glTranslatef(pos.x, pos.y, 0);
			gl.glRotatef(angle, 0, 0, 1);
			gl.glScalef(length, width, 1);
			
	        gl.glEnable(GL10.GL_TEXTURE_2D);
	        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
	        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			
			gl.glDisable(GL10.GL_TEXTURE_2D);
			
			GraphicUtils.restoreMaterialColor(gl);
				
		gl.glPopMatrix();
	}
	
	private FloatBuffer makeVertexBuffer() {
		ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = bb.asFloatBuffer();
		buffer.put(vertices);
		buffer.position(0);
		return buffer;
	}
	
    private FloatBuffer makeTextureBuffer() {
        ByteBuffer bb = ByteBuffer.allocateDirect(texture.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = bb.asFloatBuffer();
        buffer.put(texture);
        buffer.position(0);
        return buffer;
    }
	
	private FloatBuffer makeNormalBuffer() {
		ByteBuffer bb = ByteBuffer.allocateDirect(normals.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = bb.asFloatBuffer();
		buffer.put(normals);
		buffer.position(0);
		return buffer;
	}
	
	@Override
	public String toSerializedString()  {
		String serialized = "";
		serialized += "wall:";
		serialized += vertices[0] * rate + "," + vertices[1] * rate + ",";
		serialized += vertices[2] * rate + "," + vertices[3] * rate;
		return serialized;
	}

	@Override
	public void updateFromString(String string) {
	}
	
	public void loadTexture(GL10 gl, Context context) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.metal_texture);
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0); 
		bmp.recycle();
	}
}