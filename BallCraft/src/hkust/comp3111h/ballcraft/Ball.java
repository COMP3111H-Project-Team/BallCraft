package hkust.comp3111h.ballcraft;

import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

public class Ball extends Unit
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Ball(float size, float mass, float friction) 
	{
		super(size, mass);
		this.setCollisionMode(Object3D.COLLISION_CHECK_SELF);
		this.friction = friction;
		this.identity = type.BALL;
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
		this.translate(checkCollision(displacement));
		
		//Log.e("x,y", "" + this.getTransformedCenter().x + "  " + this.getTransformedCenter().y);		
		
		velocity.scalarMul(friction);
		
//		if (velocity.length() != 0f && u * rate < velocity.length())
//		{
//			SimpleVector friction = new SimpleVector(velocity);
//			friction.scalarMul(-u * rate / velocity.length());		
//			velocity.add(friction);	
//		}
//		else
//		{	
//			velocity.set(0f, 0f , 0f);
//		}
	}
}

