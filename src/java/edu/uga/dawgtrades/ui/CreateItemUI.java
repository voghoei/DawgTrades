package edu.uga.dawgtrades.ui;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.control.CreateItemCtrl;
import edu.uga.dawgtrades.control.CategoryControl;
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
		String error = "Error unknown";
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
//			request.setAttribute("error","List size: "+itemCtrl.getError());
		}else if(itemCtrl.hasError()){
			request.setAttribute("error","Error: "+itemCtrl.getError());

		}
		String categoryId = request.getParameter("id");
		if(categoryId != null){
			try{
				long id = Long.parseLong(categoryId,10);
				CategoryControl catCtrl = new CategoryControl();
				Category category = catCtrl.getCategoryWithID(id);
				request.setAttribute("categoryChosen",category);
				ArrayList<AttributeType> attributeTypes = itemCtrl.getCategoryAttributes(id);
				if(attributeTypes != null){
					request.setAttribute("attributes",attributeTypes);
				}else if(itemCtrl.hasError()){
					request.setAttribute("error","Error: "+itemCtrl.getError());	
				}
			}catch(NumberFormatException e){
				request.setAttribute("error","Invalid category ID. Please try again.");
			}
		}
		String itemName = request.getParameter("name");
		String desc = request.getParameter("desc");
		if(itemName != null){
			//send the itemName, Item Desc, attributes, and category to the control
			RegisteredUser currentUser = (RegisteredUser)session.getAttribute("currentSessionUser");
			long itemId = itemCtrl.attemptItemCreate(request.getParameterMap(),currentUser.getId());	
			if(itemId<0){
				if(itemCtrl.hasError()){
					error = itemCtrl.getError();
				}
				request.setAttribute("error","error: "+error);
				request.getRequestDispatcher("/createItem.ftl").forward(request,response);
				//response.sendRedirect("/createAuction?id="+error);
				return;	
			}
			
			response.sendRedirect("/createAuction?id="+itemId);
			return;
		}
			
		request.getRequestDispatcher("/createItem.ftl").forward(request,response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
				
		doGet(request,response);	
	}
}
