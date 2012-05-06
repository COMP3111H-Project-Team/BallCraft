package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.Particle;
import hkust.comp3111h.ballcraft.graphics.particles.WaterPropelParticle;

import java.util.ArrayList;
import java.util.Random;

import org.jbox2d.common.Vec2;

public class WaterPropel extends ParticleSystemEffect {

    public WaterPropel(Vec2 selfPos, Vec2 enemyPos) {
        COUNT = 20;
        
        particles = new ArrayList<Particle>();
        
        this.effectTime = 3000;
        
        this.x = selfPos.x;
        this.y = selfPos.y;
        this.z = 10;
        
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            WaterPropelParticle p = new WaterPropelParticle(0, 0, 0);
            
            float xSpeed = (selfPos.x - enemyPos.x) * (randGen.nextFloat() + 0.5f) / 10;
            float ySpeed = (selfPos.y - enemyPos.y) * (randGen.nextFloat() + 0.5f) / 10;
            
            p.setSpeed(xSpeed, ySpeed, 0);
            
            particles.add(p);
        }
    }
        
}
