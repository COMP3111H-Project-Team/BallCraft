package hkust.comp3111h.ballcraft.client;


import java.util.ArrayList;

import org.jbox2d.common.Vec2;


public class GameInput {
	
	public Vec2 acceleration;
	
	private ArrayList<Skill> activeSkills = null;
	
	public GameInput() {
		acceleration = new Vec2();
		activeSkills = new ArrayList<Skill>();
	}
	
	public ArrayList<Skill> getSkills() {
		return activeSkills;
	}
	
	/**
	 * True if the input contains active skills
	 * @return True if the input contains active skills
	 */
	public boolean skillActive() {
		return !activeSkills.isEmpty();
	}
	
	public void addSkill(Skill skill) {
		activeSkills.add(skill);
	}
	
	public void clearSkills() {
		activeSkills.clear();
	}
	
	public String toSerializedString() {
		String serialized = "";
		serialized += acceleration.x + "," + acceleration.y;
		if (!activeSkills.isEmpty()) {
			serialized += "/";
			for (int i = 0; i < activeSkills.size(); i++) {
				serialized += activeSkills.get(i).toSerializedString();
				if (i != activeSkills.size() - 1) {
					serialized += ",";
				}
			}
		}
		return serialized;
	}
	
	public static GameInput fromSerializedString(String serialized) {
		GameInput input = new GameInput();
		
		String [] parts = serialized.split("/");
		
		String [] accVals = parts[0].split(",");
		input.acceleration.x = Float.valueOf(accVals[0]);
		input.acceleration.y = Float.valueOf(accVals[1]);
		
		if (parts.length != 1) {
			String [] skillVals = parts[1].split(",");
			for (int i = 0; i < skillVals.length; i++) {
				input.addSkill(new Skill(Integer.valueOf(skillVals[i])));
			}
		}
		
		return input;
	}
	
}
