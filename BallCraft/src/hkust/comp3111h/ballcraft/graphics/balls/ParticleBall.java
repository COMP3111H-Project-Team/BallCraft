package hkust.comp3111h.ballcraft.graphics.balls;

import hkust.comp3111h.ballcraft.graphics.particles.Particle;
import hkust.comp3111h.ballcraft.server.Ball;

import java.util.ArrayList;
import java.util.Collections;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.common.Vec2;

public abstract class ParticleBall extends Ball {
    
    protected int COUNT;
    
    protected ArrayList<Particle> particles;
    
    public ParticleBall(float radius, Vec2 vec, float z) {
        super(radius, vec, z);
        particles = new ArrayList<Particle>();
    }
    
    public void move() {
        for (int i = 0; i < COUNT; i++) {
            this.moveParticle(i);
        }
    }
    
    protected abstract void moveParticle(int i);
    
    public void draw(GL10 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(this.getPosition().x, this.getPosition().y, this.getRadius() - this.z);
        
        Collections.sort(particles);
        for (int i = 0; i < COUNT; i++) {
            particles.get(i).draw(gl);
        }
        
        gl.glPopMatrix();
    }
    
}
