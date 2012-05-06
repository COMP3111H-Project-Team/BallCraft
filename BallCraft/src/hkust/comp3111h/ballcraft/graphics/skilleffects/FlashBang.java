package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.server.Ball;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.common.Vec3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class FlashBang extends TextureEffect {

    private static int [] textures = new int[1];
    
    private Vec3 speed;
    
    public FlashBang(Ball ballEffected, Ball throwingBall) {
        super(ballEffected);
        
        float xSpeed = (throwingBall.getPosition().x - ballEffected.getPosition().x) / 20;
        float ySpeed = (throwingBall.getPosition().y - ballEffected.getPosition().y) / 20;
        speed = new Vec3(xSpeed, ySpeed, 3);
        
        this.drawBeforeBalls = false;
    }
    
    public static void loadTexture(GL10 gl, Context context) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.flashbang);
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
        this.speed.z -= 0.1;
        this.x += this.speed.x;
        this.y += this.speed.y;
        this.z += this.speed.z;
    }
    
    @Override
    public boolean timeout() {
        return this.z < 0;
    }

}
