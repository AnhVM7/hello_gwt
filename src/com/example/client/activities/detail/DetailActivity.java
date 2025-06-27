package com.example.client.activities.detail;

import com.example.client.GreetingService;
import com.example.client.GreetingServiceAsync;
import com.example.client.activities.ClientFactory;
import com.example.shared.model.UserDTO;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;

public class DetailActivity extends AbstractActivity {
	private ClientFactory clientFactory;
	private DetailView detailView;

	public DetailActivity(ClientFactory clientFactory, Place place) {
		this.clientFactory = clientFactory;
		this.detailView = clientFactory.getDetailView();

		if (place instanceof DetailPlace) {
			Long userId = ((DetailPlace) place).getUserId();
			GWT.log("userId in DetailActivity: " + userId);

			if (userId != null) {
				getUserInfo(userId);
			}
		}
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget((Widget) detailView);

	}
	
	private void getUserInfo(Long userId) {
        GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
        greetingService.getUserInfo(userId, new AsyncCallback<UserDTO>() {
            @Override
            public void onSuccess(UserDTO result) {
                detailView.setData(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to load user info.");
            }
        });
    }
}
