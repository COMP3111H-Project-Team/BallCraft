package hkust.comp3111h.ballcraft;

import java.io.Serializable;

import hkust.comp3111h.ballcraft.Unit.type;

public class UnitData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5785133248221806623L;
	
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
