package hkust.comp3111h.ballcraft.graphics;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

class ParticleSystem1 extends ParticleSystem {
    
    private int COUNT = 100;
    
    private ArrayList<NewParticle> particles;

    public ParticleSystem1(float x, float y, float z) {
        particles = new ArrayList<NewParticle>();
        Random randGen = new Random();
        
        for (int i = 0; i < COUNT; i++) {
            particles.add(new NewParticle(0, 0, 10, 
                    randGen.nextFloat() * 4 - 2, randGen.nextFloat() * 4 - 2, 8));
        }
    }

    @Override
    public void move() {
        for (int i = 0; i < COUNT; i++) {
            particles.get(i).move();
        }
    }

    @Override
    public void draw(GL10 gl) {
        for (int i = 0; i < COUNT; i++) {
            particles.get(i).draw(gl);
        }
    }
}