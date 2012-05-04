package hkust.comp3111h.ballcraft.graphics.balls;

import hkust.comp3111h.ballcraft.graphics.particles.WaterBallParticle;

import java.util.Random;

import org.jbox2d.common.Vec2;

public class WaterBall extends ParticleBall {

    public WaterBall(float radius, Vec2 vec, float z) {
        super(radius, vec, z);
        
        COUNT = 60;
        
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            double theta = randGen.nextDouble() * 180 / Math.PI;
            double rho = randGen.nextDouble() * 360 / Math.PI;
            WaterBallParticle p = new WaterBallParticle(theta, rho, radius);
            particles.add(p);
        }
    }

}
