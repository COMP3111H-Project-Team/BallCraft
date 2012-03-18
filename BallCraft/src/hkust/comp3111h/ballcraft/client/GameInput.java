package hkust.comp3111h.ballcraft.client;


import java.util.ArrayList;

import org.jbox2d.common.Vec2;


public class GameInput {
	
	public Vec2 acceleration;
	
	private ArrayList<Skill> activeSkills = null;
	
	public GameInput()
	{
		acceleration = new Vec2();
		activeSkills = new ArrayList<Skill>();
	}
	
	public String serialize() {
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
	
	public String toSerializedString() {
		String serialized = "";
		serialized += acceleration.x + "," + acceleration.y;
		return serialized;
	}
	
	public static GameInput deserializeGameInput(String serialized) {
		GameInput input = new GameInput();
		String [] vals = serialized.split(",");
		input.acceleration.x = Float.valueOf(vals[0]);
		input.acceleration.y = Float.valueOf(vals[1]);
		return input;
	}
}
