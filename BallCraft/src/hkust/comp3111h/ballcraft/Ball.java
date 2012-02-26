package hkust.comp3111h.ballcraft;

import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

public class Ball extends Unit
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float u; // miu
	
	public Ball(float size, float mass, float u) 
	{
		super(size, mass);
		this.setCollisionMode(Object3D.COLLISION_CHECK_SELF);
		this.u = u * 5;
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
		
		velocity.scalarMul(0.8f);
		
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

