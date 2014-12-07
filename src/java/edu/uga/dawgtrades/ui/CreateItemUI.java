package edu.uga.dawgtrades.ui;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.control.CreateItemCtrl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateItemUI extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		LoginControl ctrl = new LoginControl();
		if(!ctrl.checkIsLoggedIn(session)){
			response.sendRedirect("/login");
			request.setAttribute("loggedInUser","");
			request.removeAttribute("loggedInUser");
			return;
		}else{
			RegisteredUser currentUser = (RegisteredUser)session.getAttribute("currentSessionUser");
			request.setAttribute("loggedInUser",currentUser);
		}
		CreateItemCtrl itemCtrl = new CreateItemCtrl();
		ArrayList<Category> categories = itemCtrl.getCategoryList();
		if(categories != null){	
			request.setAttribute("categoryList",categories);
		}else{
			request.setAttribute("error","Error: Category list is null");
			request.setAttribute("categoryList","");
			request.removeAttribute("categoryList");
		}	
		request.getRequestDispatcher("/createItem.ftl").forward(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
			
	}
}
