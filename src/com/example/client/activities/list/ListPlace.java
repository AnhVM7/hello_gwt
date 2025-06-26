package com.example.client.activities.list;

import com.example.client.activities.home.HomePlace;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ListPlace extends Place {
	public String getToken() {
		return "list";
	}
	
	public static class Tokenizer implements PlaceTokenizer<ListPlace> {

		@Override
		public ListPlace getPlace(String token) {
			// TODO Auto-generated method stub
			return new ListPlace();
		}

		@Override
		public String getToken(ListPlace place) {
			// TODO Auto-generated method stub
			return "list";
		}
	}
}
