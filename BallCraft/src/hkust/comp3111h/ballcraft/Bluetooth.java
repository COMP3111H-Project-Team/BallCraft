package hkust.comp3111h.ballcraft;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


//the BulueTooth provides two function:
//sendGameInput();getGameInput();
public class Bluetooth extends Activity
{
	private String TAG = "bluetooth";
	private GameInput remote_input;
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothService mBluetoothService;
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
	// Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_ENABLE_BT = 3;
	
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	// Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothService = new BluetoothService(this, mHandler);
		
	}
	
    public void onStart() {
    	super.onStart();
    	 // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        
    	Intent serverIntent = new Intent(this, DeviceListActivity.class);
    	Log.d(TAG, "startActivityForResult");
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
    }

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
         Log.d(TAG, "onActivityResult " + resultCode);
         // When DeviceListActivity returns with a device to connect
         if (resultCode == Activity.RESULT_OK) {
             connectDevice(data);
         }
	}
	
	public void connectDevice(Intent data) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BLuetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mBluetoothService.connect(device,true);
	}
	
	public void sendGameInput(GameInput local_input){
		//convert GameInput to bytes
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ObjectOutputStream os;
			os = new ObjectOutputStream(baos);
			os.writeObject(local_input);
			mBluetoothService.write(baos.toByteArray());
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

}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	