package org.servalproject.sd_magpi_interface.magpi_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	static int txNotifications = 0;
	public static int newForms = 0;
	public MainActivity me = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        me = this;
        ReceiveSuccinctDataTXNotification.setMainActivity(this);
        SuccinctDataNewFormNotification.setMainActivity(this);
        updateEventLabel();        

    	Context context = (Context)this;
    	final String form = loadAssetTextAsString(context,"example.xhtml");
    	final String record = loadAssetTextAsString(context,"example.xml");
    	final String record_uuid = loadAssetTextAsString(context,"example.uuid");

        final Button button = (Button) findViewById(R.id.dispatchMagpiRecordToSD);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {            	
            	Intent intent = new Intent();
            	intent.putExtra("recordUUID",record_uuid);
            	intent.putExtra("recordData",record);
            	intent.putExtra("recordBundle","contents of ZIP file or other representation of completed record, including any images and other large media");
            	intent.putExtra("formSpecification",form);
            	
                intent.setAction("org.servalproject.succinctdata.ReceiveNewMagpiRecord");
                sendBroadcast(intent);
            }            
        });

    }

    private String loadAssetTextAsString(Context context, String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ( (str = in.readLine()) != null ) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e("sd-magpi-test", "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("sd-magpi-test", "Error closing asset " + name);
                }
            }
        }

        return null;
    }
    
    protected void onResume() {
    	  super.onResume();
    	  updateEventLabel();
    }

    protected void onPause() {
    	  super.onPause();
      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	public void sawNewForm(String formData) {
		newForms++;
		updateEventLabel();		
	}

	public void sawRecordTX(String uuid) {
		txNotifications++;
	}

	private void updateEventLabel() {
		TextView t = (TextView) findViewById(R.id.textView1);
		t.setText(""+txNotifications+" TX notifications, " + newForms + " new forms.");
	}
}
