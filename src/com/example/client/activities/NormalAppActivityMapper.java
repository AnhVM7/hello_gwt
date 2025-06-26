package com.example.client.activities;

import com.example.client.activities.detail.DetailActivity;
import com.example.client.activities.detail.DetailPlace;
import com.example.client.activities.home.HomeActivity;
import com.example.client.activities.home.HomePlace;
import com.example.client.activities.list.ListActivity;
import com.example.client.activities.list.ListPlace;
import com.example.client.activities.list.ListView;
import com.google.gwt.place.shared.Place;

public class NormalAppActivityMapper implements AsyncActivityMapper{
    private ClientFactory clientFactory;

    public NormalAppActivityMapper(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }
    @Override
    public void getActivity(Place place, ActivityCallbackHandler activityCallbackHandler) {
        if (place instanceof HomePlace) {
            activityCallbackHandler.onRecieveActivity(new HomeActivity(clientFactory, place));
        } else if (place instanceof ListPlace) {
        	activityCallbackHandler.onRecieveActivity(new ListActivity(clientFactory, place));
        } else if (place instanceof DetailPlace) {
        	activityCallbackHandler.onRecieveActivity(new DetailActivity(clientFactory, place));
        }
    }
}