package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.MagicParticle;
import hkust.comp3111h.ballcraft.graphics.particles.Particle;

import java.util.ArrayList;
import java.util.Random;

public class ParticleSystem1 extends ParticleSystemEffect {

    public ParticleSystem1(float x, float y, float z) {
        COUNT = 10;
        
        particles = new ArrayList<Particle>();
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            /*
            particles.add(new MagicParticle(x, y, 10, 
                    randGen.nextFloat() * 6 - 3, randGen.nextFloat() * 6 - 3, randGen.nextFloat() * 1 + 5));
            */
        }
    }

}