package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.client.GameInput;

public class ServerAdapter {
	
	public ServerAdapter() {
	}
	
	public void sendToServer(GameInput input) {
		// TODO: serialize input and send to server
	}
	
	public void onMessage(Byte [] bytes) {
		// dummy function, deserialize and send to client
	}
	
}
