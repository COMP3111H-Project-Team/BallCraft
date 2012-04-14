package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.graphics.Drawable;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import android.util.Log;

public abstract class Unit implements Drawable {

    public final static float rate = 3.0f;
    public final static float g = 9.8f;
    protected Body body;
	protected Status status;
	protected int id;
    public final static Vec2 O = new Vec2(0, 0);

    public static Unit fromSerializedString(String serialized) {
        String[] parts = serialized.split(":");
        if (parts[0].equals("ball")) {
            String[] vals = parts[1].split(",");
            float x = Float.valueOf(vals[0]);
            float y = Float.valueOf(vals[1]);
            float radius = Float.valueOf(vals[2]);
            float z = Float.valueOf(vals[3]);
            return new Ball(radius, new Vec2(x, y), z);
        } else if (parts[0].equals("wall")) {
            String[] vals = parts[1].split(",");
            float x1 = Float.valueOf(vals[0]);
            float y1 = Float.valueOf(vals[1]);
            float x2 = Float.valueOf(vals[2]);
            float y2 = Float.valueOf(vals[3]);
            return new Wall(new Vec2(x1, y1), new Vec2(x2, y2));
        } else if (parts.equals("plane")) {
            String[] vals = parts[1].split(",");
            float x = Float.valueOf(vals[0]);
            float y = Float.valueOf(vals[1]);
            float xScale = Float.valueOf(vals[2]);
            float yScale = Float.valueOf(vals[3]);
            return new Plane(new Vec2(x, y), xScale, yScale);
        }
        return null;
    }

    public Unit() {
		status = Status.NORMAL;
		id = ServerGameState.getStateInstance().getUnits().size();
    }

    public void applyForce(Vec2 force) {
        body.applyForce(force.mul(1.0f / rate), O);
        // Log.e("position:", body.getPosition().x + " , " +
        // body.getPosition().y);
    }

    public Vec2 getPosition() {
        return new Vec2(body.getPosition().x, body.getPosition().y);
    }

    public abstract String toSerializedString();

    public abstract void updateFromString(String string);

    public Body getBody() {
        return body;
    }

	public void setStatus(Status status) 
	{
		this.status = status;
	}

	public Status getStatus()
	{
		return status;
	}

}