package com.example.client.activities.home;

import com.example.client.GreetingService;
import com.example.client.GreetingServiceAsync;
import com.example.client.Hello_gwt;
import com.example.client.activities.basic.widget.UserWidget;
import com.example.client.activities.list.ListPlace;
import com.example.shared.model.User;
import com.example.shared.model.UserDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;


public class HomeViewImpl extends Composite implements HomeView {
    interface HomeViewImplUiBinder extends UiBinder<VerticalPanel, HomeViewImpl> {}
    private static HomeViewImplUiBinder uiBinder = GWT.create(HomeViewImplUiBinder.class);

    @UiField
    TextBox fullNameTextBox;

    @UiField
    TextBox emailTextBox;

    @UiField
    TextBox ageTextBox;

    @UiField
    TextBox addressTextBox;

    @UiField
    ListBox genderListBox;

    @UiField
    Button saveButton;
    
    @UiField
    Button backButton;
    
    @UiField 
    VerticalPanel homePanel;
    
    UserDTO user;

    public HomeViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        
        homePanel.getElement().addClassName("container mt-4 p-4 border rounded bg-light shadow-sm");
        fullNameTextBox.getElement().addClassName("form-control");
        emailTextBox.getElement().addClassName("form-control");
        ageTextBox.getElement().addClassName("form-control");
        addressTextBox.getElement().addClassName("form-control");
        genderListBox.getElement().addClassName("form-select");

        genderListBox.addItem("Male");
        genderListBox.addItem("Female");
        
        backButton.getElement().addClassName("btn btn-warning me-2");
        backButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm("Do you want to exit?")) {
					Hello_gwt.CLIENT_FACTORY.getPlaceController().goTo(new ListPlace());
				}
			}
        	
        });

        saveButton.getElement().addClassName("btn btn-primary");
        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                String fullName = fullNameTextBox.getText();
                String email = emailTextBox.getText();
                int age = Integer.parseInt(ageTextBox.getText());
                String address = addressTextBox.getText();
                String gender = genderListBox.getSelectedItemText();

                User user = new User();
                
                if (HomeViewImpl.this.user != null &&  HomeViewImpl.this.user.getId() != null) {
                	user.setId(HomeViewImpl.this.user.getId());
                }
                
                user.setFullName(fullName);
                user.setEmail(email);
                user.setAge(age);
                user.setAddress(address);
                user.setGender(gender);

                GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
                greetingService.saveUser(user, new AsyncCallback<Void>() {
                	
                	@Override
                    public void onSuccess(Void result) {
                    	Window.alert("save user succesfully");
                    	Hello_gwt.CLIENT_FACTORY.getPlaceController().goTo(new ListPlace());
                    }
                	
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Failed to save user");
                    }

                });
            }
        });
        

    }
    
    @Override
    public void setData(UserDTO user) {
    	this.user = user;
        fullNameTextBox.setText(user.getFullName());
        emailTextBox.setText(user.getEmail());
        ageTextBox.setText(String.valueOf(user.getAge()));
        addressTextBox.setText(user.getAddress());
        genderListBox.setSelectedIndex("Male".equals(user.getGender()) ? 0 : 1);
    }
    
    public void setReadOnly(boolean readOnly) {
        fullNameTextBox.setEnabled(!readOnly);
        emailTextBox.setEnabled(!readOnly);
        ageTextBox.setEnabled(!readOnly);
        addressTextBox.setEnabled(!readOnly);
        genderListBox.setEnabled(!readOnly);
        saveButton.setVisible(!readOnly);
    }

    @Override
    public void clearForm() {
        fullNameTextBox.setText("");
        emailTextBox.setText("");
        ageTextBox.setText("");
        addressTextBox.setText("");
        genderListBox.setSelectedIndex(0);
    }
}