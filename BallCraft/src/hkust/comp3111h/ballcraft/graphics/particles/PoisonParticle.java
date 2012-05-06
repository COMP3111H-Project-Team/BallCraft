package hkust.comp3111h.ballcraft.graphics.particles;

import hkust.comp3111h.ballcraft.R;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class PoisonParticle extends MagicParticle {
    
    private static int [] textures = new int[1];
    
    private double theta;
    private double rho;
    
    private double dTheta;
    private double dRho;
    private double motionRadius;
    
    public PoisonParticle(double theta, double rho, double r) {
        super(
                (float) (r * Math.cos(rho) * Math.sin(theta)), 
                (float) (r * Math.sin(rho) * Math.sin(theta)), 
                (float) (r * Math.cos(theta))
        );
        
        this.size = 6;
        
        this.gravityInfluence = false;
        
        this.theta = theta;
        this.rho = rho;
        this.motionRadius = r;
        
        Random randGen = new Random();
        dTheta = randGen.nextDouble() / 2;
        dRho = randGen.nextDouble() / 2;
    }
     
    public static void loadTexture(GL10 gl, Context context) {
        Bitmap bmp = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.poison_particle);
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
        this.theta += this.dTheta;
        this.rho += this.dRho;
        this.x = (float) (this.motionRadius * Math.cos(rho) * Math.sin(theta));
        this.y = (float) (this.motionRadius * Math.sin(rho) * Math.sin(theta));
        this.z = (float) (this.motionRadius * Math.cos(theta));
    }
    
}
