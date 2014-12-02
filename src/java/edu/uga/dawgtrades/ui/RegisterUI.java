package edu.uga.dawgtrades.ui;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.RegisterControl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterUI extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		RegisterControl ctrl = new RegisterControl();
		request.getRequestDispatcher("/register.ftl").forward(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletReponse response)throws ServletException, IOException{
		
	}
}
