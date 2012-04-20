package hkust.comp3111h.ballcraft.graphics.particles;

import java.util.Random;

import hkust.comp3111h.ballcraft.R;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class SlipperyParticle extends MagicParticle {
    
    private static int [] textures = new int[1];
    
    private double theta;
    private float motionRadius;
    private float zSpeed;
    
    public SlipperyParticle(float radius, float z, double theta) {
        super((float) (radius * Math.cos(theta)),
                (float) (radius * Math.sin(theta)),
                z);
        
        this.size = 6;
        
        this.gravityInfluence = false;
        this.theta = theta;
        this.motionRadius = radius;
        this.z = z;
        
        Random randGen = new Random();
        this.zSpeed = randGen.nextFloat() * 2 - 1;
        if (this.zSpeed > 0) {
            this.zSpeed++;
        } else {
            this.zSpeed--;
        }
    }
     
    public static void loadTexture(GL10 gl, Context context) {
        Bitmap bmp = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.water_particle);
        gl.glGenTextures(1, textures, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0); 
        bmp.recycle();
    }
 
    @Override
    public void bindTexture(GL10 gl) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
    }
     
    @Override
    public void move() {
        this.theta += 0.1;
        this.x = this.motionRadius * (float) (Math.cos(this.theta));
        this.y = this.motionRadius * (float) (Math.sin(this.theta));
        this.z += this.zSpeed;
        
        if (this.z > 40 || this.z < 0) {
            this.zSpeed = - this.zSpeed;
        }
    }
    
}
