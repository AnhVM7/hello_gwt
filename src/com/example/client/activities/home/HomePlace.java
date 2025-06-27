package com.example.client.activities.home;

import com.example.shared.model.UserDTO;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class HomePlace extends Place {
	private UserDTO user;
	private boolean readOnly;
	
	public HomePlace(UserDTO user, boolean readOnly) {
        this.user = user;
        this.readOnly = readOnly;
    } 
	
	public HomePlace() {
    }
	
	public UserDTO getUser() {
        return user;
    }
	
	public boolean isReadOnly() {
        return readOnly;
    }

    public String getToken() {
        return "home";
    }

    public static class Tokenizer implements PlaceTokenizer<HomePlace> {
        @Override
        public HomePlace getPlace(String token) {
            return new HomePlace();
        }

        @Override
        public String getToken(HomePlace place) {
            return "home";
        }
    }
}
