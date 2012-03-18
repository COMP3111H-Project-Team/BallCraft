package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.Unit;

import java.util.ArrayList;

public class GameUpdater {
	
	private ArrayList<Unit> units = null;

	public GameUpdater() {
		units = new ArrayList<Unit>();
	}
	
	public String toSerializedString() {
		String serialized = "";
		return null;
	}
	
	public static GameUpdater decodeSerializedUpdater(String serialized) {
		return null;
	}
	
	public ArrayList<Unit> getUnits() {
		return units;
	}
}
