package com.example.client.activities.home;

import com.example.client.GreetingService;
import com.example.client.GreetingServiceAsync;
import com.example.shared.model.User;
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

    public HomeViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        genderListBox.addItem("Male");
        genderListBox.addItem("Female");

        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                String fullName = fullNameTextBox.getText();
                String email = emailTextBox.getText();
                int age = Integer.parseInt(ageTextBox.getText());
                String address = addressTextBox.getText();
                String gender = genderListBox.getSelectedItemText();

                User user = new User(fullName, email, age, address, gender);

                GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
                greetingService.saveUser(user, new AsyncCallback<Void>() {
                	
                	@Override
                    public void onSuccess(Void result) {
//                        Window.Location.assign("profile.html");
                    	Window.alert("save user succesfully");
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
    public void clearForm() {
        fullNameTextBox.setText("");
        emailTextBox.setText("");
        ageTextBox.setText("");
        addressTextBox.setText("");
        genderListBox.setSelectedIndex(0);
    }
}