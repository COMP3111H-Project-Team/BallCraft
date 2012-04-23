package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.client.Client;
import hkust.comp3111h.ballcraft.client.GameInput;
import hkust.comp3111h.ballcraft.client.MultiPlayerGameInitializer;
import hkust.comp3111h.ballcraft.server.bt.BluetoothService;

public class ServerAdapter {

	private static BluetoothService service;
	
	public static void setService(BluetoothService s) {service = s;}
	
    public static void sendToServer(GameInput input) {
        if(BallCraft.isServer) 
        {
        	Server.setState(BallCraft.myself + ";" + input.toSerializedString());
        	return;
        }
        
        String message = "__MSG__"+BallCraft.myself + ";" + input.toSerializedString();
        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            service.write(send);
        }
    }
 
    public static void processServerMsg(String msg, int id) {
        if(id == BallCraft.myself) 
        {
        	Client.processSerializedUpdate(msg);
        	return;
        }
        
        // Check that there's actually something to send
        if (msg.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
        	msg = "__MSG__" + msg;
            byte[] send = msg.getBytes();
            service.write(send);
        }
    }
    
    public static void sendInitMsgToClient(String msg, int id) {
            	
    	if(id == BallCraft.myself) 
        {
        	MultiPlayerGameInitializer.handleInitMsg(msg);
        	return;
        }
    	
        // Check that there's actually something to send
        if (msg.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
        	msg = "__MSG__" + msg;
            byte[] send = msg.getBytes();
            service.write(send);
        }
    }
    
    public static void sendClientInitMsg(String msg)
    {
        if (msg.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = msg.getBytes();
            service.write(send);
        }
    }

}
