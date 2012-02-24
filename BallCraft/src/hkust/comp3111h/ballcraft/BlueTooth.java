package hkust.comp3111h.ballcraft;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


//the BulueTooth provides two function:
//sendGameInput();getGameInput();
class BlueTooth
{
	private String TAG = "bluetooth";
	private GameInput remote_input;
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothSocket mSocket = null;
	private ConnectedThread mConnectedThread;
	private String mArrayAdapter;

	
	public BlueTooth(BluetoothAdapter adapter){
		mBluetoothAdapter = adapter;
		mConnectedThread = new ConnectedThread(mSocket);
	}
	
	//build a socket 
	public void setSocket(){
		//first check is there are paired devices
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		     //   mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		    }
		}
		
	}
	
	public void sendGameInput(GameInput local_input){
		//convert GameInput to bytes
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ObjectOutputStream os;
			os = new ObjectOutputStream(baos);
			os.writeObject(local_input);
			mConnectedThread.write(baos.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	}
	 private final Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	        	byte[] writeBuf = (byte[]) msg.obj;
	        	ObjectInputStream ois;
				try {
					ois = new ObjectInputStream(new ByteArrayInputStream(writeBuf));
				    remote_input = (GameInput)ois.readObject();
				} catch (StreamCorruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        }
	 };
	 
	public GameInput getGameInput(){
		//the handler will update the remote input
		return remote_input;
	}
	/**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    // Send the obtained bytes to the UI Activity
                    mHandler.obtainMessage(-1, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         * @param buffer  The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(-1, -1, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }
    }
	
	
	
}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	