package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.client.GameInput;

public class ServerAdapter {
		
	public static void sendToServer(GameInput input) {
		Server.process(input.toSerializedString());
	}
	
	public static void onMessage(Byte [] bytes) {
		// dummy function, deserialize and send to client
	}
	
}
