package hkust.comp3111h.ballcraft;

import android.util.Log;

import com.threed.jpct.Object3D;
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
		this.setCollisionMode(Object3D.COLLISION_CHECK_SELF);
		this.mass = mass;
		this.u = u;
		velocity = new SimpleVector(0f, 0f, 0f);
		acceleration = new SimpleVector(0f, 0f, 0f);
	}

	@Override
	public void move(int msecElapsed) 
	{
		float rate = (float)msecElapsed / 30;
		SimpleVector addition = new SimpleVector(acceleration);
		addition.scalarMul(rate);
		velocity.add(addition);
		SimpleVector displacement = new SimpleVector(velocity); 
		displacement.scalarMul(rate);
		this.translate(checkForCollisionSpherical(displacement, 10)); //TODO: why getscale doesn't work?
		
		if (velocity.length() != 0f && u * rate > velocity.length())
		{
			SimpleVector friction = new SimpleVector(velocity);
			friction.scalarMul(-u * rate / velocity.length());		
			velocity.add(friction);	
		}
		else
		{			
			velocity.set(0f, 0f , 0f);
		}
	}
	
	public void setAcceleration(SimpleVector acceleration)
	{
		this.acceleration = acceleration;
	}
}

