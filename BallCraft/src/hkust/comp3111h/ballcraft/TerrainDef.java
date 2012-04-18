package hkust.comp3111h.ballcraft;

public class TerrainDef {
    
    public static final class TableTerrain {
        public static final int id = BallCraft.Terrain.TABLE_TERRAIN;
        public static final String name = "Table";
        public static final String description = "The most common terrain";
        public static final int floorTexture = R.drawable.forest_floor;
        public static final int wallTexture = R.drawable.forest_floor;
        public static final int friction = 5;
        public static final int benefitBall = -1;
    }
    
    public static final class ForestTerrain {
        public static final int id = BallCraft.Terrain.FOREST_TERRAIN;
        public static final String name = "Forest";
        public static final String description = "Beneficial to the Wood Ball";
        public static final int floorTexture = R.drawable.forest_floor;
        public static final int wallTexture = R.drawable.forest_wall;
        public static final int friction = 7;
        public static final int benefitBall = BallCraft.Ball.WOOD_BALL;
    }
    
    public static final class StoneTerrain {
        public static final int id = BallCraft.Terrain.STONE_TERRAIN;
        public static final String name = "Stone";
        public static final String description = "Beneficial to the Rock Ball";
        public static final int floorTexture = R.drawable.stone_floor;
        public static final int wallTexture = R.drawable.stone_wall;
        public static final int friction = 8;
        public static final int benefitBall = BallCraft.Ball.ROCK_BALL;
    }
    
    public static final class OceanTerrain {
        public static final int id = BallCraft.Terrain.OCEAN_TERRAIN;
        public static final String name = "Ocean";
        public static final String description = "Beneficial to the Water Ball";
        public static final int floorTexture = R.drawable.water_floor;
        public static final int wallTexture = R.drawable.water_wall;
        public static final int friction = 3;
        public static final int benefitBall = BallCraft.Ball.WATER_BALL;
    }
    
    public static final class MetalTerrain {
        public static final int id = BallCraft.Terrain.METAL_TERRAIN;
        public static final String name = "Metal";
        public static final String description = "Beneficial to the Metal Ball";
        public static final int floorTexture = R.drawable.metal_floor;
        public static final int wallTexture = R.drawable.metal_wall;
        public static final int friction = 3;
        public static final int benefitBall = BallCraft.Ball.IRON_BALL;
    }
    
    public static final class FirelandTerrain {
        public static final int id = BallCraft.Terrain.FIRELAND_TERRAIN;
        public static final String name = "Fireland";
        public static final String description = "Beneficial to the Fire Ball";
        public static final int floorTexture = R.drawable.fire_floor;
        public static final int wallTexture = R.drawable.fire_wall;
        public static final int friction = 5;
        public static final int benefitBall = BallCraft.Ball.FIRE_BALL;
    }
    
    @SuppressWarnings("rawtypes")
    public static final Class [] terrains = {
        TableTerrain.class,
        ForestTerrain.class,
        StoneTerrain.class,
        OceanTerrain.class,
        MetalTerrain.class,
        FirelandTerrain.class,
    };

    public static String getTerrainNameById(int id) {
        if (id < terrains.length) {
            try {
                return (String) terrains[id].getField("name").get(null);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }
    
    public static String getTerrainDescriptionById(int id) {
        if (id < terrains.length) {
            try {
                return (String) terrains[id].getField("description").get(null);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }
    
    public static int getTerrainFloorTextureById(int id) {
        if (id < terrains.length) {
            try {
                return terrains[id].getField("floorTexture").getInt(null);
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }
    
    public static int getTerrainWallTextureBallId(int id) {
        if (id < terrains.length) {
            try {
                return terrains[id].getField("wallTexture").getInt(null);
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }
    
    public static int getTerrainFrictionById(int id) {
        if (id < terrains.length) {
            try {
                return terrains[id].getField("friction").getInt(null);
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }
    
    public static int getTerrainBenefitBallById(int id) {
        if (id < terrains.length) {
            try {
                return terrains[id].getField("benefitBall").getInt(null);
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }
    
}
