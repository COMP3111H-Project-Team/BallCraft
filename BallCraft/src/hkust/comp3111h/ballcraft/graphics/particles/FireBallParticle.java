package hkust.comp3111h.ballcraft.graphics.particles;

import hkust.comp3111h.ballcraft.R;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class FireBallParticle extends BallParticle {
    
    private static int [] textures = new int[1];
    
    public FireBallParticle(double theta, double rho, double r) {
        super(theta, rho, r);
        
        this.size = 6;
        
        randGen = new Random();
        
        dTheta = randGen.nextDouble() / 2;
        dRho = 0;
    }
    
    public static void loadTexture(GL10 gl, Context context) {
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_particle);
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0); 
		bmp.recycle();
    }
    
    public void bindTexture(GL10 gl) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
    }
    
}
