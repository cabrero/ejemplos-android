package org.madsgroup.timeapp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class City {

    private String _name;
    double _lat;
    double _lng;
    private int _dstOffset;
    private int _rawOffset;
    private String _timeZone;
    private String _timeZoneName;

    public City(String name, double lat, double lng, int dstOffset, int rawOffset, String timeZone, String timeZoneName) {
	_name = name;
	_lat = lat;
	_lng = lng;
	_dstOffset = dstOffset;
	_rawOffset = rawOffset;
	_timeZone = timeZone;
	_timeZoneName = timeZoneName;
    }

    public String toString() {
	return _name + " " + _time();
    }

    public String name() {
	return _name;
    }

    public String localTime() {
	if (_timeZone != null)
	    return _time();
	else
	    return "??:??";
    }

    private String _time() {
	Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(_timeZone));
	return String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

    }
}

