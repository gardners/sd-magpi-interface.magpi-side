package org.servalproject.sd_magpi_interface.magpi_side;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReceiveSuccinctDataTXNotification extends BroadcastReceiver {

	static MainActivity mainActivity=null;
	
	public static void setMainActivity(MainActivity m) {
		mainActivity = m;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		String recordUUID =  intent.getStringExtra("recordUUID");
		Toast.makeText(context, "Succinct Data has TXd record UUID:" + recordUUID, Toast.LENGTH_LONG).show();		
		mainActivity.sawRecordTX(recordUUID);
	
	}

}
