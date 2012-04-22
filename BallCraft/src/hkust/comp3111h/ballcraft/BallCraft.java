package hkust.comp3111h.ballcraft;

/**
 * This class includes the constants and definitions of the game
 */
public class BallCraft {

    public static int myself = 0;
    public static int enemy = 1;
    public static int maxPlayer = 1;
    
    public static boolean isServer = true;

    public enum Status {
		NORMAL, DEAD, FROZEN, DIZZY
	}
    
    public static final class Skill {
        public static final int DEACTIVATED = 0;
        public static final int TEST_SKILL_1 = 1;
        public static final int TEST_SKILL_2 = 2;
        public static final int MINE = 3;
        public static final int MASSOVERLOAD = 4;
        public static final int PROPEL = 5;
        public static final int BUMP = 6;
    }
    
    public static final class Ball {
        public static final int WOOD_BALL = 0;
        public static final int ROCK_BALL = 1;
        public static final int WATER_BALL = 2;
        public static final int IRON_BALL = 3;
        public static final int FIRE_BALL = 4;
        public static final int DARK_BALL = 5;
    }
    
    public static final class Terrain {
        public static final int TABLE_TERRAIN = 0;
        public static final int FOREST_TERRAIN = 1;
        public static final int STONE_TERRAIN = 2;
        public static final int OCEAN_TERRAIN = 3;
        public static final int METAL_TERRAIN = 4;
        public static final int FIRELAND_TERRAIN = 5;
    }
    
    public static final class MapMode {
        public static final int DAY_MODE = 0;
        public static final int NIGHT_MODE = 1;
    }

}