package org.madsgroup.timeapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.TextView;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
	Time now = new Time();
	now.setToNow();
	String labelText = String.format(getString(R.string.isTime),
					 now.format("%H:%M"));
	TextView tv = (TextView) findViewById(R.id.label4time);
	tv.setText(labelText);
    }
}
