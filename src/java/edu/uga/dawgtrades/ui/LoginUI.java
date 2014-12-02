package edu.uga.dawgtrades.ui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.LoginControl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sahar
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session
        HttpSession session = request.getSession(true);
        LoginControl ctrl = new LoginControl();
        if(ctrl.checkIsLoggedIn(session))
        {
            // Already logged in, redirect.
            response.sendRedirect("/");
            return;
        }

        request.setAttribute("error", "");
        request.removeAttribute("error");
        request.getRequestDispatcher("/login.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session
        HttpSession session = request.getSession(true);
        LoginControl ctrl = new LoginControl();
        if(ctrl.checkIsLoggedIn(session))
        {
            // Already logged in, redirect.
            response.sendRedirect("/");
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            if (ctrl.attemptLogin(username, password, session)) {
                response.sendRedirect("/");
            } else {
                request.setAttribute("error", "Invalid username/password given.");
                request.getRequestDispatcher("/login.ftl").forward(request, response);
            }

        } catch (DTException e) {
            request.setAttribute("error", "An exception occurred: " + e.getMessage());
            request.getRequestDispatcher("/login.ftl").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Login UI";
    }// </editor-fold>

}
