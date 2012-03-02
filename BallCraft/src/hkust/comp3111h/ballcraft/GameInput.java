package hkust.comp3111h.ballcraft;

import java.util.ArrayList;

import com.threed.jpct.SimpleVector;

public class GameInput 
{
	public SimpleVector acceleration;
	
	private ArrayList<Skill> skills;
	
	//ArrayList<int> keysPressed;
	public GameInput(SimpleVector acceleration)
	{
		this.acceleration = acceleration;
		skills = new ArrayList<Skill>();
	}
	
	public void addSkill(Skill skill) {
		skills.add(skill);
	}
	
	public ArrayList<Skill> getSkills() {
		return skills;
	}
	
	public void clearSkill() {
		skills.clear();
	}
}
