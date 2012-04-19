package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.MassOverlordParticle;
import hkust.comp3111h.ballcraft.graphics.particles.Particle;
import hkust.comp3111h.ballcraft.server.Ball;

import java.util.ArrayList;

public class MassOverlord extends ParticleSystemEffect {

    public MassOverlord(Ball b) {
        COUNT = 18;
        
        this.effectTime = 1000;
        
        this.particles = new ArrayList<Particle>();
        
        this.ballEffected = b;
        
        this.x = b.getPosition().x;
        this.y = b.getPosition().y;
        this.z = b.z;
        
        for (int i = 0; i < COUNT; i++) {
            particles.add(new MassOverlordParticle(60, 10, Math.PI * i * 20 / 180));
        }
    }
    
}
