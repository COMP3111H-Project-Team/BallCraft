package hkusk.comp3111h.ballcraft;

import android.util.Log;

import com.threed.jpct.SimpleVector;

public class Ball extends Unit
{
	private SimpleVector velocity;
	private SimpleVector acceleration;
	private float mass;
	private float u; // miu
	
	public Ball(float size, float mass, float u) 
	{
		super(size);
		this.mass = mass;
		this.u = u;
		velocity = new SimpleVector(0f, 0f, 0f);
		acceleration = new SimpleVector(0f, 0f, 0f);
	}

	@Override
	public void move(int msecElapsed) 
	{
		float rate = msecElapsed / 36;
		SimpleVector addition = new SimpleVector(acceleration);
		addition.scalarMul(rate);
		velocity.add(addition);
		SimpleVector displacement = new SimpleVector(velocity); 
		displacement.scalarMul(rate);
		this.translate(0.5f, 0.3f, 0);
				
		SimpleVector friction = new SimpleVector(velocity);
		if (u * rate > velocity.length())
		{
			friction.scalarMul(-u * rate / velocity.length());			
		}
		else
		{
			friction.scalarMul(-1f);				
		}
		velocity.add(friction);
	}
	
	public void setAcceleration(SimpleVector acceleration)
	{
		this.acceleration = acceleration;
	}
}

