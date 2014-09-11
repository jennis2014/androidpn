package org.androidpn.server.console.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.androidpn.server.model.User;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.UserNotFoundException;
import org.androidpn.server.service.UserService;
import org.androidpn.server.xmpp.presence.PresenceManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class LoginController extends MultiActionController {

	private UserService userService;

	public LoginController() {
		userService = ServiceLocator.getUserService();
	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter("name");
		String password = request.getParameter("password");
		OutputStream stream = null;
		try {
			stream = response.getOutputStream();

			User user;
			user = userService.getUserByUsername(userName);
			if (user.getPassword().equals(password)) {
				stream.write("success".getBytes());
			}
		}  catch (UserNotFoundException e) {
			stream.write("fail".getBytes());
		}
		

	}

}
