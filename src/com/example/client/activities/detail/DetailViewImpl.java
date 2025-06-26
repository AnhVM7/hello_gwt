package com.example.client.activities.detail;

import com.example.client.GreetingService;
import com.example.client.GreetingServiceAsync;
import com.example.client.Hello_gwt;
import com.example.client.activities.list.ListPlace;
import com.example.shared.model.UserDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DetailViewImpl extends Composite implements DetailView {

	interface DetailViewImplUiBinder extends UiBinder<VerticalPanel, DetailViewImpl> {}
	private static DetailViewImplUiBinder uiBinder = GWT.create(DetailViewImplUiBinder.class);
	
	@UiField
	VerticalPanel detailPanel;
	
	@UiField
	Label fullNameLabel, emailLabel, ageLabel, addressLabel, genderLabel;
	
	@UiField
	Button backButton;
	
	
	private UserDTO user;
	public DetailViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
//		detailPanel.clear();
		
		Long userId = getUserIdFromUrl();
		
		if (userId != null) {
			getUserInfo(userId);
		}
		
		backButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Hello_gwt.CLIENT_FACTORY.getPlaceController().goTo(new ListPlace());
				
			}	
		});
	}
	
	public void setData(UserDTO user) {
		this.user = user;
		
	}
//	
//	public UserDTO getUser() {
//		if(validate())
//			return null;
//		return this.user;
//	}
//	
//	private boolean validate() {
//		
//		return true;
//	}
	
	private Long getUserIdFromUrl() {
        String hash = Window.Location.getHash();
        if (hash.contains("detail")) {
            String[] params = hash.split("\\?");
            if (params.length > 1) {
                String[] keyValue = params[1].split("=");
                if (keyValue.length > 1 && keyValue[0].equals("id")) {
                	Long userId = Long.parseLong(keyValue[1]);
                    return userId;
                }
            }
        }
        return null;
	}
	
	private void getUserInfo(Long userId) {
		GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
		greetingService.getUserInfo(userId, new AsyncCallback<UserDTO>() {

			@Override
			public void onSuccess(UserDTO result) {
				fullNameLabel.setText(result.getFullName());
                emailLabel.setText(result.getEmail());
                ageLabel.setText(String.valueOf(result.getAge()));
                addressLabel.setText(result.getAddress());
                genderLabel.setText(result.getGender());	
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			
			
		});
	}
	
}
