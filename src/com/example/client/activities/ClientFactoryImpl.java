package com.example.client.activities;

import com.example.client.activities.detail.DetailView;
import com.example.client.activities.detail.DetailViewImpl;
import com.example.client.activities.home.HomeView;
import com.example.client.activities.home.HomeViewImpl;
import com.example.client.activities.list.ListView;
import com.example.client.activities.list.ListViewImpl;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

public class ClientFactoryImpl implements ClientFactory {
	
	protected SimpleEventBus eventBus;
	protected PlaceController placeController;
	private HomeView homeView;
	private ListView listView;
	private DetailView detailView;
	
	
	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();
		placeController = new PlaceController(eventBus);
	}
	
	@Override
	public HomeView getHomeView() {
		homeView = new HomeViewImpl();
		return homeView;
	}
	
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}


	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public ListView getListView() {
		listView = new ListViewImpl();
		return listView;
	}

	@Override
	public DetailView getDetailView() {
		detailView = new DetailViewImpl();
		return detailView;
	}

}
