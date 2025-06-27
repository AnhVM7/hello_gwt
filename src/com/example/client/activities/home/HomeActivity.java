package com.example.client.activities.home;

import com.example.client.activities.ClientFactory;
import com.example.shared.model.UserDTO;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;

public class HomeActivity extends AbstractActivity{
	private ClientFactory clientFactory;
    private HomeView homeView;
    
    public HomeActivity (ClientFactory clientFactory, Place place) {
        this.clientFactory = clientFactory;
        this.homeView = clientFactory.getHomeView();
        
        if (place instanceof HomePlace) {
        	HomePlace homePlace = (HomePlace) place;
            UserDTO user = homePlace.getUser();
            boolean readOnly = homePlace.isReadOnly();
            if (user != null) {
                homeView.setData(user);
            }
            
            if (homeView instanceof HomeViewImpl) {
                ((HomeViewImpl) homeView).setReadOnly(readOnly);
            }
        }
    }


	@Override
	public void start(AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus eventBus) {
		panel.setWidget((Widget) homeView);
		
	}
}
