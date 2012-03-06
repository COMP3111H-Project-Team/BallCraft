package hkust.comp3111h.ballcraft.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * 
 * @author guanlun
 */
class ParticleSystem1 extends ParticleSystem {
	private Particle [] mParticles;
	private int COUNT = 360;
	
	private FloatBuffer mVertexBuffer;
	private ShortBuffer mIndexBuffer;
	
	public ParticleSystem1(float x, float y, float z) {
		mParticles = new Particle [COUNT];
		// Random gen = new Random(System.currentTimeMillis());
		for (int i = 0; i < COUNT; i++) {
			float xs = (float) (20f * Math.cos(Math.toRadians(i)));
			float ys = (float) (20f * Math.sin(Math.toRadians(i)));
			
			mParticles[i] = new Particle(x, y, z, xs, ys, 0f);
			// mParticles[i] = new Particle(x, y, z, gen);
		}
		
		float [] coords = {
				-8.0f, 0.0f, 0.0f, 
                8.0f, 0.0f, 0.0f, 
                0.0f, .0f, 15.0f
        };
		
		short [] icoords = {0, 1, 2};
		mVertexBuffer = makeFloatBuffer(coords);
		mIndexBuffer = makeShortBuffer(icoords);
	}
	
	private FloatBuffer makeFloatBuffer(float [] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	
	private ShortBuffer makeShortBuffer(short [] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		ShortBuffer sb = bb.asShortBuffer();
		sb.put(arr);
		sb.position(0);
		return sb;
	}
	
	@Override
	public void move() {
		for (int i = 0; i < COUNT; i++) {
			mParticles[i].move(1);
		}
	}
	
	@Override
	public void draw(GL10 gl) {
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColor4f(1f, 0.5f, 0f, 1f);
		for (int i = 0; i < COUNT; i++) {
			gl.glPushMatrix();
			gl.glTranslatef(mParticles[i].x, mParticles[i].y, mParticles[i].z);
			gl.glDrawElements(GL10.GL_TRIANGLES, 3, GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
			gl.glPopMatrix();
		}
	}
}