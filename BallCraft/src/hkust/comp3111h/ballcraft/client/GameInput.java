package hkust.comp3111h.ballcraft.client;


import hkust.comp3111h.ballcraft.server.Vector2f;

import java.util.ArrayList;


public class GameInput {
	
	public Vector2f acceleration;
	
	private ArrayList<Skill> activeSkills = null;
	
	GameInput(Vector2f acceleration)
	{
		this.acceleration = acceleration;
		activeSkills = new ArrayList<Skill>();
	}
	
	public String serialize() {
		return null;
	}
	
	public static GameInput decodeSerializedInput(String serialized) {
		return null;
	}
	
	public ArrayList<Skill> getSkills() {
		return activeSkills;
	}
	
	public void addSkill(Skill skill) {
		activeSkills.add(skill);
	}
	
	public void clearSkill() {
		activeSkills.clear();
	}
	
}
