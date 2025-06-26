package com.example.client.activities.list;

import com.example.client.activities.ClientFactory;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;

public class ListActivity extends AbstractActivity {
	private ClientFactory clientFactory;
	private ListView listView;
	
	public ListActivity(ClientFactory clientFactory, Place place) {
		this.clientFactory = clientFactory;
		this.listView = clientFactory.getListView();
	}
	
	

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget((Widget) listView);
		
	}
	
}
