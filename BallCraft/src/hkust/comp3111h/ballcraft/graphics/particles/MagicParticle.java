package hkust.comp3111h.ballcraft.graphics.particles;


public abstract class MagicParticle extends Particle {

    public MagicParticle(float x, float y, float z) {
        super(x, y, z);
    }
    
    @Override
    public void move() {
        if (this.gravityInfluence) {
            this.zSpeed += gravity;
        }
        this.x += this.xSpeed;
        this.y += this.ySpeed;
        this.z += this.zSpeed;
    }
    
}
