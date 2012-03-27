package hkust.comp3111h.ballcraft.server.bt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public abstract class BluetoothAwareActivity extends Activity {
	private int kRequestEnableBluetooth;
	private int kRequestEnableBluetoothDiscoverability;
	private BluetoothAdapter bluetoothAdapter;
	
	/**
	 * Ask the user to enable bluetooth. App should override the
	 * onEnableRequestResult callback method to receive the result.
	 * @param kRequestEnableBluetooth A unique constant to be used in onActivityResult
	 * @param kRequestEnableBluetoothDiscoverability A unique constant
	 */
	void initializeBluetooth(int kRequestEnableBluetooth,
							 int kRequestEnableBluetoothDiscoverability) {
		this.kRequestEnableBluetooth = kRequestEnableBluetooth;
		this.kRequestEnableBluetoothDiscoverability = kRequestEnableBluetoothDiscoverability;
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		enableBluetooth();
	}

	private void enableBluetooth() {
		if (!bluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, kRequestEnableBluetooth);
		}
		else {
			onEnableRequestResult(true);
		}
	}
	
	/**
	 * Ask the user to allow his phone to be bluetooth-discoverable for some time.
	 * @param numSeconds How long shall we be discoverable
	 */
	void setDiscoverableFor(int numSeconds) {
    	Intent discoverableIntent = new
    			Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, numSeconds);
		startActivityForResult(discoverableIntent, kRequestEnableBluetoothDiscoverability);
	}
	
	/**
	 * Asynchronous callback method, called when the result of enabling bluetooth
	 * came out.
	 * @param enabled Whether the user allowed or not
	 */
	abstract void onEnableRequestResult(boolean enabled);
	
	/**
	 * Asynchronous callback method, called with the result of enabling bluetooth
	 * discoverability came out.
	 * @param discoverable Whether the user allowed or not
	 */
	abstract void onDiscoverabilityRequestResult(boolean discoverable);
	
    public void onActivityResult(int reqCode, int resCode, Intent ite) {
    	super.onActivityResult(reqCode, resCode, ite);
    	
    	if (reqCode == kRequestEnableBluetooth) {
    		onEnableRequestResult(resCode == RESULT_OK);
    	}
    	else if (reqCode == kRequestEnableBluetoothDiscoverability) {
    		onDiscoverabilityRequestResult(resCode != RESULT_CANCELED);
    	}
    }
}
