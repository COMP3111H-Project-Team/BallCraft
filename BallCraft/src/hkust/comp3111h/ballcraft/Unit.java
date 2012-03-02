package hkust.comp3111h.ballcraft;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

public abstract class Unit
{
	
	public enum type {BALL}

    public static int msecElapsed;
	private static int maxRecursion = 5;
	
	private static int maxX;
	private static int maxY;
	private static int minX;
	private static int minY;
	
	protected UnitData unitdata;
	protected float mass;	
	protected float friction;
	protected Vector2f velocity;
	protected Vector2f acceleration;
	
	protected int leanX;
	protected int leanY;
	
	public boolean moved;
	
	public static ArrayList<Unit> units = new ArrayList<Unit>();
	public static List<UnitData> data = new ArrayList<UnitData>();
	
	
	static public void setWorld(int maxX, int minX, int maxY, int minY)
	{
		Unit.maxX = maxX;
		Unit.minX = minX;
		Unit.maxY = maxY;
		Unit.minY = minY;
	}
	
	public Unit(float size, float mass, Vector2f position, type identity)
	{
		unitdata = new UnitData(position, size, identity);
		this.mass = mass;
		velocity = new Vector2f(0f, 0f);
		acceleration = new Vector2f(0f, 0f);
		leanX = 0;
		leanY = 0;
		units.add(this);
		data.add(unitdata);
	}
	
	public void move()
	{
		
	}
	
	protected Vector2f checkCollision(Vector2f displacement)
	{
		return collisionRecursion(displacement, 1);
	}
	
	private Vector2f collisionRecursion(Vector2f displacement, int recursion)
	{
		if (recursion == maxRecursion) 
		{
			Log.e("collision", "Max recursion reached");
			return new Vector2f(0, 0);		
		}
		
		Vector2f dest = new Vector2f(unitdata.position);
		dest.add(displacement);
		
		Unit temp;
				
		//collsion with other units
    	Iterator<Unit> i = Unit.units.iterator();
    	while(i.hasNext())
    	{
    	    temp = i.next();
    	    if (temp.moved || temp == this)
    	    {
    	    	// a object do not have collision with itself
    	    	continue;
    	    }
    	    
    	    if (dest.distanceSqured(temp.unitdata.position) < (unitdata.size + temp.unitdata.size) * (unitdata.size + temp.unitdata.size))
    	    {
    	    	float vx = (velocity.x * (mass - temp.mass) + 2 * temp.mass * temp.velocity.x) / (mass + temp.mass);
    	    	float vy = (velocity.y * (mass - temp.mass) + 2 * temp.mass * temp.velocity.y) / (mass + temp.mass);
    	    	temp.velocity.x = (temp.velocity.x * (temp.mass - mass) + 2 * mass * velocity.x) / (mass + temp.mass);
    	    	temp.velocity.y = (temp.velocity.y * (temp.mass - mass) + 2 * mass * velocity.y) / (mass + temp.mass);
    	    	velocity.x = vx; 
    	    	velocity.y = vy;
        	    temp.moved = true;
    	    	temp.move();
    	    	Vector2f result = new Vector2f(velocity);
    	    	result.scalarMul(msecElapsed/30);
        		return collisionRecursion(result, recursion + 1);
    	    }
    	} 
    	
    	//collision with walls
    	if (dest.x - unitdata.size < minX || dest.x + unitdata.size > maxX)
    	{
    		if (unitdata.identity == type.BALL && acceleration.x * velocity.x > 0f)
    		{
    			leanX = velocity.x > 0 ? 1 : -1;
    			velocity.x = 0;
    			if (dest.x - unitdata.size < minX) unitdata.position.x = minX + unitdata.size;
    			else unitdata.position.x = maxX - unitdata.size;
    		}
    		else 
    		{
        		velocity.x = -velocity.x * 0.55f;
    		}
	    	Vector2f result = new Vector2f(velocity);
	    	result.x = 0;
	    	result.scalarMul(msecElapsed/30);
    		return collisionRecursion(result, recursion + 1);
    	}
    	else if (dest.y - unitdata.size < minY || dest.y + unitdata.size > maxY)
    	{
    		if (unitdata.identity == type.BALL && acceleration.y * velocity.y > 0f)
    		{
    			leanY = velocity.y > 0 ? 1 : -1;
    			velocity.y = 0;
    			if (dest.y - unitdata.size < minY) unitdata.position.y = minY + unitdata.size;
    			else unitdata.position.y = maxY - unitdata.size;
    		}
    		else 
    		{
        		velocity.y = -velocity.y * 0.75f;
    		}
	    	Vector2f result = new Vector2f(velocity);
	    	result.scalarMul(msecElapsed/30);
    		return collisionRecursion(result, recursion + 1);
    	}
    	
    	// no collision found
    	return displacement;
	}
	
	public void setAcceleration(Vector2f acceleration)
	{
		this.acceleration = acceleration;
		
		if (leanX * acceleration.x > 0) acceleration.x = 0;
		else if (acceleration.x != 0) leanX = 0;
		
		if (leanY * acceleration.y > 0) acceleration.y = 0;
		else if (acceleration.y != 0) leanY = 0;
	}	
	
	public Vector2f getPosition()
	{
		return unitdata.position;
	}
	
}