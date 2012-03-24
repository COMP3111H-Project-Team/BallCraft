package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallCraft;

/**
 * Define a skill to be casted
 */
public class Skill {
	
	private int id;
	
	public Skill(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public boolean isActive() {
		return id != BallCraft.Skill.DEACTIVATED;
	}
	
	public void deactivate() {
		id = BallCraft.Skill.DEACTIVATED;
	}
	
	public String toSerializedString() {
		return String.valueOf(id);
	}
	
}
