package hkust.comp3111h.ballcraft.graphics;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * The particle system used for graphical effects
 */
public abstract class ParticleSystem implements Drawable {
    
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
    public void move() {
        if (this.isActive()) {
            boolean stillActive = false;
            for (int i = 0; i < COUNT; i++) {
                particles.get(i).move();
                if (this.particleActive(particles.get(i))) {
                    stillActive = true;
                }
            }
            if (!stillActive) {
                this.deactivate();
            }
        }
    }

    /**
     * Draw every particle in the particle system
     */
    public void draw(GL10 gl) {
        if (this.isActive()) {
            for (int i = 0; i < COUNT; i++) {
                particles.get(i).draw(gl);
            }
        }
    }
    
    /**
     * Check whether a particle is active
     * @param p The particle to be checked
     * @return True if particle is active
     */
    protected abstract boolean particleActive(Particle p);
    
    /**
     * Whether the particle system is active
     */
    protected boolean active = true;
    
    /**
     * Activate the particle system
     */
    protected void activate() {
        this.active = true;
    }
    
    /**
     * Deactivate the particle system
     */
    protected void deactivate() {
        this.active = false;
    }
    
    /**
     * Check whether the system is active
     * @return True if active
     */
    protected boolean isActive() {
        return active;
    }
    
}
