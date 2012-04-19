package hkust.comp3111h.ballcraft.graphics.skilleffects;

import hkust.comp3111h.ballcraft.graphics.Drawable;
import hkust.comp3111h.ballcraft.server.Ball;

public abstract class SkillEffect implements Drawable {

    protected Ball ballEffected;
    
    protected long initTime; 
    protected long effectTime = -1; // normally no time limit
    
    protected float x;
    protected float y;
    protected float z;
    
    public SkillEffect() {
        this.initTime = System.currentTimeMillis();
    }
    
    public SkillEffect(Ball ballEffected) {
        this.ballEffected = ballEffected;
        this.x = this.ballEffected.getPosition().x;
        this.y = this.ballEffected.getPosition().y;
    }
    
    public void setBallEffected(Ball b) {
        this.ballEffected = b;
    }
    
    public Ball getBallEffected() {
        return this.ballEffected;
    }
    
    public void setEffectTime(long t) {
        this.effectTime = t;
    }
    
    public boolean timeout() {
        if (effectTime == -1) { // no time limit
            return false;
        }
        return (System.currentTimeMillis() - this.initTime > this.effectTime);
    }
    
    public abstract void move();
    
}
