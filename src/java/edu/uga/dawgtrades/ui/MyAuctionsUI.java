package edu.uga.dawgtrades.ui;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.control.MyAuctionsControl;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.RegisteredUser;
import java.util.*;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;

/*
List the open auctions for items owned by the current user.
*/
public class MyAuctionsUI extends HttpServlet{

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
		 RegisteredUser currentUser = (RegisteredUser)session.getAttribute("currentSessionUser");

		long userId = currentUser.getId();
		MyAuctionsControl auctionCtrl = new MyAuctionsControl();
		ArrayList<Auction> auctions = auctionCtrl.getUserAuctions(userId);
		if(auctions != null){
			request.setAttribute("auctions",auctions);
		}else if(auctionCtrl.hasError()){
			request.setAttribute("error",auctionCtrl.getError());
		}
		 request.getRequestDispatcher("/myAuctions.ftl").forward(request,response);

	}

	
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doGet(request,response);
	}
}

