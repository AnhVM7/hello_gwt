package com.example.client.activities.detail;

import com.example.client.activities.list.ListPlace;
import com.example.shared.model.User;
import com.example.shared.model.UserDTO;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.Window;

public class DetailPlace extends Place{
	
	private Long userId;

	
	public DetailPlace(Long userId) {
        this.userId = userId;
    }


    public Long getUserId() {
        return userId;
    }


    public String getToken() {
        return "detail?id=" + userId;
    }
	
	public static class Tokenizer implements PlaceTokenizer<DetailPlace> {

		@Override
		public DetailPlace getPlace(String token) {
			
			System.out.println("Tokenizer nhận token: " + token);
			Window.alert("token: " + token);
			if (token != null && token.startsWith("detail?id=")) {
				try {
					Long userId = Long.parseLong(token.substring(10));
					return new DetailPlace(userId);
					
				} catch (Exception e) {
					
				}
			}
			return new DetailPlace(-1L);
		}

		@Override
		public String getToken(DetailPlace place) {
			if (place.getUserId() != null) {
                return "detail?id=" + place.getUserId();
            }
            return "";
		}
	}
}
