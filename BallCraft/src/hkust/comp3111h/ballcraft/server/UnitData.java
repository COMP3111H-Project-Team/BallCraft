package hkust.comp3111h.ballcraft.server;

import java.io.Serializable;

import hkust.comp3111h.ballcraft.server.Unit.type;

public class UnitData implements Serializable {
	
	public Vector2f position;
	public Vector2f velocity;
	public float size;
	public type identity;
	
	public UnitData(Vector2f position, Vector2f velocity, float size, type identity)
	{
		this.position = position;
		this.velocity = velocity;
		this.size = size;
		this.identity = identity;
	}
}
