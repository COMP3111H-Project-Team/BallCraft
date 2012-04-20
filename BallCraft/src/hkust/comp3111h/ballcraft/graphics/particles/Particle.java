package hkust.comp3111h.ballcraft.graphics.particles;

import hkust.comp3111h.ballcraft.graphics.Drawable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.common.Vec3;

public abstract class Particle implements Drawable, Comparable<Object> {
    
    protected static FloatBuffer vertexBuffer;
    protected static FloatBuffer textureBuffer;
    
    protected static final float [] vertices = {
            -1, -1, 0,
            -1, 1, 0,
            1, -1, 0,
            1, 1, 0,
    };
    
    protected static final float[] texture = { 
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
    };
    
    protected static final float gravity = -0.1f;
    
    public float x = 0;
    public float y = 0;
    public float z = 0;
    
    public float xSpeed = 0;
    public float ySpeed = 0;
    public float zSpeed = 0;
    
    protected float size = 3;
    
    protected boolean gravityInfluence;
    
    static {
        vertexBuffer = makeVertexBuffer();
        textureBuffer = makeTextureBuffer();
    }

    public Particle(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Particle(float x, float y, float z, float xSpeed, float ySpeed, float zSpeed) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }
    
    /**
     * Set whether gravity influences the particle, if so the particle will have a z-direction acceleration
     * @param gi Whether gravity influences
     */
    public void setGravityInfluence(boolean gi) {
        this.gravityInfluence = gi;
    }
    
    public void setSpeed(float xSpeed, float ySpeed, float zSpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }
    
    public Vec3 getSpeed() {
        return new Vec3(xSpeed, ySpeed, zSpeed);
    }
    
    public void setSize(float size) {
        this.size = size;
    }
    
    public float getSize() {
        return this.size;
    }

    public abstract void move();
    
    public void draw(GL10 gl) {
        gl.glPushMatrix();
        
	        gl.glTranslatef(x, y, z);
	        gl.glScalef(size, size, 1);
	        gl.glRotatef(180, 0, 0, 1);
	        gl.glEnable(GL10.GL_TEXTURE_2D);
	        
	        this.bindTexture(gl);
	        
	        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	        
	        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
	        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
	        
	        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	        
	        gl.glDisable(GL10.GL_TEXTURE_2D);
        
	    gl.glPopMatrix();
    }
    
    protected abstract void bindTexture(GL10 gl);
    
    private static FloatBuffer makeVertexBuffer() {
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = bb.asFloatBuffer();
        buffer.put(vertices);
        buffer.position(0);
        return buffer;
    }
    
    private static FloatBuffer makeTextureBuffer() {
        ByteBuffer bb = ByteBuffer.allocateDirect(texture.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = bb.asFloatBuffer();
        buffer.put(texture);
        buffer.position(0);
        return buffer;
    }

    public int compareTo(Object obj) {
        Particle p = (Particle) obj;
        if (this.z > p.z) {
            return 1;
        } else {
            return -1;
        }
    }
    
    public boolean isActive() {
        return true;
    }
    
}
