package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.server.Unit.type;

import java.io.Serializable;

import org.jbox2d.common.Vec2;

public class UnitData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8882818284804032491L;
	
	public Vec2 position;
	public float size;
	public type identity;
	
	public UnitData(Vec2 position, float size, type identity)
	{
		this.position = position;
		this.size = size;
		this.identity = identity;
	}
}
