package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.Particle;

import java.util.ArrayList;
import java.util.Random;

/*
public class ParticleSystem2 extends ParticleSystem {
    
    public ParticleSystem2(float x, float y, float z) {
        COUNT = 18;
        
        particles = new ArrayList<Particle>();
        
        Random randGen = new Random();
        for (int i = 0; i < COUNT; i++) {
            double angle = i * 20 / 57.3;
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);
            
            Particle p = new Particle(x + cos * 15, y + sin * 15, z + 3 * randGen.nextFloat());
            p.setGravityInfluence(false);
            p.setSpeed(-sin * 3, cos * 3, 0);
            
            particles.add(p);
        }
    }

    @Override
    protected boolean particleActive(Particle p) {
        return p.x * p.x + p.y + p.y < 100000;
    }
    
}
*/