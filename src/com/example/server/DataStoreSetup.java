package com.example.server;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;

public class DataStoreSetup {
	
	public void test() {
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		KeyFactory keyFactory = datastore.newKeyFactory().setKind("User");
		Key key = keyFactory.newKey("username123");

		Entity userEntity = Entity.newBuilder(key)
		        .set("username", "username123")
		        .set("email", "user@example.com")
		        .build();

		datastore.put(userEntity);
		
	}

}
