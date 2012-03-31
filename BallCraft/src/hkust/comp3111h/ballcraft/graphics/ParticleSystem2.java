package hkust.comp3111h.ballcraft.graphics;

import java.util.ArrayList;

public class ParticleSystem2 extends ParticleSystem {
    
    public ParticleSystem2(float x, float y, float z) {
        COUNT = 18;
        
        particles = new ArrayList<Particle>();
        
        for (int i = 0; i < COUNT; i++) {
            double angle = i * 20 / 57.3;
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);
            particles.add(new Particle(x + cos * 10, y + sin * 10, z));
        }
    }
    
    @Override
    public void move() {
        if (this.isActive()) {
            boolean stillActive = false;
            for (int i = 0; i < COUNT; i++) {
                particles.get(i).move();
                if (this.particleActive(particles.get(i))) {
                    stillActive = true;
                }
            }
            if (!stillActive) {
                this.deactivate();
            }
        }
    }

    @Override
    protected boolean particleActive(Particle p) {
        float xAbs = Math.abs(p.x);
        float yAbs = Math.abs(p.y);
        return xAbs * xAbs + yAbs * yAbs < 1000;
    }
    
}