package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.particles.FlameThrowParticle;
import hkust.comp3111h.ballcraft.graphics.particles.Particle;

import java.util.ArrayList;
import java.util.Random;

import org.jbox2d.common.Vec2;

public class FlameThrow extends ParticleSystemEffect {

    public FlameThrow(Vec2 selfPos, Vec2 enemyPos) {
        COUNT = 20;
        
        particles = new ArrayList<Particle>();
        
        this.effectTime = 3000;
        
        this.x = selfPos.x;
        this.y = selfPos.y;
        this.z = 10;
        
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            FlameThrowParticle fp = new FlameThrowParticle(0, 0, 0);
            
            float xSpeed = (enemyPos.x - selfPos.x) * (randGen.nextFloat() / 2 + 0.75f) / 10;
            float ySpeed = (enemyPos.y - selfPos.y) * (randGen.nextFloat() / 2 + 0.75f) / 10;
            
            fp.setSpeed(xSpeed, ySpeed, 0);
            
            particles.add(fp);
        }
    }
        
}
