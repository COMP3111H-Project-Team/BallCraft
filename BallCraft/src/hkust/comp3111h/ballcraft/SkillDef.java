package hkust.comp3111h.ballcraft;

public class SkillDef {
    
	public static final class GrowRoot {
	    public static final int id = BallCraft.Skill.GROW_ROOT;
	    public static final String name = "Grow Root";
	    public static final int effectTime = 5000;
	    public static final int coolDownTime = 20000;
	}
	
	public static final class NaturesCure {
	    public static final int id = BallCraft.Skill.NATURES_CURE;
	    public static final String name = "Nature's Cure";
	    public static final int effectTime = 0;
	    public static final int coolDownTime = 30000;
	}
	
	public static final class MassOverlord {
	    public static final int id = BallCraft.Skill.MASS_OVERLORD;
	    public static final String name = "Mass Overlord";
	    public static final int effectTime = 10000;
	    public static final int coolDownTime = 30000;
	}
	
	public static final class RockBump {
	    public static final int id = BallCraft.Skill.ROCK_BUMP;
	    public static final String name = "Rock Bump";
	    public static final int effectTime = 8000;
	    public static final int coolDownTime = 20000;
	}
	
	public static final class WaterPropel {
	    public static final int id = BallCraft.Skill.WATER_PROPEL;
	    public static final String name = "Water Propel";
	    public static final int effectTime = 5000;
	    public static final int coolDownTime = 20000;
	}
	
	public static final class Slippery {
	    public static final int id = BallCraft.Skill.SLIPPERY;
	    public static final String name = "Slippery";
	    public static final int effectTime = 15000;
	    public static final int coolDownTime = 45000;
	}
	
	public static final class IronWill {
	    public static final int id = BallCraft.Skill.IRON_WILL;
	    public static final String name = "Iron Will";
	    public static final int effectTime = 15000;
	    public static final int coolDownTime = 30000;
	}
	
	public static final class Crush {
	    public static final int id = BallCraft.Skill.CRUSH;
	    public static final String name = "Crush";
	    public static final int effectTime = 5000;
	    public static final int coolDownTime = 30000;
	}
	
	public static final class FlameThrow {
	    public static final int id = BallCraft.Skill.FLAME_THROW;
	    public static final String name = "Flame Throw";
	    public static final int effectTime = 8000;
	    public static final int coolDownTime = 30000;
	}
	
	public static final class Landmine {
	    public static final int id = BallCraft.Skill.LANDMINE;
	    public static final String name = "Landmine";
	    public static final int effectTime = -1;
	    public static final int coolDownTime = -1;
	}
	
	public static final class Stealth {
	    public static final int id = BallCraft.Skill.STEALTH;
	    public static final String name = "Stealth";
	    public static final int effectTime = 10000;
	    public static final int coolDownTime = 50000;
	}
	
	public static final class MidNight {
	    public static final int id = BallCraft.Skill.MIDNIGHT;
	    public static final String name = "Midnight";
	    public static final int effectTime = 20000;
	    public static final int coolDownTime = 80000;
	}
		
    @SuppressWarnings("rawtypes")
    public static final Class [] skills = {
        GrowRoot.class,
        NaturesCure.class,
        MassOverlord.class,
        RockBump.class,
        WaterPropel.class,
        Slippery.class,
        IronWill.class,
        Crush.class,
        FlameThrow.class,
        Landmine.class,
        Stealth.class,
        MidNight.class,
    };
    
	public static String getSkillNameById(int id) {
	    if (id < skills.length) {
	        try {
	            return (String) (skills[id].getField("name").get(null));
	        } catch (Exception e) {
	            return "None";
	        }
	    } else {
	        return "None";
	    }
	}
		
	public static int getEffectTimeById(int id) {
	    if (id < skills.length) {
	        try {
	            return skills[id].getField("effectTime").getInt(0);
	        } catch (Exception e) {
	            return 0;
	        }
	    } else {
	        return 0;
	    }
	}
		
	public static int getCoolDownTimeById(int id) {
	    if (id < skills.length) {
	        try {
	            return skills[id].getField("coolDownTime").getInt(0);
	        } catch (Exception e) {
	            return 0;
	        }
	    } else {
	        return 0;
	    }
	}
	
}
