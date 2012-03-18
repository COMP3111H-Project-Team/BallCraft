package hkust.comp3111h.ballcraft.client;

import java.util.ArrayList;

public class GameUpdater {
	
	ArrayList<InternalUnitData> units = null;

	public GameUpdater() {
		units = new ArrayList<InternalUnitData>();
	}
	
	public String serialize() {
		return null;
	}
	
	public static GameUpdater decodeSerializedUpdater(String serialized) {
		return null;
	}
}
