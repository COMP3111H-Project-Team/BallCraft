package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.Particle;

import java.util.ArrayList;
import java.util.Random;

/*
public class ParticleSystem3 extends ParticleSystem {
    
    public ParticleSystem3(float x, float y, float z) {
        COUNT = 50;
        
        particles = new ArrayList<Particle>();
        
        Random randGen = new Random();
        for (int i = 0; i < COUNT; i++) {
            float speed = randGen.nextFloat() * 5 + 2;
            float angle = ((randGen.nextFloat() * 15) / 57.3f) / speed + 30f / 57.3f;
            float zSpeed = randGen.nextFloat() * 1 - 0.5f;
            Particle p = new Particle(x, y, z);
            p.setGravityInfluence(false);
            p.setSpeed((float) (speed * Math.cos(angle)), (float) (speed * Math.sin(angle)), zSpeed);
            
            particles.add(p);
        }
    }
    
    @Override
    public void moveParticle(int i) {
        Particle p = particles.get(i);
        p.setSize(p.getSize() + 0.2f);
        p.move();
    }

    @Override
    protected boolean particleActive(Particle p) {
        return p.x * p.x + p.y + p.y < 100000;
    }
    
}
*/