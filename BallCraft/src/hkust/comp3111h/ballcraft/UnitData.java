package hkust.comp3111h.ballcraft;

import hkust.comp3111h.ballcraft.Unit.type;

public class UnitData
{
	public Vector2f position;
	public float size;
	public type identity;
	
	public UnitData(Vector2f position, float size, type identity)
	{
		this.position = position;
		this.size = size;
		this.identity = identity;
	}
}
