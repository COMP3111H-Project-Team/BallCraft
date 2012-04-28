package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.server.Ball;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class GrowRoot extends TextureEffect {

    private static int [] textures = new int[1];
    
    private boolean sizeIncreasing = true;
    
    public GrowRoot(Ball ballEffected) {
        super(ballEffected);
    }
    
    public static void loadTexture(GL10 gl, Context context) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.root_texture);
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
    
    @Override
    public void move() {
        if (this.sizeIncreasing) {
            if (this.size < 15) {
                this.size += 0.2f;
            } else {
                this.sizeIncreasing = false;
            }
        } else {
            if (this.size > 10) {
                this.size -= 0.2f;
            } else {
                this.sizeIncreasing = true;
            }
        }
    }

}
