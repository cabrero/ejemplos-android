package org.madsgroup.timeapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.TextView;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends Activity
{
    private static final String TAG = "IPM_TUT";
	
    private TextView _tvTime;
    private Handler _handler;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	_tvTime = (TextView) findViewById(R.id.label4time);
	_handler = new Handler();
    }

    @Override
    public void onResume() 
    {
	super.onResume();
        _updateTime();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        _handler.removeCallbacksAndMessages(null);
    }

    private void _updateTime()
    {
        Log.v(TAG, "update time");
	Time now = new Time();
	now.setToNow();
	String labelText = String.format(getString(R.string.isTime),
					 now.format("%H:%M"));
	_tvTime.setText(labelText);
	long delay = Math.abs(60 - now.second) * 1000;
	_handler.postDelayed(new Runnable() {
			public void run() {
				MainActivity.this._updateTime();
			}
		},
		delay);
    }
}