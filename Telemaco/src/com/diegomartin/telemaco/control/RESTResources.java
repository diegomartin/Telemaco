package com.diegomartin.telemaco.control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.sync.RestMethod;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Place;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.model.User;

import android.content.Context;

public class RESTResources {
	private static RESTResources instance;
	
	private static final String TRIP = Trip.class.getSimpleName();
	private static final String USER = User.class.getSimpleName();
	private static final String REGISTRATION = "Register";
	private static final String RECOMMENDATION = "Recommendation";
	private static final String CITY = City.class.getSimpleName();
	private static final String CITYSEARCH = "CitySearch";
	private static final String PLACE = Place.class.getSimpleName();
	
	private String tripURL;
	private String userURL;
	private String placeURL;
	private String cityURL;
	private String citySearchURL;
	private String registrationURL;
	private String recommendationURL;
	
	private RESTResources(Context c) throws JSONException{
		String baseURL = c.getString(R.string.server_url);
		
		String response = RestMethod.get(c, baseURL);
		JSONArray arr = new JSONArray(response);
		
		for(int i=0;i<arr.length();i++){
			JSONObject obj = arr.getJSONObject(i);
			String name = obj.getString("name");
			String url = obj.getString("url");
			
			if(name.equals(TRIP)) this.tripURL = url;
			else if(name.equals(USER)) this.userURL = url;
			else if(name.equals(CITY)) this.cityURL = url;
			else if(name.equals(CITYSEARCH)) this.citySearchURL = url;
			else if(name.equals(PLACE)) this.placeURL = url;
			else if(name.equals(REGISTRATION)) this.registrationURL = url;
			else if(name.equals(RECOMMENDATION)) this.recommendationURL = url;
		}
	}
	
	public static RESTResources getInstance(Context c) throws JSONException{
		if (instance == null){
			instance = new RESTResources(c);
		}
		return instance;
	}

	public String getTripURL() {
		return this.tripURL;
	}
	
	public String getRegistrationURL() {
		return this.registrationURL;
	}

	public String getUserURL() {
		return this.userURL;
	}

	public String getPlaceURL(City city) {
		return this.placeURL + "/" + String.valueOf(city.getId());
	}
	
	public String getRecommendationURL(City city) {
		return this.recommendationURL + "/" + String.valueOf(city.getId());
	}

	public String getCityURL() {
		return this.cityURL;
	}
	
	public String getCityURL(City city) {
		return this.cityURL + "/" + String.valueOf(city.getId());
	}

	public String getCitySearchURL(Country country) {
		return this.citySearchURL + "/" + String.valueOf(country.getId());
	}
	
	public String getCitySearchURL(Country country, String query) {
		return this.citySearchURL + "/" + String.valueOf(country.getId()) + "/" + query;
	}	
}
