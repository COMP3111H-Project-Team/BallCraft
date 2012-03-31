package hkust.comp3111h.ballcraft.graphics;

import java.util.ArrayList;
import java.util.Random;

public class ParticleSystem2 extends ParticleSystem {
    
    public ParticleSystem2(float x, float y, float z) {
        COUNT = 100;
        
        particles = new ArrayList<Particle>();
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            particles.add(new Particle(x, y, z, 
                    randGen.nextFloat() * 6 - 3, randGen.nextFloat() * 6 - 3, 5));
        }
    }

    @Override
    protected boolean particleActive(Particle p) {
        float xAbs = Math.abs(p.x);
        float yAbs = Math.abs(p.y);
        return xAbs * xAbs + yAbs * yAbs < 1000;
    }
    
}