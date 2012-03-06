package hkust.comp3111h.ballcraft.server;



public class Ball extends Unit
{

	public Ball(float size, float mass, float friction, Vector2f position) 
	{
		super(size, mass, position, type.BALL);
		this.friction = friction;
	}

	@Override
	public void move() 
	{
		float rate = (float)msecElapsed / 30;
		Vector2f addition = new Vector2f(acceleration);
		addition.scalarMul(rate);
		
		/*
		double tanAngle = Math.atan(velocity.x / velocity.y);
		velocity.x += (-addition.y) * Math.sin(tanAngle);
		velocity.y += (-addition.y) * Math.cos(tanAngle);
		*/
		
		// velocity.add(addition);
		velocity.add(addition);
		Vector2f displacement = new Vector2f(velocity); 
		displacement.scalarMul(rate);
		unitdata.position.add(checkCollision(displacement));
		
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

