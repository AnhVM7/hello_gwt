package com.example.client.activities;

import com.example.client.activities.detail.DetailPlace;
import com.example.client.activities.home.HomePlace;
import com.example.client.activities.list.ListPlace;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.google.gwt.user.client.Window;

@WithTokenizers({HomePlace.Tokenizer.class })

public class AppPlaceHistoryMapper implements PlaceHistoryMapper {
	public static final String DELIMITER = "/";
	public static final String COLON = "=";
	
	private PlaceHistoryMapper placeHistoryMapper;
	
    public AppPlaceHistoryMapper() {
		super();
	}
    public AppPlaceHistoryMapper(PlaceHistoryMapper placeHistoryMapper) {
    	super();
    	this.placeHistoryMapper = placeHistoryMapper;
    }
	
	public long getValue(String str){
		String itemIdValue = "";
		if (str.contains(COLON)) {
			String [] tokens = str.split(COLON);
			itemIdValue = tokens[1];
		}
		else {
			itemIdValue = str;
		}
		try{
			return Long.valueOf(itemIdValue);
		} catch(Exception e){
			return -1;
		}
	}
	
	private Place getDefaultPlace() {
		return new HomePlace();
	}

	@Override
	public Place getPlace(String token) {
		String[] tokens = token.split(DELIMITER); 
		if(tokens.length == 0) {
			return getDefaultPlace();
		}
		String tokenPlace = tokens[0].trim();
		Place nextPlace = null;
        if (tokenPlace.indexOf(PlaceToken.HOME) == 0) {
        	nextPlace = new HomePlace();
        } else if (tokenPlace.indexOf(PlaceToken.LIST) == 0){
        	nextPlace = new ListPlace();
        } else if (tokenPlace.indexOf(PlaceToken.DETAIL) == 0) {
        	long userId = -1;
        	try {
        		userId = Long.parseLong(tokenPlace.substring(10));
            	Window.alert("userId: " + userId);
        	} catch (Exception e) {
        		Window.alert(e.getMessage());
        	}
            nextPlace = new DetailPlace(userId > 0 ? userId : null);
        }
        else {
        	nextPlace = new ListPlace();
        }
        return nextPlace;
	}
	
	@Override
	public String getToken(Place place) {
		String token = placeHistoryMapper.getToken(place);
		if(token != null && token.contains(COLON)){
			if (token.endsWith(COLON)) {
				token = token.replace(COLON, "");
	        }else {
	        	token = token.replace(COLON, "");
	        }
			return token;
		}
		if (place != null && place instanceof ListPlace) {
			return ((ListPlace) place).getToken();
		} else if(place != null && place instanceof DetailPlace) {
			return ((DetailPlace) place).getToken();
		}
		else {
			return ((HomePlace)place).getToken();
		}
	}
}