package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.Particle;

import java.util.ArrayList;
import java.util.Random;

/*
public class ParticleSystem4 extends ParticleSystem {
    
    public ParticleSystem4(float x, float y, float z) {
        COUNT = 72;
        
        particles = new ArrayList<Particle>();
        
        Random randGen = new Random();
        for (int i = 0; i < COUNT; i++) {
            double angle = i * 5 / 57.3;
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);
            
            Particle p = new Particle(x, y, randGen.nextFloat() * 3 + z);
            p.setGravityInfluence(false);
            p.setSpeed(cos * 20, sin * 20, 0);
            
            particles.add(p);
        }
    }

    @Override
    protected boolean particleActive(Particle p) {
        return p.x * p.x + p.y + p.y < 100000;
    }
    
}
*/