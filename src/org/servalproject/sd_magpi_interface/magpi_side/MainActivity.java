package org.servalproject.sd_magpi_interface.magpi_side;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
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
        
        final Button button = (Button) findViewById(R.id.dispatchMagpiRecordToSD);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent();
            	intent.putExtra("recordUUID","UUID-of-completed-record-as-a-string");
            	intent.putExtra("recordData","xml of completed form record goes here as one long string");
            	intent.putExtra("recordBundle","contents of ZIP file or other representation of completed record, including any images and other large media");
            	intent.putExtra("formSpecification","xml of form specification file goes here as one long string");
            	
                intent.setAction("org.servalproject.succinctdata.ReceiveNewMagpiRecord");
                sendBroadcast(intent);
            }            
        });

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
