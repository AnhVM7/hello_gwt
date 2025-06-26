package com.example.client.activities;

import com.example.client.activities.detail.DetailView;
import com.example.client.activities.home.HomeView;
import com.example.client.activities.list.ListView;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	HomeView getHomeView();
	ListView getListView();
	DetailView getDetailView();

	EventBus getEventBus();

	PlaceController getPlaceController();
}
