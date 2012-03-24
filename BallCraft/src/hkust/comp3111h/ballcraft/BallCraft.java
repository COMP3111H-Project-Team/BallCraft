package hkust.comp3111h.ballcraft;

/**
 * This class includes the constants and definitions of the game
 * 
 * @author guanlun
 */
public class BallCraft {
	
	public static final class Ball {
		public static final int WOOD_BALL = 1;
		public static final int ROCK_BALL = 2;
		public static final int WATER_BALL = 3;
		public static final int IRON_BALL = 4;
		public static final int FIRE_BALL = 5;
		public static final int DARK_BALL = 6;
	}
	
	public static final String [] ballNames = {
		"Wood Ball",
		"Rock Ball",
		"Water Ball",
		"Iron Ball",
		"Fire Ball", 
		"Dark Ball",
	};
	
	public static final int [] ballImageResources = {
		R.drawable.wood_ball,
		R.drawable.hk_ball,
		R.drawable.table_ball,
		R.drawable.splash_us,
		R.drawable.title,
		R.drawable.icon,
	};
	
	public static final int numOfBalls = 6;
	
	public static final class Skill {
		public static final int DEACTIVATED = 0;
		public static final int TEST_SKILL_1 = 1;
		public static final int TEST_SKILL_2 = 2;
	}
	
	public static int getBallImageResourceById(int id) {
		if (id < numOfBalls) {
			return ballImageResources[id];
		} else {
			return 0;
		}
	}
	
	public static String getBallNameById(int id) {
		if (id < numOfBalls) {
			return ballNames[id];
		} else {
			return null;
		}
	}
	
	/**
	 * Check whether a ball has been unlocked
	 * @param id The id of the ball
	 * @return True if the ball is unlocked
	 */
	public static boolean ballUnlocked(int id) {
		// TODO: link this with shared preferences
		return id < 2;
	}
	
}
