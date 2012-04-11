package hkust.comp3111h.ballcraft.graphics;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

public class ParticleSystem5 extends ParticleSystem {
    
    private double angle = 0;
    
    public ParticleSystem5(float x, float y, float z) {
        COUNT = 1;
        
        particles = new ArrayList<Particle>();
        
        Random randGen = new Random();
        for (int i = 0; i < COUNT; i++) {
            double angle = i * 20 / 57.3;
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);
            
            Particle p = new Particle(x + cos * 15, y + sin * 15, z + 3 * randGen.nextFloat());
            p.setGravityInfluence(false);
            p.setSpeed(-sin, cos, 0);
            
            particles.add(p);
        }
    }
    
    @Override
    protected void moveParticle(int i) {
        angle += 3 / 57.3;
        Particle p = particles.get(i);
        double sin = - p.getSpeed().x;
        double angle = Math.asin(sin);
        double newAngle = angle + 3 / 57.3;
        double newSin = Math.sin(newAngle);
        p.setSpeed((float) -newSin, (float) (Math.cos(newAngle)), 0);
        p.move();
        Log.w("" + newAngle, "" + newSin);
    }

    @Override
    protected boolean particleActive(Particle p) {
        return p.x * p.x + p.y + p.y < 100000;
    }
    
}