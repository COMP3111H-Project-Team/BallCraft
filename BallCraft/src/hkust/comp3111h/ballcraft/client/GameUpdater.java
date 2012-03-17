package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.InternalUnitData;

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
