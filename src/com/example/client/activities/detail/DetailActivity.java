package com.example.client.activities.detail;

import com.example.client.activities.ClientFactory;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;

public class DetailActivity extends AbstractActivity {
	private ClientFactory clientFactory;
	private DetailView detailView;
	
	 public DetailActivity(ClientFactory clientFactory, Place place) {
		 this.clientFactory = clientFactory;
		 this.detailView = clientFactory.getDetailView();
		 if (place instanceof DetailPlace) {
			 Window.alert(((DetailPlace) place).getUserId() +"-111");
		 }
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget((Widget) detailView);

		
	}
}
