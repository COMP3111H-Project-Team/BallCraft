package hkust.comp3111h.ballcraft.graphics;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.common.Vec2;

import android.content.Context;

public class Sphere implements Drawable {

    private FloatBuffer vertexBuffer;
    private FloatBuffer normalBuffer;
    private FloatBuffer textureBuffer;
    
    private float radius = 20;
    private Vec2 pos;
    
    public Sphere() {
    }
    
    private void makeVertexBuffer() {
    }
    
    private void makeNormalBuffer() {
    }
    
    private void makeTextureBuffer() {
    }
    
    public static void loadTexture(Context context, GL10 gl) {
    }

    @Override
    public void draw(GL10 gl) {
        
    }
    
}
