package com.example.client.activities.basic.widget;

import java.util.List;

import com.example.client.GreetingService;
import com.example.client.GreetingServiceAsync;
import com.example.client.activities.list.ListViewImpl;
import com.example.shared.model.User;
import com.example.shared.model.UserDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;


public class UserWidget extends Composite {
    interface HomeViewImplUiBinder extends UiBinder<VerticalPanel, UserWidget> {}
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
    
    UserDTO user;
    		

    public UserWidget() {
        initWidget(uiBinder.createAndBindUi(this));
//        fullNameTextBox.getElement().addClassName("textBox");
        genderListBox.addItem("Male");
        genderListBox.addItem("Female");
//        emailTextBox.getElement().getStyle().setFontWeight(FontWeight.BOLD);

        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                String fullName = fullNameTextBox.getText();
                String email = emailTextBox.getText();
                int age = Integer.parseInt(ageTextBox.getText());
                String address = addressTextBox.getText();
                String gender = genderListBox.getSelectedItemText();

                User user = new User();

                if (UserWidget.this.user != null && UserWidget.this.user.getId() != null) {
                    user.setId(UserWidget.this.user.getId());
                }

                user.setFullName(fullName);
                user.setEmail(email);
                user.setAge(age);
                user.setAddress(address);
                user.setGender(gender);
//                Window.alert("hao1");
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
    
    public void setData(UserDTO user) {
    	this.user = user;
    	fullNameTextBox.setValue(user.getFullName());
    	emailTextBox.setValue(user.getEmail());
    	ageTextBox.setValue(String.valueOf(user.getAge()));
    	addressTextBox.setValue(user.getAddress());
    	genderListBox.setSelectedIndex("Male".equals(user.getGender()) ? 0 : 1);
    }

    public void clearForm() {
        fullNameTextBox.setText("");
        emailTextBox.setText("");
        ageTextBox.setText("");
        addressTextBox.setText("");
        genderListBox.setSelectedIndex(0);
    }
}







