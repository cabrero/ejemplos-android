package org.madsgroup.timeapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.TextView;

public class MainActivity extends Activity
{
	
    private TextView _tvTime;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	_tvTime = (TextView) findViewById(R.id.label4time);
    }

    @Override
    public void onResume() 
    {
	super.onResume();
	Time now = new Time();
	now.setToNow();
	String labelText = String.format(getString(R.string.isTime),
					 now.format("%H:%M"));
	_tvTime.setText(labelText);
    }
}