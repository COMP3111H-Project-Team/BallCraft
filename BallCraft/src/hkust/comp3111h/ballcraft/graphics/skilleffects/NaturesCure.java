package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.NaturesCureParticle;
import hkust.comp3111h.ballcraft.graphics.particles.Particle;
import hkust.comp3111h.ballcraft.server.Ball;

import java.util.ArrayList;

public class NaturesCure extends ParticleSystemEffect {

    public NaturesCure(Ball b) {
        COUNT = 72;
        
        this.effectTime = 3000;
        
        this.particles = new ArrayList<Particle>();
        
        this.ballEffected = b;
        
        this.x = b.getPosition().x;
        this.y = b.getPosition().y;
        this.z = b.z;
        
        for (int i = 0; i < COUNT; i++) {
            particles.add(new NaturesCureParticle(60, 10, Math.PI * i * 5 / 180));
        }
    }
    
}
