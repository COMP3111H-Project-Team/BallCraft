package hkust.comp3111h.ballcraft.server.bt;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class BluetoothService {
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	private Thread connectionAccepterThread;
	private BluetoothServerSocket serverSocket;
	private Activity parent;
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		public void onReceive(Context ctx, Intent ite) {
			String act = ite.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(act)) {
				BluetoothDevice device = ite.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				onDeviceFound(device);
			}
			else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(act)) {
				onDiscoveryStarted();
			}
			else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(act)) {
				onDiscoveryFinished();
			}
		}
	};
	
	private Runnable connectionAccepter = new Runnable() {
		public void run() {
			BluetoothServerSocket serverSocket = getServerSocket();
			BluetoothSocket newConnection = null;
			
			while (stillAcceptingConnection()) {
				try {
					newConnection = serverSocket.accept();
				}
				catch (IOException e) {
					continue;
				}
    				
				if (newConnection == null) {
					continue;
				}
				
				if (shallAccept(newConnection)) {
					try {
						onClientConnected(wrapAcceptedSocket(newConnection));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
				else {
					try {
						newConnection.close();
					} catch (IOException e) { }
				}
				newConnection = null;
			}
		}
	};

	/**
	 * Call this method to start bluetooth device discovery.
	 * Should only be called after bluetooth has been enabled.
	 * @param parent The activity that is used during discovery
	 */
	public void startDiscovery(Activity parent) {
		this.parent = parent;
		parent.registerReceiver(broadcastReceiver,
						 		new IntentFilter(BluetoothDevice.ACTION_FOUND));
		parent.registerReceiver(broadcastReceiver,
						 	    new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
		parent.registerReceiver(broadcastReceiver,
						 	    new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
    	bluetoothAdapter.startDiscovery();
    	
		Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
		BluetoothDevice device;
		for (Iterator<BluetoothDevice> it = devices.iterator(); it.hasNext(); ) {
			device = it.next();
			onDeviceFound(device);
		}
	}
	
	/**
	 * Should be called before the activity that registered the
	 * broadcast receiver is closed.
	 */
	void destroy() {
		parent.unregisterReceiver(broadcastReceiver);
		if (getServerSocket() != null) {
			try {
				getServerSocket().close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Asynchronous listening method, start listening on the local machine.
	 * Throws IOException if the listening failed.
	 */
	public void startListening() throws IOException {
		setServerSocket(bluetoothAdapter.listenUsingRfcommWithServiceRecord(
				getServiceName(), getUUID()));
		onListeningStarted();
		connectionAccepterThread = new Thread(connectionAccepter);
		connectionAccepterThread.start();
	}
	
	
	/**
	 * Asynchronous connection method, connection to a given server using
	 * the default server factory.
	 * @param device The device to connect to
	 * @return A channel that is paired with the server.
	 * @throws IOException
	 */
	public BluetoothChannel connectToDevice(BluetoothDevice device) throws IOException {
    	final BluetoothSocket socket = device.createRfcommSocketToServiceRecord(getUUID());
		final BluetoothChannel channel = getChannelFactory().newServerChannel();
		final Thread opener = new Thread() {
			public void run() {
				try {
					socket.connect();
				} catch (IOException e) {
					try {
						socket.close();
					} catch (IOException e1) { }
					channel.onError(e);
					return;
				}
				try {
					channel.initialize(socket);
				} catch (IOException e) {
					try {
						socket.close();
					} catch (IOException e1) { }
					channel.onError(e);
					return;
				}
				channel.onOpen();
			}
		};
		opener.start();
		return channel;
	}
	
	/**
	 * @return The UUID that the server and the client are using. Should be
	 * the same for both the server and the client.
	 */
	abstract UUID getUUID();
	
	/**
	 * Asynchronous callback method, called during device discovery
	 * when a device that previously is not paired is found.
	 * @param device The device found
	 */
	abstract void onDeviceFound(BluetoothDevice device);
	
	/**
	 * Asynchronous callback method, called when the device discovery
	 * just really started.
	 */
	abstract void onDiscoveryStarted();
	
	/**
	 * Asynchronous callback method, called when there is no more device
	 * left to be discovered.
	 */
	abstract void onDiscoveryFinished();
	
	/**
	 * Asynchronous callback method, called when the server has just
	 * started listening.
	 */
	abstract void onListeningStarted();
	
	/**
	 * Asynchronous callback method, called when a client has connected.
	 * @param client The connected client
	 */
	abstract void onClientConnected(BluetoothChannel client);
	
	/**
	 * @return Whether the server is accepting further connections.
	 */
	abstract boolean stillAcceptingConnection();
	
	/**
	 * @return The channel factory that the service is using.
	 */
	abstract BluetoothChannelFactory getChannelFactory();
	
	/**
	 * @param socket The client socket to be accepted
	 * @return Whether the server should accept it
	 */
	abstract boolean shallAccept(BluetoothSocket socket);
	
	/**
	 * @return Whatever you like :)
	 */
	abstract String getServiceName();
	
	
	private BluetoothChannel wrapAcceptedSocket(final BluetoothSocket socket) throws IOException {
		final BluetoothChannel channel;
		try {
			channel = getChannelFactory().newClientChannel();
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
		channel.initialize(socket);
		
		Thread openNotifier = new Thread() {
			public void run() {
				channel.onOpen();
			}
		};
		openNotifier.start();
		
		return channel;
	}

	protected void setServerSocket(BluetoothServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	protected BluetoothServerSocket getServerSocket() {
		return serverSocket;
	}
}
