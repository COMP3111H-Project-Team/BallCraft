package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.graphics.Drawable;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public abstract class Unit implements Drawable {

	protected Body body;
	private final static Vec2 O = new Vec2(0, 0);
	
	private ServerGameState gameState = ServerGameState.getStateInstance();

	public static Unit fromSerializedString(String serialized) {
		String [] parts = serialized.split(":");
		if (parts[0].equals("ball")) {
			String [] vals = parts[1].split(",");
			float x = Float.valueOf(vals[0]);
			float y = Float.valueOf(vals[1]);
			float radius = Float.valueOf(vals[2]);
			return new Ball(radius, 0, 0, new Vec2(x, y));
		} else if (parts[0].equals("wall")) {
			// TODO
		}
		return null;
	}
	
	public Unit() {		
		gameState.addUnit(this);
	}
	
	public void applyForce(Vec2 force) {
		body.applyForce(force, O);
	}
	
	public abstract String toSerializedString();
	
}