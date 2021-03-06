package hkust.comp3111h.ballcraft.graphics;

import hkust.comp3111h.ballcraft.TerrainDef;
import hkust.comp3111h.ballcraft.client.ClientGameState;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Plane implements Drawable {

    private static FloatBuffer vertexBuffer = null;
    private static FloatBuffer textureBuffer = null;
    private static FloatBuffer normalBuffer = null;

    private static final float[] vertices = { 
            -1, -1, 0,
            -1, 1, 0,
            1, -1, 0,
            1, 1, 0,
    };
    
    private static final float[] normals = { 
            0, 0, 1, 
            0, 0, 1, 
            0, 0, 1, 
            0, 0, 1, 
    };

    private static final float[] texture = { 
            0, 1,
            0, 0,
            1, 1,
            1, 0,
    };

    private static int [] textures = new int[1];

    private float size = 200f;
    
    static {
        vertexBuffer = makeVertexBuffer();
        textureBuffer = makeTextureBuffer();
        normalBuffer = makeNormalBuffer();
    }

    public Plane() {
    }

    public void draw(GL10 gl) {
        gl.glPushMatrix();
        
            gl.glScalef(size, size, 1);
        
	        gl.glEnable(GL10.GL_TEXTURE_2D);
	        
	        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
	        
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

    public static void loadTexture(GL10 gl, Context context) {
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), 
		        TerrainDef.getTerrainFloorTextureById(
		                ClientGameState.getClientGameState().getMapTerrain()));
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0); 
		bmp.recycle();
    }

}
