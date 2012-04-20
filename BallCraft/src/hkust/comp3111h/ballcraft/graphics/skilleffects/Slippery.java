package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.Particle;
import hkust.comp3111h.ballcraft.graphics.particles.SlipperyParticle;
import hkust.comp3111h.ballcraft.server.Ball;

import java.util.ArrayList;

public class Slippery extends ParticleSystemEffect {
    
    public Slippery(Ball b) {
        COUNT = 9;
        
        this.effectTime = 15000;
        
        this.particles = new ArrayList<Particle>();
        
        this.ballEffected = b;
        
        this.x = b.getPosition().x;
        this.y = b.getPosition().y;
        this.z = b.z;
        
        for (int i = 0; i < COUNT; i++) {
            particles.add(new SlipperyParticle(30, 10, Math.PI * i * 40 / 180));
        }
    }
 
}
