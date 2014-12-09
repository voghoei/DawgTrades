package edu.uga.dawgtrades.ui;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.control.CreateAuctionCtrl;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.Item;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

public class CreateAuctionUI extends HttpServlet{
	private static long itemId;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		LoginControl ctrl = new LoginControl();
		CreateAuctionCtrl auctionCtrl = new CreateAuctionCtrl();
		if(!ctrl.checkIsLoggedIn(session)){
			response.sendRedirect("/login");
			request.setAttribute("loggedInUser","");
			request.removeAttribute("loggedInUser");
			return;
		}else{
			RegisteredUser currentUser = (RegisteredUser)session.getAttribute("currentSessionUser");
			request.setAttribute("loggedInUser",currentUser);
		}
		//if the form hasn't been filled out yet. Just so it doesn't do this part for no reason
		if(request.getParameter("id") != null && request.getParameter("expiration") == null){
			this.itemId = Long.parseLong(request.getParameter("id"));
			Item item = auctionCtrl.getItem(itemId);
			if(!auctionCtrl.itemHasAuction(item)) {
				request.setAttribute("item",item);
			}else{
				request.setAttribute("error", "Invalid item or item already has an auction associated with it.");
			}
		}
		//if the form has been submitted
		if(request.getParameter("expiration") != null){
			Map<String,String[]> parameters = request.getParameterMap();
			//send to database
			if(!auctionCtrl.attemptAuctionCreate(parameters,this.itemId)){
				request.setAttribute("error", "Error: "+auctionCtrl.getError());	
			}else{
				response.sendRedirect("/myAuctions");
				return;
			}
		}
		
		request.getRequestDispatcher("/createAuction.ftl").forward(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doGet(request,response);	
			
	}
}
