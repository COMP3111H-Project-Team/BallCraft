package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.Skill;
import hkust.comp3111h.ballcraft.server.Vector2f;


public class GameInput 
{
	public Vector2f acceleration;
	private Skill currSkill = new Skill(0);
	GameInput(Vector2f acceleration)
	{
		this.acceleration = acceleration;
		// skills = new ArrayList<Skill>();
	}
	
	public void setSkill(int skillId) {
		currSkill = new Skill(skillId);
	}
	
	public Skill getSkill() {
		return currSkill;
	}

	/*
	public void addSkill(Skill skill) {
		skills.add(skill);
	}
	
	public ArrayList<Skill> getSkills() {
		return skills;
	}
	
	public void clearSkill() {
		skills.clear();
	}
	*/
}
