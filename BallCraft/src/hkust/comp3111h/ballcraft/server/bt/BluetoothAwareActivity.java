package hkust.comp3111h.ballcraft.server.bt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public abstract class BluetoothAwareActivity extends Activity {
	private int kRequestEnableBluetooth;
	private int kRequestEnableBluetoothDiscoverability;
	private BluetoothAdapter bluetoothAdapter;
	
	void initializeBluetooth(int kRequestEnableBluetooth,
							 int kRequestEnableBluetoothDiscoverability) {
		this.kRequestEnableBluetooth = kRequestEnableBluetooth;
		this.kRequestEnableBluetoothDiscoverability = kRequestEnableBluetoothDiscoverability;
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		enableBluetooth();
	}

	void enableBluetooth() {
		if (!bluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, kRequestEnableBluetooth);
		}
		else {
			onEnableRequestResult(true);
		}
	}
	
	void setDiscoverableFor(int numSeconds) {
    	Intent discoverableIntent = new
    			Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, numSeconds);
		startActivityForResult(discoverableIntent, kRequestEnableBluetoothDiscoverability);
	}
	
	abstract void onEnableRequestResult(boolean enabled);
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
