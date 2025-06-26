package com.example.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.example.client.GreetingService;
import com.example.shared.FieldVerifier;
import com.example.shared.model.User;
import com.example.shared.model.UserDTO;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>"
				+ userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	@Override
	public void saveUser(User user) {
		try {
			System.out.println("user: " + user);

			ObjectifyService.run(new Work<User>() {

				@Override
				public User run() {
					ofy().save().entity(user).now();
					return user;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error while saving user");
		}

	}

	@Override
	public List<UserDTO> getAllUser() {

		try {
			System.out.println("day la ham lay danh sach user");
			return ObjectifyService.run(new Work<List<UserDTO>>() {
				@Override
				public List<UserDTO> run() {

					List<User> users = ofy().load().type(User.class).list();
					List<UserDTO> userDTOs = new ArrayList<>();

					for (User user : users) {
						UserDTO userDTO = new UserDTO(user.getFullName(), user.getEmail(), user.getAge(), user.getAddress(),
								user.getGender());
						userDTO.setId(user.getId());
						userDTOs.add(userDTO);
					}

					for (UserDTO user : userDTOs) {
						System.out.println("User: " + user.getFullName() + " id : " + user.getId());
					}
					userDTOs.sort(Comparator.comparing(UserDTO::getFullName));
					return userDTOs;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while fetching users");
			return new ArrayList<>();
		}
	}

	@Override
	public UserDTO getUserInfo(Long userId) {
	    try {
	        return ObjectifyService.run(new Work<UserDTO>() {

	            @Override
	            public UserDTO run() {
	                User user = ofy().load().type(User.class).id(userId).now();
	                if (user != null) {
	                	System.out.println("id: " + user.getId() + " === fullname: " + user.getFullName());
	                    return new UserDTO(user.getFullName(), user.getEmail(), user.getAge(), user.getAddress(), user.getGender());
	                }
	                return null;
	            }
	        });
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	@Override
	public void deleteUser(Long userId) {
		try {
	        ObjectifyService.run(new Work<Void>() {
	            @Override
	            public Void run() {
	                ofy().delete().type(User.class).id(userId).now();
	                System.out.println("xoa thanh cong user voi id: " + userId);
	                
	                return null;
	            }
	        });
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error while deleting user");
	    }
		
	}

//	public List<User> getUser() {
//		List<User> users = new ArrayList<>();
//		
//		Datastore datastore = DatastoreOptions.newBuilder()
//                .setHost("localhost:8081")
//                .setProjectId("maianh-new")
//                .build()
//                .getService();
//		
//		Query<Entity> query = Query.newEntityQueryBuilder()
//                .setKind("User")
//                .build();
//		
//		QueryResults<Entity> results = datastore.run(query);
//		
//		while (results.hasNext()) {
//            Entity entity = results.next();
//            String fullName = entity.getString("fullName");
//            String email = entity.getString("email");
//            int age = Integer.parseInt(entity.getString("age"));
//            String address = entity.getString("address");
//            String gender = entity.getString("gender");
//            
//            User newUser = new User(fullName, email, age, address, gender);
//            users.add(newUser);
//        }
//		System.out.println(users);
//		return users;
//	}

}
