package hkust.comp3111h.ballcraft.graphics.particles;

import hkust.comp3111h.ballcraft.R;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class WaterPropelParticle extends MagicParticle {
    
    private static int textureImg = R.drawable.water_particle;
    
    private static int [] textures = new int[1];
    
    public WaterPropelParticle(float x, float y, float z) {
        super(x, y, z);
        
        this.gravityInfluence = true;
        
        this.size = 15;
    }

    public static void loadTexture(GL10 gl, Context context) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), textureImg);
        gl.glGenTextures(1, textures, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0); 
        bmp.recycle();
    }
    
    @Override
    protected void bindTexture(GL10 gl) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
    }
    
}
