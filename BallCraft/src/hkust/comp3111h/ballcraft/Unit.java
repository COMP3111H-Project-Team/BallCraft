package hkust.comp3111h.ballcraft;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;

public abstract class Unit extends Object3D
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int maxRecursion = 5;
	
	private static int maxX;
	private static int maxY;
	private static int minX;
	private static int minY;
	
	private float mass;
	protected SimpleVector velocity;
	protected SimpleVector acceleration;
	
	public static ArrayList<Unit> units = new ArrayList<Unit>();
	
	
	static public void setWorld(int maxX, int minX, int maxY, int minY)
	{
		Unit.maxX = maxX;
		Unit.minX = minX;
		Unit.maxY = maxY;
		Unit.minY = minY;
	}
	
	public Unit(float size, float mass)
	{
		super(Primitives.getSphere(1f));
		this.scale(size);
		this.mass = mass;
		velocity = new SimpleVector(0f, 0f, 0f);
		acceleration = new SimpleVector(0f, 0f, 0f);
		units.add(this);
	}
	
	public void move(int msecElapsed)
	{
		
	}
	
	protected SimpleVector checkCollision(SimpleVector displacement)
	{
		return collisionRecursion(displacement, 1);
	}
	
	private SimpleVector collisionRecursion(SimpleVector displacement, int recursion)
	{
		if (recursion == maxRecursion) 
		{
			Log.e("collision", "Max recursion reached");
			return new SimpleVector(0, 0, 0);		
		}
		
		SimpleVector dest = getTransformedCenter();
		dest.add(displacement);
		
		Unit temp;
		
		float scale = getScale();
		
		//collsion with other units
    	Iterator<Unit> i = Unit.units.iterator();
    	while(i.hasNext())
    	{
    	    temp = i.next();
    	    if (temp == this)
    	    {
    	    	// a object do not have collision with itself
    	    	continue;
    	    }
    	    
    	    if (dest.distance(temp.getTransformedCenter()) < scale + temp.getScale())
    	    {
    	    	float vx = (velocity.x * (mass - temp.mass) + 2 * temp.mass * temp.velocity.x) / (mass + temp.mass);
    	    	float vy = (velocity.y * (mass - temp.mass) + 2 * temp.mass * temp.velocity.y) / (mass + temp.mass);
    	    	temp.velocity.x = (temp.velocity.x * (temp.mass - mass) + 2 * mass * velocity.x) / (mass + temp.mass);
    	    	temp.velocity.y = (temp.velocity.y * (temp.mass - mass) + 2 * mass * velocity.y) / (mass + temp.mass);
    	    	velocity.x = vx; 
    	    	velocity.y = vy;
    	    	//approximation
        		return collisionRecursion(velocity, recursion + 1);
    	    }
    	} 
    	
    	//collision with walls
    	//TODO: reflection
    	if (dest.x - scale < minX || dest.x + scale> maxX)
    	{
    		acceleration.z = 10;
    	}
    	else if (dest.y - scale< minY || dest.y + scale> maxY)
    	{
    		acceleration.z = 10;
    	}
    	
    	// no collision found
    	return displacement;
	}
	
	public void setAcceleration(SimpleVector acceleration)
	{
		this.acceleration = acceleration;
	}	
	
}