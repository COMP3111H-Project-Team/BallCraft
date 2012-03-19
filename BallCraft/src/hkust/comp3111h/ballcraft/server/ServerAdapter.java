package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.client.Client;
import hkust.comp3111h.ballcraft.client.GameInput;

public class ServerAdapter {
		
	public static void sendToServer(GameInput input) {
		Server.setState(input.toSerializedString());
	}
	
	public static void processServerMsg(String msg) {
		Client.processSerializedUpdate(msg);
	}
	
}
