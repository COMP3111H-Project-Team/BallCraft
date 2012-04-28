package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.server.Ball;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public abstract class TextureEffect extends SkillEffect {
    
    private static FloatBuffer vertexBuffer = null;
    private static FloatBuffer textureBuffer = null;
    private static FloatBuffer normalBuffer = null;
    
    private static final float[] vertices = { 
            -1, -1, 0.0f,
            -1, 1, 0.0f,
            1, -1, 0.0f,
            1, 1, 0.0f,
    };
    
    private static final float[] normals = { 
            0, 0, 1, 
            0, 0, 1, 
            0, 0, 1, 
            0, 0, 1, 
    };

    private static final float[] texture = { 
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
    };
    
    protected float size = 10;
    
    static {
        vertexBuffer = makeVertexBuffer();
        textureBuffer = makeTextureBuffer();
        normalBuffer = makeNormalBuffer();
    }
    
    public TextureEffect() {
        super();
        this.drawBeforeBalls = true;
    }
    
    public TextureEffect(Ball ballEffected) {
        super(ballEffected);
        this.drawBeforeBalls = true;
    }
 
    public void draw(GL10 gl) {
        gl.glPushMatrix();
        
            gl.glTranslatef(this.x, this.y, this.z);
            gl.glRotatef(180, 0, 0, 1);
            this.rotate(gl);
            gl.glScalef(size, size, 1);
	        gl.glEnable(GL10.GL_TEXTURE_2D);
	        
	        this.bindTexture(gl);
	        
	        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
	        
	        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
	        gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
	        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
	        
	        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	        
	        gl.glDisable(GL10.GL_TEXTURE_2D);
	        
	    gl.glPopMatrix();
    }
    
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

    private static FloatBuffer makeNormalBuffer() {
        ByteBuffer bb = ByteBuffer.allocateDirect(normals.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = bb.asFloatBuffer();
        buffer.put(normals);
        buffer.position(0);
        return buffer;
    }
    
    protected abstract void bindTexture(GL10 gl);
    
    protected void rotate(GL10 gl) {
    }
    
    @Override
    public void move() {
    }
    
}
