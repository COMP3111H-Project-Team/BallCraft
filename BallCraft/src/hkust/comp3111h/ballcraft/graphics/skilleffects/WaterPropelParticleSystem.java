package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.Particle;
import hkust.comp3111h.ballcraft.graphics.particles.WaterPropelParticle;

import java.util.ArrayList;
import java.util.Random;

public class WaterPropelParticleSystem extends ParticleSystemEffect {

    public WaterPropelParticleSystem(float x, float y, float z) {
        COUNT = 20;
        
        particles = new ArrayList<Particle>();
        
        for (int i = 0; i < COUNT; i++) {
            WaterPropelParticle p = new WaterPropelParticle(x, y, z);
            particles.add(p);
        }
    }

    public void refresh(float xSpeed, float ySpeed) {
        particles = new ArrayList<Particle>();
        
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            /*
            float speed = randGen.nextFloat() * 3 + 1;
            float angle = (randGen.nextFloat() * 15) / 57.3f;
            float zSpeed = randGen.nextFloat() * 1 - 0.5f;
            
            WaterPropelParticle p = new WaterPropelParticle(x, y, z);
            p.setSpeed((float) (speed * Math.cos(angle)), (float) (speed * Math.sin(angle)), zSpeed);
            
            
            particles.add(p);
            */
            particles.get(i).setSpeed(xSpeed, ySpeed, 0);
        }
    }
    
}
