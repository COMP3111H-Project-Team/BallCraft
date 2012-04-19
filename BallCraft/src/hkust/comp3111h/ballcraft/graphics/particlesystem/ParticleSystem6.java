package hkust.comp3111h.ballcraft.graphics.particlesystem;

import hkust.comp3111h.ballcraft.graphics.particles.Particle;

import java.util.ArrayList;
import java.util.Random;

/*
public class ParticleSystem6 extends ParticleSystem {
    
    Random randGen = new Random();
    
    private float radius = 10;
    
    public ParticleSystem6(float x, float y, float z) {
        COUNT = 60;
        particles = new ArrayList<Particle>();
        for (int i = 0; i < COUNT; i++) {
            particles.add(new Particle(0, 0, 0));
        }
    }
    
    @Override
    protected void moveParticle(int i) {
        double rho = randGen.nextDouble() * 360 / Math.PI;
        double theta = randGen.nextDouble() * 180 / Math.PI;
        Particle p = particles.get(i);
        p.x = (float) (radius * Math.cos(rho) * Math.sin(theta));
        p.y = (float) (radius * Math.sin(rho) * Math.sin(theta));
        p.z = (float) (radius * Math.cos(theta));
    }

    @Override
    protected boolean particleActive(Particle p) {
        return true;
    }

}
*/