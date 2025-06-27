package com.example.client.activities.home;

import com.example.shared.model.UserDTO;

public interface HomeView {
	public void clearForm();
	
	void setData(UserDTO user);
}
