package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.Unit;

import java.util.ArrayList;

public class GameUpdater {
	
	public ArrayList<Unit> units = null;

	public GameUpdater() {
		units = new ArrayList<Unit>();
	}
	
	public void fromSerializedString(String serialized) {
		String [] unitStrs = serialized.split("/");
		for (int i = 0; i < unitStrs.length; i++) {
			if (i < units.size()) {
				units.get(i).updateFromString(unitStrs[i]);
			} else {
				units.add(Unit.fromSerializedString(unitStrs[i]));
			}
		}
	}
	
	public String toSerializedString() {
		String serialized = "";
		for (int i = 0; i < units.size(); i++) {
			serialized += units.get(i).toSerializedString();
			if (i != units.size() - 1) { // not the last one
				serialized += "/";
			}
		}
		return serialized;
	}
	
	public ArrayList<Unit> getUnits() {
		return units;
	}
	
}
