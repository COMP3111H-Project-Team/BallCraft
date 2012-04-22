package hkust.comp3111h.ballcraft.server.bt;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.server.ServerAdapter;
import hkust.comp3111h.ballcraft.ui.BallSelectMenu;
import hkust.comp3111h.ballcraft.ui.MainMenu;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class BluetoothActivity extends Activity {

	//Debug
	public final static String TAG = "bluetooth";
	public final boolean D = true;

	private static BluetoothActivity context;
	
	// Message types sent from the BluetoothService Handler
	public static final int MESSAGE_LOST = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	public static final int CANCLE = 6;
	private static BluetoothService service;

	// Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;    
    private static final int REQUEST_DISCOVERABLE = 3;    
	  
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private BluetoothAdapter bluetoothAdapter;
    private String recievedMessage;
    
    // Name of the connected device
    private String connectedDeviceName = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        // Get local Bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
   	 	initialize();
    }
    
    public void initialize(){
    	service = new BluetoothService(this, mHandler);    	
    	setDiscoverableFor(300);    	
    	Log.i(TAG,"initialize");
    }
    
    public void scanDevice(){
    	Log.e(TAG,"start device activity");
    	 Intent serverIntent = new Intent(this, DeviceListActivity.class);
         startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
         Log.e(TAG,"after start device activity");
         // Performing this check in onResume() covers the case in which BT was
         // not enabled during onStart(), so we were paused to enable it...
         // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
         if (service != null) {
             // Only if the state is STATE_NONE, do we know that we haven't started already
             if (service.getState() == BluetoothService.STATE_NONE) {
               // Start the Bluetooth chat services
               service.start();
             }
         }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.e(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
        	Log.e(TAG, "connect:" + resultCode);	
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                Log.e(TAG,address);
                // Get the BLuetoothDevice object
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                service.connect(device);

            } else if(resultCode == CANCLE){
            	this.destroy();
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
            	initialize();
                
            } else {
                // User did not enable Bluetooth or an error occured
                Log.d(TAG, "BT not enabled");
                //Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        case REQUEST_DISCOVERABLE:
        	if (resultCode == Activity.RESULT_CANCELED) 
        	{
        		this.destroy();
        	}else {
        		scanDevice();
        	}
        	break;
        }
        
    }
    
   
    public void setDiscoverableFor(int numSeconds) {
        Intent discoverableIntent = new Intent(
                BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(
                BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, numSeconds);
        startActivityForResult(discoverableIntent, REQUEST_DISCOVERABLE);
    }
    
    public static void startGame()
    {
    	ServerAdapter.setService(service);
        context.startActivity(new Intent(context, BallSelectMenu.class));
        context.finish();
    }
   
    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    public void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (service.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            service.write(send);
        }
    }
    
    
    public String getMessage() {
    	return recievedMessage;
    }
    
    private void destroy(){
    	service.stop();
    	this.finish();
    }
    
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//        	Log.e("msg received", "Some Message");
            switch (msg.what) {
            
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
            	Log.e("msg received", "MESSAGE_READ");
                // construct a string from the valid bytes in the buffer
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                connectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + connectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_LOST:
            	destroy();
            	break;
            }
        }
    };
};