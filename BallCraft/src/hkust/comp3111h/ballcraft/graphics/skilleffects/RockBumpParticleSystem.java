package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.DarkBallParticle;
import hkust.comp3111h.ballcraft.graphics.particles.Particle;
import hkust.comp3111h.ballcraft.server.Ball;

import java.util.ArrayList;
import java.util.Random;

public class RockBumpParticleSystem extends ParticleSystemEffect {
    
    public RockBumpParticleSystem(Ball b) {
        COUNT = 9;
        
        this.effectTime = 15000;
        
        this.particles = new ArrayList<Particle>();
        
        this.ballEffected = b;
        
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            double theta = randGen.nextDouble() * 180 / Math.PI;
            double rho = randGen.nextDouble() * 360 / Math.PI;
            DarkBallParticle p = new DarkBallParticle(theta, rho, 20);
            particles.add(p);
        }
    }
 
}
