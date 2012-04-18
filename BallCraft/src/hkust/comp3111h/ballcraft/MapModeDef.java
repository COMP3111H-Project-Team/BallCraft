package hkust.comp3111h.ballcraft;

public class MapModeDef {

    public static final class DayMode {
        public static final int id = BallCraft.MapMode.DAY_MODE;
        public static final String name = "Day";
        public static final String description = "Not beneficial to dark ball";
        public static final float lightAmbient[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        public static final float lightDiffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        public static final float lightSpecular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    }
    
    public static final class NightMode {
        public static final int id = BallCraft.MapMode.NIGHT_MODE;
        public static final String name = "Night";
        public static final String description = "Beneficial to dark ball";
        public static final float lightAmbient[] = { 0.5f, 0.5f, 0.5f, 1.0f };
        public static final float lightDiffuse[] = { 0.5f, 0.5f, 0.5f, 1.0f };
        public static final float lightSpecular[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    }
    
    @SuppressWarnings("rawtypes")
    public static final Class [] mapModes = {
        DayMode.class,
        NightMode.class,
    };
    
	public static String getMapModeNameById(int id) {
        if (id < mapModes.length) {
            try {
                return (String) mapModes[id].getField("name").get(null);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }
    
	public static String getMapModeDescriptionById(int id) {
        if (id < mapModes.length) {
            try {
                return (String) mapModes[id].getField("description").get(null);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }
    
	public static float [] getMapModeAmbientLightById(int id) {
        if (id < mapModes.length) {
            try {
                return (float[]) mapModes[id].getField("lightAmbient").get(null);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }
	
	public static float [] getMapModeDiffuseLightById(int id) {
	    if (id < mapModes.length) {
	        try {
	            return (float[]) mapModes[id].getField("lightDiffuse").get(null);
	        } catch (Exception e) {
	            return null;
	        }
	    } else {
	        return null;
	    }
	}
	   
	public static float [] getMapModeSpecularLightById(int id) {
	    if (id < mapModes.length) {
	        try {
	            return (float[]) mapModes[id].getField("lightSpecular").get(null);
	        } catch (Exception e) {
	            return null;
	        }
	    } else {
	        return null;
	    }
	}
	
}
