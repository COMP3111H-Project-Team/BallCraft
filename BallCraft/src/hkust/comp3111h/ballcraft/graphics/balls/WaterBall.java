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
            double rho = randGen.nextDouble() * 360 / Math.PI;
            double theta = randGen.nextDouble() * 180 / Math.PI;
            float xc = this.getPosition().x + (float) (radius * Math.cos(rho) * Math.sin(theta));
            float yc = this.getPosition().y + (float) (radius * Math.sin(rho) * Math.sin(theta));
            float zc = radius + (float) (radius * Math.cos(theta)); 
            WaterBallParticle p = new WaterBallParticle(xc, yc, zc);
            particles.add(p);
        }
    }

    @Override
    protected void moveParticle(int i) {
        
    }

}
