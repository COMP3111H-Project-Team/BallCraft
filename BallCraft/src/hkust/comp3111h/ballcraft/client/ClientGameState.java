package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.graphics.Drawable;
import hkust.comp3111h.ballcraft.server.Unit;

import java.util.ArrayList;

import android.util.Log;

public class ClientGameState {
	
	private static ClientGameState stateInstance;
	
	private ArrayList<Drawable> drawables;
	private ArrayList<Skill> skills;
	
	private ClientGameState() {
		drawables = new ArrayList<Drawable>();
		skills = new ArrayList<Skill>();
	}
	
	public static ClientGameState getClientGameState() {
		if (stateInstance == null) {
			stateInstance = new ClientGameState();
		}
		return stateInstance;
	}
	
	/**
	 * Apply a serialized GameUpdater to the ClientGameState to change the data
	 * @param serialized The serialized string containing the data of the GameUpdater
	 */
	public void applyUpdater(String serialized) {
		String [] unitStrs = serialized.split("/");
		for (int i = 0; i < unitStrs.length; i++) {
			if (i < drawables.size()) {
				((Unit)(drawables.get(i))).updateFromString(unitStrs[i]);
			} else {
				drawables.add(Unit.fromSerializedString(unitStrs[i]));
			}
		}
	}
	
	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}
	
	public void addSkill(Skill skill) {
		skills.add(skill);
	}
	
	public ArrayList<Drawable> getDrawables() {
		return drawables;
	}
	
	public ArrayList<Skill> getSkills() {
		return skills;
	}

}
