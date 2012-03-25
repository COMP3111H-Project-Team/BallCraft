package hkust.comp3111h.ballcraft.server.bt;

abstract interface BluetoothChannelFactory {
	/**
	 * Create a new server socket object.
	 * This is intended to be used by the client side. That is, it creates
	 * a channel that connect to the server.
	 * @return A bluetooth channel that connects this side to the server side.
	 */
	BluetoothChannel newServerChannel();
	
	/**
	 * Create a new client socket object.
	 * This is intended to be used by the server side. That is, it creates
	 * a channel that connect to the client.
	 * @return A bluetooth channel that connects this side to the client side.
	 */
	BluetoothChannel newClientChannel();
}
