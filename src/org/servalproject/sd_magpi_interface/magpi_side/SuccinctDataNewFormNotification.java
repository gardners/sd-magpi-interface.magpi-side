package org.servalproject.sd_magpi_interface.magpi_side;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SuccinctDataNewFormNotification extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// Received a new form
		String formData =  intent.getStringExtra("formData");
		int length = formData.length();
		Toast.makeText(context, "Succinct Data has sent a new form with length = " + Integer.toString(length), Toast.LENGTH_LONG).show();
	}

}
