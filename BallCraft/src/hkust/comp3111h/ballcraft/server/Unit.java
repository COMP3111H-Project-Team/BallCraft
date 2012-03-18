package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.graphics.Drawable;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public abstract class Unit implements Drawable {

	protected Body body;
	private final static Vec2 O = new Vec2(0, 0);
	
	private ServerGameState gameState = ServerGameState.getStateInstance();
		
	public Unit() {		
		gameState.addUnit(this);
	}
	
	public void applyForce(Vec2 force) {
		body.applyForce(force, O);
	}
	
}