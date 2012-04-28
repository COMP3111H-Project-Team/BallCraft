package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.server.Ball;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class IronWill extends TextureEffect {
    
    private static int [] textures = new int[1];
    
    public IronWill(Ball ballEffected) {
        super(ballEffected);
        
        this.drawBeforeBalls = false;
        this.size = 10;
    }
    
    public static void loadTexture(GL10 gl, Context context) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.shield);
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
        this.x = this.ballEffected.getPosition().x;
        this.y = this.ballEffected.getPosition().y - 10;
        this.z = this.ballEffected.z + 40;
    }
    
    @Override
    public void rotate(GL10 gl) {
        gl.glRotatef(45, 1, 0, 0);
    }
    
}
