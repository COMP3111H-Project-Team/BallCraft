/**
 * This is an example of how the Bluetooth API works.
 */

package hkust.comp3111h.ballcraft.server.bt;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ThreadedBlueToothActivity extends BluetoothAwareActivity {
	final String kServerName = "sanya-htc";
	
	class ChannelToClient extends BluetoothChannel {
		@Override
		void onOpen() {
			log("@server, channel opened");
		}

		@Override
		void onMessage(byte[] message) {
			log("@server, message received: `" + new String(message) + "'");
		}

		@Override
		void onError(Exception e) {
			log("@server, error happened");
		}

		@Override
		void onClose() {
			log("@server, connection closed");
		}
	}
	
	class ChannelToServer extends BluetoothChannel {
		@Override
		void onOpen() {
			log("@client, channel opened.");
			sendMessage("From client.".getBytes());
		}

		@Override
		void onMessage(byte[] message) {
			log("@client, msg received: `" + new String(message) + "'");
		}

		@Override
		void onError(Exception e) {
			log("@client, err caught: " + e);
		}

		@Override
		void onClose() {
			log("@client, conn closed");
		}
	}
	
	BluetoothChannelFactory channelFactory = new BluetoothChannelFactory() {
		@Override
		public BluetoothChannel newServerChannel() {
			return new ChannelToServer();
		}

		@Override
		public BluetoothChannel newClientChannel() {
			return new ChannelToClient();
		}
	};
	
	BluetoothService bluetoothService = new BluetoothService() {
		BluetoothDevice serverDevice;
		
		@Override
		UUID getUUID() {
			// Just to keep it the same across the server and the client.
			return UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
		}
	
		@Override
		void onDeviceFound(BluetoothDevice device) {
			String name = device.getName();
			if (name == null) {
				name = "<nil>";
			}
			else if (name.equals(kServerName)) {
				serverDevice = device;
				BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
			}
			log("found " + name);
		}

		@Override
		void onDiscoveryFinished() {
			if (serverDevice == null) {
				log("discovery finished, I am the server.");
				new Thread() {
					public void run() {
						int mSecSleep = 500;
						while (true) {
							try {
								startListening();
								break;
							} catch (IOException e) {
								log("listen failed: " + e + ", retry after " + mSecSleep + "mSec.");
								try {
									Thread.sleep(mSecSleep);
									mSecSleep <<= 1;
								} catch (InterruptedException e1) { }
								log("listening: retry now.");
							}
						}
					}
				}.start();
			}
			else {
				log("discovery finished, I am the client, connecting to the server: " + serverDevice.getName());
				try {
					connectToDevice(serverDevice);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		@Override
		void onClientConnected(BluetoothChannel client) {
			log("server: someone connected");
			client.sendMessage("hello, world! -- from server".getBytes());
		}

		@Override
		boolean stillAcceptingConnection() {
			return true;
		}

		@Override
		BluetoothChannelFactory getChannelFactory() {
			return channelFactory;
		}

		@Override
		void onListeningStarted() {
			log("listening started.");
		}

		@Override
		void onDiscoveryStarted() {
			log("discovery started.");
		}

		@Override
		boolean shallAccept(BluetoothSocket socket) {
			return true;
		}

		@Override
		String getServiceName() {
			return "Ballcraft";
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.main);
        
        initializeBluetooth(0, 1);
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	bluetoothService.destroy();
    }
    
    @Override
    public void onBackPressed() {
    	onDestroy();
    	crashMe();
    }
    
    @SuppressWarnings({ "null", "unused" })
	void crashMe() {
    	Integer i = null;
    	int i2 = i;
    }

	@Override
	void onEnableRequestResult(boolean enabled) {
		if (enabled) {
			log("enabled: true");
			setDiscoverableFor(600);
		}
		else {
			log("enabled: false");
		}
	}

	@Override
	void onDiscoverabilityRequestResult(boolean discoverable) {
		if (discoverable) {
			log("discoverable: true");
			bluetoothService.startDiscovery(this);
		}
		else {
			log("discoverable: false");
		}
	}

	void log(final String msg) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(ThreadedBlueToothActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
		Log.e("btServ", msg);
	}
}
