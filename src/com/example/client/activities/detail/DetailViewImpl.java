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
		
		detailPanel.getElement().addClassName("container mt-4 p-4 border rounded shadow-sm bg-light");
		
		backButton.getElement().addClassName("btn btn-warning");
		backButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Hello_gwt.CLIENT_FACTORY.getPlaceController().goTo(new ListPlace());
				
			}	
		});
	}

	public void setData(UserDTO user) {
	    this.user = user;
	    
	    fullNameLabel.setText(user.getFullName());
	    emailLabel.setText(user.getEmail());
	    ageLabel.setText(String.valueOf(user.getAge()));
	    addressLabel.setText(user.getAddress());
	    genderLabel.setText(user.getGender());
	}
	
}
