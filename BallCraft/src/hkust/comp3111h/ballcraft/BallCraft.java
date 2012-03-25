package hkust.comp3111h.ballcraft;

/**
 * This class includes the constants and definitions of the game
 */
public class BallCraft {
	
	public static final class Ball {
		public static final int WOOD_BALL = 0;
		public static final int ROCK_BALL = 1;
		public static final int WATER_BALL = 2;
		public static final int IRON_BALL = 3;
		public static final int FIRE_BALL = 4;
		public static final int DARK_BALL = 5;
	}

	public static final Class [] balls = {
		WoodBall.class,
		RockBall.class,
		WaterBall.class,
		IronBall.class,
		FireBall.class,
		DarkBall.class,
	};
	
	public static final class Skill {
		public static final int DEACTIVATED = 0;
		public static final int TEST_SKILL_1 = 1;
		public static final int TEST_SKILL_2 = 2;
	}
	
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
				return (String)(balls[id].getField("name").get(null));
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public static String getBallDescriptionById(int id) {
		if (id < balls.length) {
			try {
				return (String)(balls[id].getField("description").get(null));
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
	
	/**
	 * Check whether a ball has been unlocked
	 * @param id The id of the ball
	 * @return True if the ball is unlocked
	 */
	public static boolean ballUnlocked(int id) {
		// TODO: link this with shared preferences
		return id < 3;
	}
	
	/**
	 * The super class of all the ball definitions, used to specific the static data of 
	 * different balls
	 */
	public static abstract class BallDef {
	}
	
	public static final class WoodBall extends BallDef {
		public static final int id = 0;
		public static final String name = "Wood Ball";
		public static final String description = 
				"The Wood Ball is made from the power of nature";
		public static final int imageResource = R.drawable.wood_ball;
		public static final int mass = 5;
		public static final int friction = 3;
		public static final int magic = 3;
	}
	
	public static final class RockBall extends BallDef {
		public static final int id = 1;
		public static final String name = "Rock Ball";
		public static final String description = 
				"This is the Rock Ball";
		public static final int imageResource = R.drawable.rock_ball;
		public static final int mass = 7;
		public static final int friction = 7;
		public static final int magic = 4;
	}
	
	public static final class WaterBall extends BallDef {
		public static final int id = 2;
		public static final String name = "Water Ball";
		public static final String description = 
				"This is the Water Ball";
		public static final int imageResource = R.drawable.water_ball;
		public static final int mass = 6;
		public static final int friction = 3;
		public static final int magic = 6;
	}
	
	public static final class IronBall extends BallDef {
		public static final int id = 3;
		public static final String name = "Iron Ball";
		public static final String description = 
				"This is the Iron Ball";
		public static final int imageResource = R.drawable.iron_ball;
		public static final int mass = 10;
		public static final int friction = 4;
		public static final int magic = 7;
	}
	
	public static final class FireBall extends BallDef {
		public static final int id = 4;
		public static final String name = "Fire Ball";
		public static final String description = 
				"This is the Fire Ball";
		public static final int imageResource = R.drawable.fire_ball;
		public static final int mass = 4;
		public static final int friction = 5;
		public static final int magic = 8;
	}
	
	public static final class DarkBall extends BallDef {
		public static final int id = 5;
		public static final String name = "Dark Ball";
		public static final String description = 
				"This is the Dark Ball";
		public static final int imageResource = R.drawable.dark_ball;
		public static final int mass = 4;
		public static final int friction = 4;
		public static final int magic = 10;
	}
	
} 