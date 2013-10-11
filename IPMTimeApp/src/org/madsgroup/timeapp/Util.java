package org.madsgroup.timeapp;

import java.net.URL;
import java.net.HttpURLConnection;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.util.Locale;
import android.util.Log;


public class Util {

    static final String TAG = "IPM_TUT";

    static public JSONObject downloadJSONObject(URL url)  throws IOException, JSONException {

	Log.v(TAG, "Downloading: " + url.toString());

	HttpURLConnection urlConnection = null;
	JSONObject jsonResponse = null;
	try {
	    urlConnection = (HttpURLConnection) url.openConnection();
	    urlConnection.setRequestProperty("Connection", "Keep-Alive");
	
	    if (urlConnection.getResponseCode() == 200) {
		Scanner scanner = new Scanner(urlConnection.getInputStream());
		scanner.useDelimiter("\\Z");
		String jsonResponseStr = scanner.next();
		Log.v(TAG, "Got response: " + jsonResponseStr);
		jsonResponse = new JSONObject(jsonResponseStr);
	    }
	    else {
		throw(new IOException("(" + urlConnection.getResponseCode() + ") " + urlConnection.getResponseMessage()));
	    }
	}
	finally {
	    if (urlConnection != null) 
		urlConnection.disconnect();
	}
	return jsonResponse;
    }

}
