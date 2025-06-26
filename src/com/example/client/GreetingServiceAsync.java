package com.example.client;

import java.util.List;

import com.example.shared.model.User;
import com.example.shared.model.UserDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	void saveUser(User user, AsyncCallback<Void> callback);
	
	void getAllUser(AsyncCallback<List<UserDTO>> callback);
	
	void getUserInfo(Long userId, AsyncCallback<UserDTO> callback);
	
	void deleteUser(Long userId, AsyncCallback<Void> callback);
}
