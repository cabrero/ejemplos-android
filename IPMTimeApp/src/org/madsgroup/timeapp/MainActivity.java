package org.madsgroup.timeapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.TextView;
import android.os.Handler;
import android.util.Log;
import android.widget.GridView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.util.Locale;
import android.app.ProgressDialog;


public class MainActivity extends Activity
{

    private String _locationName = "";
    private TextView _tvLocation;
    private TextView _tvTime;
    private Handler _handler;
    private ArrayAdapter<City> _citiesGridViewAdapter;
    private ProgressDialog _progressDialog;

    private City[] _cities = new City[22];
    private void _init() {
	int i = 0;
        _cities[i++] = new City("Madrid", 40.4165, -3.70256, 0, 0, null, null);
        _cities[i++] = new City("Barcelona", 41.38879, 2.15899, 0, 0, null, null);
        _cities[i++] = new City("Amsterdam", 52.37403, 4.88969, 0, 0, null, null);
        _cities[i++] = new City("Stockholm", 59.33258, 18.0649, 0, 0, null, null);
        _cities[i++] = new City("Sydney", -33.86785, 151.20732, 0, 0, null, null);
        _cities[i++] = new City("Zurich", 47.36667, 8.55, 0, 0, null, null);
        _cities[i++] = new City("Vienna", 48.20849, 16.37208, 0, 0, null, null);
        _cities[i++] = new City("Copenhagen", 55.67594, 12.56553, 0, 0, null, null);
        _cities[i++] = new City("Vancouver", 49.24966, -123.11934, 0, 0, null, null);
        _cities[i++] = new City("Helsinki", 60.16952, 24.93545, 0, 0, null, null);
        _cities[i++] = new City("Paris", 48.85341, 2.3488, 0, 0, null, null);
        _cities[i++] = new City("Honolulu", 21.30694, -157.85833, 0, 0, null, null);
        _cities[i++] = new City("Melbourne", -37.814, 144.96332, 0, 0, null, null);
        _cities[i++] = new City("Berlin", 52.52437, 13.41053, 0, 0, null, null);
        _cities[i++] = new City("Tokyo", 35.6895, 139.69171, 0, 0, null, null);
        _cities[i++] = new City("Auckland", -36.86667, 174.76667, 0, 0, null, null);
        _cities[i++] = new City("Kyoto", 35.02107, 135.75385, 0, 0, null, null);
        _cities[i++] = new City("Munich", 48.13743, 11.57549, 0, 0, null, null);
        _cities[i++] = new City("Hamburg", 53.57532, 10.01534, 0, 0, null, null);
        _cities[i++] = new City("Singapore", 1.28967, 103.85007, 0, 0, null, null);
        _cities[i++] = new City("Duesseldorf", 51.22172, 6.77616, 0, 0, null, null);
        _cities[i++] = new City("Fukuoka", 33.60639, 130.41806, 0, 0, null, null);

    }


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	_init();

	_tvLocation = (TextView) findViewById(R.id.label4location);
	_tvTime = (TextView) findViewById(R.id.label4time);
	_handler = new Handler();
        GridView citiesGridView = (GridView) findViewById(R.id.citiesGrid);
	_citiesGridViewAdapter = new CitiesArrayAdapter(this, _cities);
	citiesGridView.setAdapter(_citiesGridViewAdapter);

	_progressDialog = new ProgressDialog(MainActivity.this);
	_progressDialog.setTitle(getString(R.string.downloadingData));
	_progressDialog.setMessage(getString(R.string.downloadingTimeInfo));
	_progressDialog.setProgressStyle(_progressDialog.STYLE_HORIZONTAL);
	_progressDialog.setMax(_cities.length);
	_progressDialog.show();
	Runnable downloader = new Runnable() {
		@Override
		public void run() {
		    _downloadCityTimeData();
		}
	    };
	new Thread(downloader).start();
	new Thread(new Runnable() {
		@Override
		public void run() {
		    _downloadLocation();
		}
	    }).start();
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

    private void _updateLocation(String name) {
	_tvLocation.setText(getString(R.string.isLocation, name));
	_locationName = name;
    }

    private void _updateTime()
    {
        Log.v(Util.TAG, "update time");
	Time now = new Time();
	now.setToNow();
	_tvTime.setText(getString(R.string.isTime, now.format("%H:%M")));
	_citiesGridViewAdapter.notifyDataSetChanged();
	long delay = Math.abs(60 - now.second) * 1000;
	_handler.postDelayed(new Runnable() {
			public void run() {
				MainActivity.this._updateTime();
			}
		},
		delay);
    }

    private void _downloadLocation() {
	final String name;
	try {
	    JSONObject jsonObject = Util.downloadJSONObject(new URL("http://freegeoip.net/json/"));
	    name = jsonObject.getString("city");
	    runOnUiThread(new Runnable() {
		    @Override
		    public void run() {
			_updateLocation(name);
		    }
		});
	}
	catch (MalformedURLException e) {
	}
	catch (JSONException e) {
	}
	catch (IOException e) {
	}
    }

    private void _downloadCityTimeData() {
	HttpsURLConnection urlConnection = null;
	String urlStr = "https://maps.googleapis.com/maps/api/timezone/json?location=%f,%f&timestamp=%d&sensor=false";
	Time now = new Time();
	now.setToNow();
	long timestamp = now.toMillis(true) / 1000;
	URL url = null;
	for(int i=0; i<_cities.length; i++) {
	    final City city = _cities[i];
	    runOnUiThread(new Runnable() {
		    @Override
		    public void run() {
			_progressDialog.setMessage(getString(R.string.downloadingTimeInfo)+city.name());
		    }
		});
	    try {
		JSONObject jsonObject = Util.downloadJSONObject(new URL(String.format((Locale) null, urlStr, city._lat, city._lng, timestamp)));
		_cities[i] = new City(city.name(), city._lat, city._lng,
				      jsonObject.getInt("dstOffset"),
				      jsonObject.getInt("rawOffset"),
				      jsonObject.getString("timeZoneId"),
				      jsonObject.getString("timeZoneName"));
		
	    }
	    catch (MalformedURLException e) {
	    }
	    catch (IOException e) {
	    }
	    catch (JSONException e) {		    
	    }
	    runOnUiThread(new Runnable() {
		    @Override
		    public void run() {
			_progressDialog.incrementProgressBy(1);
		    }
		});
	}
	runOnUiThread(new Runnable() {
		@Override
		public void run() {
		    _progressDialog.dismiss();
		    _citiesGridViewAdapter.notifyDataSetChanged();
		}
	    });		    
    }

    static class CityRowViewCache {
	public TextView tvName;
	public TextView tvTime;
    }

    private class CitiesArrayAdapter extends ArrayAdapter<City> {
	private Activity _context;
	private City[] _cities;

	public CitiesArrayAdapter(Activity context, City[] cities) {
	    super(context, R.layout.city_cell, cities);
	    _context = context;
	    _cities = cities;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
	    View rowView = contentView;
	    CityRowViewCache cityRowViewCache;

	    if (rowView == null) {
		LayoutInflater inflater = _context.getLayoutInflater();
		rowView = inflater.inflate(R.layout.city_cell, null, true);
		cityRowViewCache = new CityRowViewCache();
		cityRowViewCache.tvName = (TextView) rowView.findViewById(R.id.cityName);
		cityRowViewCache.tvTime = (TextView) rowView.findViewById(R.id.cityTime);
		rowView.setTag(cityRowViewCache);
	    }
	    else {
		cityRowViewCache = (CityRowViewCache) rowView.getTag();
	    }

	    cityRowViewCache.tvName.setText(_cities[position].name());
	    cityRowViewCache.tvTime.setText(_cities[position].localTime());

	    return rowView;
	}
    } 
}
