package hkust.comp3111h.ballcraft.skills;

public class MassOverload extends Skill
{
	private final static float rate = 1.3f;

	public MassOverload(int player, int id) 
	{
        this.player = player;
        this.id = id;
        this.duration = 10000;
	}
	
	@Override
	public void init() 
	{
		getBody().getFixtureList().m_shape.m_radius *= rate;
	}

	@Override
	public void beforeStep() {}

	@Override
	public void afterStep() {}

	@Override
	public void finish() 
	{
		getBody().getFixtureList().m_shape.m_radius /= rate;		
	}

}
