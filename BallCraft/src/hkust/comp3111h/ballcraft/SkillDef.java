package hkust.comp3111h.ballcraft;

public class SkillDef {
    
	public static final class GrowRoot {
	    public static final int id = BallCraft.Skill.GROW_ROOT;
	    public static final String name = "Grow Root";
	    public static final int effectTime = 5000;
	    public static final int coolDownTime = 20000;
	    public static final int [] RGB = {184, 134, 11};
	    public static final int button = R.drawable.skill_grow_root;
	}
	
	public static final class NaturesCure {
	    public static final int id = BallCraft.Skill.NATURES_CURE;
	    public static final String name = "Nature's Cure";
	    public static final int effectTime = 0;
	    public static final int coolDownTime = 30000;
	    public static final int [] RGB = {124, 255, 0};
	    public static final int button = R.drawable.skill_grow_root;
	}
	
	public static final class MassOverlord {
	    public static final int id = BallCraft.Skill.MASS_OVERLORD;
	    public static final String name = "Mass Overlord";
	    public static final int effectTime = 10000;
	    public static final int coolDownTime = 30000;
	    public static final int [] RGB = {139, 139, 122};
	    public static final int button = R.drawable.skill_mass_overlord;
	}
	
	public static final class RockBump {
	    public static final int id = BallCraft.Skill.ROCK_BUMP;
	    public static final String name = "Rock Bump";
	    public static final int effectTime = 8000;
	    public static final int coolDownTime = 20000;
	    public static final int [] RGB = {205, 186, 150};
	    public static final int button = R.drawable.skill_rock_bump;
	}
	
	public static final class WaterPropel {
	    public static final int id = BallCraft.Skill.WATER_PROPEL;
	    public static final String name = "Water Propel";
	    public static final int effectTime = 5000;
	    public static final int coolDownTime = 20000;
	    public static final int [] RGB = {0, 0, 205};
	    public static final int button = R.drawable.skill_water_propel;
	}
	
	public static final class Slippery {
	    public static final int id = BallCraft.Skill.SLIPPERY;
	    public static final String name = "Slippery";
	    public static final int effectTime = 15000;
	    public static final int coolDownTime = 45000;
	    public static final int [] RGB = {0, 191, 255};
	    public static final int button = R.drawable.skill_slippery;
	}
	
	public static final class IronWill {
	    public static final int id = BallCraft.Skill.IRON_WILL;
	    public static final String name = "Iron Will";
	    public static final int effectTime = 15000;
	    public static final int coolDownTime = 30000;
	    public static final int [] RGB = {211, 211, 211};
	    public static final int button = R.drawable.skill_iron_will;
	}
	
	public static final class FlashBang {
	    public static final int id = BallCraft.Skill.FLASHBANG;
	    public static final String name = "Flashbang";
	    public static final int effectTime = 5000;
	    public static final int coolDownTime = 30000;
	    public static final int [] RGB = {105, 105, 105};
	    public static final int button = R.drawable.skill_flashbang;
	}
	
	public static final class FlameThrow {
	    public static final int id = BallCraft.Skill.FLAME_THROW;
	    public static final String name = "Flame Throw";
	    public static final int effectTime = 8000;
	    public static final int coolDownTime = 30000;
	    public static final int [] RGB = {255, 69, 0};
	    public static final int button = R.drawable.skill_flame_throw;
	}
	
	public static final class Landmine {
	    public static final int id = BallCraft.Skill.LANDMINE;
	    public static final String name = "Landmine";
	    public static final int effectTime = -1;
	    public static final int coolDownTime = -1;
	    public static final int [] RGB = {139, 0, 0};
	    public static final int button = R.drawable.skill_landmine;
	}
	
	public static final class Stealth {
	    public static final int id = BallCraft.Skill.STEALTH;
	    public static final String name = "Stealth";
	    public static final int effectTime = 10000;
	    public static final int coolDownTime = 50000;
	    public static final int [] RGB = {125, 38, 205};
	    public static final int button = R.drawable.skill_stealth;
	}
	
	public static final class MidNight {
	    public static final int id = BallCraft.Skill.MIDNIGHT;
	    public static final String name = "Midnight";
	    public static final int effectTime = 20000;
	    public static final int coolDownTime = 80000;
	    public static final int [] RGB = {34, 34, 34};
	    public static final int button = R.drawable.skill_sunset;
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
        FlashBang.class,
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
	
	public static int [] getRGBById(int id) {
	    if (id < skills.length) {
	        try {
	            return (int []) skills[id].getField("RGB").get(0);
	        } catch (Exception e) {
	            return null;
	        }
	    } else {
	        return null;
	    }
	}
	
	public static int getButtonById(int id) {
	    if (id < skills.length) {
	        try {
	            return skills[id].getField("button").getInt(0);
	        } catch (Exception e) {
	            return 0;
	        }
	    } else {
	        return 0;
	    }
	}
	
}
