package hkusk.comp3111h.ballcraft;

import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;

public abstract class Unit extends Object3D
{

	
	public Unit(float size)
	{
		super(Primitives.getSphere(size));
	}
	
	public void move(int msecElapsed)
	{
		
	}
}