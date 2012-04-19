package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.Particle;

import java.util.ArrayList;
import java.util.Collections;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

/**
 * The particle system used for graphical effects
 */
public abstract class ParticleSystemEffect extends SkillEffect {
    
    /**
     * Number of particles in the system
     */
    protected int COUNT;
    
    /**
     * Array of all the particles
     */
    protected ArrayList<Particle> particles;
    
    /**
     * Move every particle in the particle system
     */
    @Override
    public void move() {
        if (this.ballEffected != null) { // attached to ball
            this.x = this.ballEffected.getPosition().x;
            this.y = this.ballEffected.getPosition().y;
            this.z = this.ballEffected.z;
        }
        for (int i = 0; i < COUNT; i++) {
            this.moveParticle(i);
        }
    }
    
    /**
     * Move a particle specified by its position in the array
     * @param i
     */
    protected void moveParticle(int i) {
        particles.get(i).move();
    }

    /**
     * Draw every particle in the particle system
     */
    public void draw(GL10 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(this.x, this.y, this.z);
        
        Collections.sort(particles);
        for (int i = 0; i < COUNT; i++) {
            particles.get(i).draw(gl);
        }
        
        gl.glPopMatrix();
    }
    
}
