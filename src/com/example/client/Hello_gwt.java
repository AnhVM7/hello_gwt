package com.example.client;

import com.example.client.activities.AppPlaceHistoryMapper;
import com.example.client.activities.AsyncActivityManager;
import com.example.client.activities.AsyncActivityMapper;
import com.example.client.activities.ClientFactory;
import com.example.client.activities.ClientFactoryImpl;
import com.example.client.activities.MyPlaceHistoryMapper;
import com.example.client.activities.NormalAppActivityMapper;
import com.example.client.activities.home.HomePlace;
import com.example.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Hello_gwt implements EntryPoint {
	public static final GreetingServiceAsync GREETING_SERVICES = GWT.create(GreetingService.class);
	public static final ClientFactory CLIENT_FACTORY = new ClientFactoryImpl();

	public void onModuleLoad() {
		
		SimplePanel display = new SimplePanel();
		AsyncActivityMapper activityMapper = new NormalAppActivityMapper(CLIENT_FACTORY);
		AsyncActivityManager activityManager = new AsyncActivityManager(activityMapper, CLIENT_FACTORY.getEventBus());
		activityManager.setDisplay(display);
		RootPanel.get().add(display);
		final PlaceHistoryMapper myHistoryMapper = GWT.create(MyPlaceHistoryMapper.class);
		PlaceHistoryMapper historyMapper = new AppPlaceHistoryMapper(myHistoryMapper);
		final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(CLIENT_FACTORY.getPlaceController(), CLIENT_FACTORY.getEventBus(), new HomePlace());
		historyHandler.handleCurrentHistory();
		
		GREETING_SERVICES.greetServer("11111111", new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
	}
}
