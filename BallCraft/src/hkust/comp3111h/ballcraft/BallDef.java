package hkust.comp3111h.ballcraft;

import hkust.comp3111h.ballcraft.data.GameData;

/**
 * The super class of all the ball definitions, used to specific the static
 * data of different balls
 */
public class BallDef {

	public static final class WoodBall {
	    public static final int id = BallCraft.Ball.WOOD_BALL;
	    public static final String name = "Wood Ball";
	    public static final String description = "Making use of the power of nature";
	    public static final int imageResource = R.drawable.wood_ball;
	    public static final int mass = 5;
	    public static final int friction = 3;
	    public static final int magic = 3;
	    public static final int unlockExp = 0;
	}
	
	public static final class RockBall {
	    public static final int id = BallCraft.Ball.ROCK_BALL;
	    public static final String name = "Rock Ball";
	    public static final String description = "Hard and heavy";
	    public static final int imageResource = R.drawable.rock_ball;
	    public static final int mass = 7;
	    public static final int friction = 7;
	    public static final int magic = 4;
	    public static final int unlockExp = 50;
	}
	
	public static final class WaterBall {
	    public static final int id = BallCraft.Ball.WATER_BALL;
	    public static final String name = "Water Ball";
	    public static final String description = "Water is soft, but powerful";
	    public static final int imageResource = R.drawable.water_ball;
	    public static final int mass = 6;
	    public static final int friction = 3;
	    public static final int magic = 6;
	    public static final int unlockExp = 200;
	}
	
	public static final class IronBall {
	    public static final int id = BallCraft.Ball.IRON_BALL;
	    public static final String name = "Iron Ball";
	    public static final String description = "Hard to find its weakness";
	    public static final int imageResource = R.drawable.iron_ball;
	    public static final int mass = 10;
	    public static final int friction = 4;
	    public static final int magic = 7;
	    public static final int unlockExp = 600;
	}
	
	public static final class FireBall {
	    public static final int id = BallCraft.Ball.FIRE_BALL;
	    public static final String name = "Fire Ball";
	    public static final String description = "Burns everything to the ground";
	    public static final int imageResource = R.drawable.fire_ball;
	    public static final int mass = 4;
	    public static final int friction = 5;
	    public static final int magic = 8;
	    public static final int unlockExp = 1500;
	}
	
	public static final class DarkBall {
	    public static final int id = BallCraft.Ball.DARK_BALL;
	    public static final String name = "Dark Ball";
	    public static final String description = "At night, it is never visible";
	    public static final int imageResource = R.drawable.dark_ball;
	    public static final int mass = 4;
	    public static final int friction = 4;
	    public static final int magic = 10;
	    public static final int unlockExp = 3000;
	}
	
    @SuppressWarnings("rawtypes")
    public static final Class [] balls = { 
        WoodBall.class, 
        RockBall.class,
        WaterBall.class, 
        IronBall.class, 
        FireBall.class, 
        DarkBall.class, 
	};
	
	public static int getBallImageResourceById(int id) {
	    if (id < balls.length) {
	        try {
	            return balls[id].getField("imageResource").getInt(null);
	        } catch (Exception e) {
	            return 0;
	        }
	    } else {
	        return 0;
	    }
	}
	
	public static String getBallNameById(int id) {
	    if (id < balls.length) {
	        try {
	            return (String) (balls[id].getField("name").get(null));
	        } catch (Exception e) {
	            return "None";
	        }
	    } else {
	        return "None";
	    }
	}
	
	public static String getBallDescriptionById(int id) {
	    if (id < balls.length) {
	        try {
	            return (String) (balls[id].getField("description").get(null));
	        } catch (Exception e) {
	            return null;
	        }
	    } else {
	        return null;
	    }
	}
	
	public static int getBallMassById(int id) {
	    if (id < balls.length) {
	        try {
	            return balls[id].getField("mass").getInt(null);
	        } catch (Exception e) {
	            return 0;
	        }
	    } else {
	        return 0;
	    }
	}
	
	public static int getBallFrictionById(int id) {
	    if (id < balls.length) {
	        try {
	            return balls[id].getField("friction").getInt(0);
	        } catch (Exception e) {
	            return 0;
	        }
	    } else {
	        return 0;
	    }
	}
	
	public static int getBallMagicById(int id) {
	    if (id < balls.length) {
	        try {
	            return balls[id].getField("magic").getInt(0);
	        } catch (Exception e) {
	            return 0;
	        }
	    } else {
	        return 0;
	    }
	}
	
	public static int getBallUnlockExpById(int id) {
	    if (id < balls.length) {
	        try {
	            return balls[id].getField("unlockExp").getInt(0);
	        } catch(Exception e) {
	            return 0;
	        }
	    } else {
	        return 0;
	    }
	}
	
	/**
	 * Check whether a ball has been unlocked
	 * @param id The id of the ball
	 * @return True if the ball is unlocked
	 */
	public static boolean ballUnlocked(int id) {
	    return GameData.getExperience() >= getBallUnlockExpById(id);
	}
	
}