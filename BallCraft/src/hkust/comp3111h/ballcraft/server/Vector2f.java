package hkust.comp3111h.ballcraft.server;

import java.io.Serializable;

public class Vector2f implements Serializable {
	
	public float x;
	public float y;
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	public float length()
	{
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public void add(Vector2f v)
	{
		x += v.x;
		y += v.y;
	}
	
	public float distanceSqured(Vector2f v)
	{
		return (x - v.x) * (x - v.x) + (y - v.y)*(y - v.y);
	}

	public void scalarMul(float rate)
	{
		x *= rate;
		y *= rate;
	}
	
	public void addToXY(Vector2f vec) {
		if (vec.y != 0) {
			double angleTan = Math.atan(this.x / this.y);
			vec.x += Math.sin(angleTan) * vec.x;
			vec.y += Math.cos(angleTan) * vec.x;
			vec.x += Math.cos(angleTan) * vec.y;
			vec.y += Math.sin(angleTan) * vec.y;
		} else {
			vec.y = 0.1f;
		}
	}
	
}
