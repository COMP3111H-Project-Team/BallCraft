package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.skills.TestSkill1;
import hkust.comp3111h.ballcraft.skills.TestSkill2;

/**
 * Define a skill to be casted
 */
public abstract class Skill {
	
	protected int id;
	protected int player;
	private long time;
	protected long duration;
	
	public static Skill getSkill(int id)
	{
		return getSkill(BallCraft.myself, id);		
	}
	
	public static Skill getSkill(int player, int id)
	{
		switch (id)
		{
		case BallCraft.Skill.TEST_SKILL_1:
			return new TestSkill1(player, id);
		case BallCraft.Skill.TEST_SKILL_2:
			return new TestSkill2(player, id);
		}
		return null;
	}
	
	public void setTime()
	{
		time = System.currentTimeMillis();
		init();
	}
	
	
	public int getPlayer()
	{
		return player;
	}
	
	public abstract void init();
	public abstract void beforeStep();
	public abstract void afterStep();
	
	public int getId()
	{
		return id;
	}
	
	public boolean isActive() 
	{
		return System.currentTimeMillis() - time < duration && id != BallCraft.Skill.DEACTIVATED;
	}
	
	public void deactivate()
	{
		id = BallCraft.Skill.DEACTIVATED;
	}
	
	public String toSerializedString() {
		return String.valueOf(id);
	}
	
}
