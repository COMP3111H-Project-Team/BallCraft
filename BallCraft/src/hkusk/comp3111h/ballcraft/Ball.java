package hkusk.comp3111h.ballcraft;

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
		// TODO Auto-generated method stub
		this.translate(1f, 1f, 0f);
	}
}

