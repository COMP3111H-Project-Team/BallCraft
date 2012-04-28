package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.Particle;
import hkust.comp3111h.ballcraft.graphics.particles.WaterPropelParticle;

import java.util.ArrayList;
import java.util.Random;

public class WaterPropel extends ParticleSystemEffect {

    public WaterPropel(float x, float y, float z, float angle) {
        COUNT = 30;
        
        particles = new ArrayList<Particle>();
        
        this.effectTime = 5000;
        
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            WaterPropelParticle p = new WaterPropelParticle(x, y, z + 15);
            
            float speedVal = randGen.nextFloat() * 4 + 2;
            float offset = (randGen.nextFloat() * 10 - 5) / speedVal;
            angle += offset;
            
            float xSpeed = (float) (speedVal * Math.cos(angle / 180 * Math.PI));
            float ySpeed = (float) (speedVal * Math.sin(angle / 180 * Math.PI));
            
            p.setSpeed(xSpeed, ySpeed, 0);
            
            particles.add(p);
        }
    }
        
}
