package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.FlameThrowParticle;
import hkust.comp3111h.ballcraft.graphics.particles.Particle;

import java.util.ArrayList;
import java.util.Random;

public class FlameThrow extends ParticleSystemEffect {

    public FlameThrow(float x, float y, float z, float angle) {
        COUNT = 30;
        
        particles = new ArrayList<Particle>();
        
        this.effectTime = 5000;
        
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            FlameThrowParticle fp = new FlameThrowParticle(x, y, z);
            
            float speedVal = randGen.nextFloat() * 3 + 5;
            float offset = (randGen.nextFloat() * 20 - 10) / speedVal;
            angle += offset;
            
            float xSpeed = (float) (speedVal * Math.cos(angle / 180 * Math.PI));
            float ySpeed = (float) (speedVal * Math.sin(angle / 180 * Math.PI));
            
            fp.setSpeed(xSpeed, ySpeed, 0);
            
            particles.add(fp);
        }
    }
        
}
