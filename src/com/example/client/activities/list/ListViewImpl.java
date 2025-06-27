package com.example.client.activities.list;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Modal;

import com.example.client.GreetingService;
import com.example.client.GreetingServiceAsync;
import com.example.client.Hello_gwt;
import com.example.client.activities.ClientFactory;
import com.example.client.activities.basic.widget.UserWidget;
import com.example.client.activities.detail.DetailPlace;
import com.example.client.activities.home.HomePlace;
import com.example.shared.model.User;
import com.example.shared.model.UserDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListViewImpl extends Composite implements ListView {
	interface ListViewImplUiBinder extends UiBinder<VerticalPanel, ListViewImpl> {
	}

	private static ListViewImplUiBinder uiBinder = GWT.create(ListViewImplUiBinder.class);

	@UiField
	VerticalPanel userListPanel;

	@UiField
	FlexTable userTable;

	public ListViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		
		Button addNewUserButton = new Button("Add New User");
		addNewUserButton.setStyleName("btn btn-success");
		addNewUserButton.addClickHandler(new ClickHandler() {
		    @Override
		    public void onClick(ClickEvent event) {
		        Hello_gwt.CLIENT_FACTORY.getPlaceController().goTo(new HomePlace());
		    }
		});
		
		userListPanel.insert(addNewUserButton, 1);
		
		userTable.getElement().addClassName("table table-success table-striped");

		userTable.setText(0, 0, "Full Name");
		userTable.setText(0, 1, "Email");
		userTable.setText(0, 2, "Age");
		userTable.setText(0, 3, "Address");
		userTable.setText(0, 4, "Gender");
		userTable.setText(0, 5, "Actions");


		fetchUsers(new AsyncCallback<List<UserDTO>>() {

			@Override
			public void onSuccess(List<UserDTO> result) {
				for (int i = 0; i < result.size(); i++) {
					addUserToTable(result.get(i), i + 1);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void fetchUsers(AsyncCallback<List<UserDTO>> callback) {
		GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
		greetingService.getAllUser(new AsyncCallback<List<UserDTO>>() {

			@Override
			public void onSuccess(List<UserDTO> result) {
				callback.onSuccess(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failed to load users: " + caught.getMessage());
				caught.printStackTrace();
				Window.alert("Failed to load users. " + caught.getMessage());
			}
		});
	}

	private void clearTable() {
		int rowCount = userTable.getRowCount();
		for (int i = rowCount - 1; i > 0; i--) {
			userTable.removeRow(i);
		}
	}

	private List<User> getAll() {
		List<User> list = new ArrayList<>();
		list.add(new User("trump", "abcd", 70, "America", "male"));
		list.add(new User("trump1", "abcdefgh", 75, "America", "female"));
		return list;
	}

	private void addUserToTable(UserDTO user, int row) {

		ClientFactory clientFactory = Hello_gwt.CLIENT_FACTORY;
		PlaceController placeController = clientFactory.getPlaceController();
		row = userTable.getRowCount();
		userTable.insertRow(row);

		userTable.setText(row, 0, user.getFullName());
		userTable.setText(row, 1, user.getEmail());
		userTable.setText(row, 2, String.valueOf(user.getAge()));
		userTable.setText(row, 3, user.getAddress());
		userTable.setText(row, 4, user.getGender());

		// Button View
		Button viewButton = new Button("View");
		viewButton.getElement().addClassName("btn btn-info");
		viewButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//
//	            Long userId = user.getId();
//
//	            Place detailPlace = new DetailPlace(userId);
//	            Window.alert(((DetailPlace) detailPlace).getToken());
//	            
//	            Hello_gwt.CLIENT_FACTORY.getPlaceController().goTo(detailPlace);
//				
////				UserWidget userWidget = new UserWidget();
////				userWidget.setData(user);
////				Modal popup = new Modal();
////				popup.add(userWidget);
////				popup.getElement().addClassName("modal-popup");
////				popup.show();
//
//			}
			
			@Override
		    public void onClick(ClickEvent event) {
		        HomePlace place = new HomePlace(user, true);
		        Hello_gwt.CLIENT_FACTORY.getPlaceController().goTo(place);
		    }
		});

		Button editButton = new Button("Edit");
		editButton.getElement().addClassName("btn btn-warning");
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				HomePlace homePlace = new HomePlace(user, false);
				Hello_gwt.CLIENT_FACTORY.getPlaceController().goTo(homePlace);
				
			}
			
		});
		
		// Button Delete
		Button deleteButton = new Button("Delete");
		deleteButton.getElement().addClassName("btn btn-danger");
		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm("Are you want to delete this user?")) {

					GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
					greetingService.deleteUser(user.getId(), new AsyncCallback<Void>() {
						@Override
						public void onSuccess(Void result) {
							Window.alert("Delete successfully");
							clearTable();
							fetchUsers(new AsyncCallback<List<UserDTO>>() {
								@Override
								public void onSuccess(List<UserDTO> result) {
									for (int i = 0; i < result.size(); i++) {
										addUserToTable(result.get(i), i + 1);
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("fail to reload list user");
								}
							});
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("fail to delete user");
						}
					});
				}
			}
		});

		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(viewButton);
		actionPanel.add(editButton);
		actionPanel.add(deleteButton);

		userTable.setWidget(row, 5, actionPanel);

	}

}
