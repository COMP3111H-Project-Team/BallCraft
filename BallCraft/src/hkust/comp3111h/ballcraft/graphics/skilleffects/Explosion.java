package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.ExplosionParticle;
import hkust.comp3111h.ballcraft.graphics.particles.Particle;

import java.util.ArrayList;
import java.util.Random;

public class Explosion extends ParticleSystemEffect {

    public Explosion(float x, float y, float z) {
        COUNT = 30;
        
        this.effectTime = 2000;
        
        particles = new ArrayList<Particle>();
        
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            ExplosionParticle ep = new ExplosionParticle(x, y, z);
            float xSpeed = randGen.nextFloat() * 4 - 2;
            float ySpeed = randGen.nextFloat() * 4 - 2;
            float zSpeed = randGen.nextFloat() * 2 + 4;
            ep.setSpeed(xSpeed, ySpeed, zSpeed);
            particles.add(ep);
        }
        
    }
    
}
