package com.example.client;

import java.util.List;

import com.example.shared.model.User;
import com.example.shared.model.UserDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	
	void saveUser(User user);
	
	List<UserDTO> getAllUser();
	
	UserDTO getUserInfo(Long userId);
	
	void deleteUser(Long userId);
}
