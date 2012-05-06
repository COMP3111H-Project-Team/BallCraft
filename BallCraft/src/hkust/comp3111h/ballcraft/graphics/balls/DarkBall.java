package hkust.comp3111h.ballcraft.graphics.balls;

import hkust.comp3111h.ballcraft.graphics.particles.DarkBallParticle;

import java.util.Random;

import org.jbox2d.common.Vec2;

public class DarkBall extends ParticleBall {

    public DarkBall(float radius, Vec2 vec, float z) {
        super(radius, vec, z);
        COUNT = 30;
        
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            double theta = randGen.nextDouble() * 180 / Math.PI;
            double rho = randGen.nextDouble() * 360 / Math.PI;
            DarkBallParticle p = new DarkBallParticle(theta, rho, radius);
            particles.add(p);
        }
    }

}
