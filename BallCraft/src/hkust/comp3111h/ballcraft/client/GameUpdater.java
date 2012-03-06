package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.UnitData;

import java.util.ArrayList;

public class GameUpdater {
	
	ArrayList<UnitData> units = null;

	public GameUpdater() {
		units = new ArrayList<UnitData>();
	}
	
	public String serialize() {
		return null;
	}
	
	public static GameUpdater decodeSerializedUpdater(String serialized) {
		return null;
	}
}
