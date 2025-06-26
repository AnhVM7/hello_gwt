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
		
//		userTable.getElement().addClassName("styled-table");
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
		viewButton.getElement().addClassName("table-button");
		viewButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

	            Long userId = user.getId();

	            Place detailPlace = new DetailPlace(userId);
	            Window.alert(((DetailPlace) detailPlace).getToken());
	            
	            Hello_gwt.CLIENT_FACTORY.getPlaceController().goTo(detailPlace);
				
//				UserWidget userWidget = new UserWidget();
//				userWidget.setData(user);
//				Modal popup = new Modal();
//				popup.add(userWidget);
//				popup.getElement().addClassName("modal-popup");
//				popup.show();

			}
		});

		// Button Delete
		Button deleteButton = new Button("Delete");
		deleteButton.getElement().addClassName("table-button");
		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm("Bạn có chắc chắn muốn xóa user này không?")) {

					GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
					greetingService.deleteUser(user.getId(), new AsyncCallback<Void>() {
						@Override
						public void onSuccess(Void result) {
							Window.alert("Đã xóa user thành công!");
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
									Window.alert("Không load lại được danh sách user!");
								}
							});
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Xóa user thất bại!");
						}
					});
				}
			}
		});

		HorizontalPanel actionPanel = new HorizontalPanel();
		actionPanel.add(viewButton);
		actionPanel.add(deleteButton);

		userTable.setWidget(row, 5, actionPanel);

	}

}
