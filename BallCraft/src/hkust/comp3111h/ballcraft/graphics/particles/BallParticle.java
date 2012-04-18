package hkust.comp3111h.ballcraft.graphics.particles;

import java.util.Random;

/**
 * The super class of all particles of ball
 */
public abstract class BallParticle extends Particle {
    
    protected double theta;
    protected double rho;
    protected double r;
    
    // the rate of change
    protected double dTheta;
    protected double dRho;
    
    protected Random randGen;

    public BallParticle(double theta, double rho, double r) {
        super(
                (float) (r * Math.cos(rho) * Math.sin(theta)), 
                (float) (r * Math.sin(rho) * Math.sin(theta)), 
                (float) (r * Math.cos(theta))
        );
        this.theta = theta;
        this.rho = rho;
        this.r = r;
        
        this.gravityInfluence = false;
    }
    
    @Override
    public void move() {
        this.theta += this.dTheta;
        this.rho += this.dRho;
        this.x = (float) (r * Math.cos(rho) * Math.sin(theta));
        this.y = (float) (r * Math.sin(rho) * Math.sin(theta));
        this.z = (float) (r * Math.cos(theta));
    }
    
}
