package edu.uga.dawgtrades.ui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.*;
import edu.uga.dawgtrades.model.*;
import java.util.*;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import edu.uga.dawgtrades.model.RegisteredUser;


/**
 *
 * @author sahar
 */
public class CreateAttributeAdminUI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session
        HttpSession session = request.getSession(true);
        LoginControl loginCtrl = new LoginControl();
        CategoryControl catCtrl = new CategoryControl();
        if(!loginCtrl.checkIsLoggedIn(session)){
            response.sendRedirect("/login");
            return;
        }else{
            RegisteredUser currentUser = (RegisteredUser)session.getAttribute("currentSessionUser");
            if(!currentUser.getIsAdmin()) {
                response.sendRedirect("/");
                return;
            }
            request.setAttribute("loggedInUser",currentUser);
        }

        String categoryID = request.getParameter("categoryID");
        if(categoryID != null) {
            try {
                long id = Long.parseLong(categoryID, 10);
                if(catCtrl.categoryExists(id)) {
                    request.setAttribute("categoryID", Long.valueOf(id).toString());
                    request.getRequestDispatcher("/attributeCreateAdmin.ftl").forward(request, response);
                    return;
                }else{
                    request.setAttribute("error", "Category doesn't exist.");
                    request.setAttribute("returnTo", "/admin/categories");
                    request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                    return; 
                }
            }
            catch(NumberFormatException e) {
                request.setAttribute("error", "Invalid category given: Not a number.");
                request.setAttribute("returnTo", "/admin/categories");
                request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                return; 
            }
        }else {
            request.setAttribute("error", "Category required.");
            request.setAttribute("returnTo", "/admin/categories");
            request.getRequestDispatcher("/genericError.ftl").forward(request, response);
            return; 
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session
        HttpSession session = request.getSession(true);
        LoginControl loginCtrl = new LoginControl();
        CategoryControl catCtrl = new CategoryControl();
        if(!loginCtrl.checkIsLoggedIn(session)){
            response.sendRedirect("/login");
            return;
        }else{
            RegisteredUser currentUser = (RegisteredUser)session.getAttribute("currentSessionUser");
            if(!currentUser.getIsAdmin()) {
                response.sendRedirect("/");
                return;
            }
            request.setAttribute("loggedInUser",currentUser);
        }
        String categoryID = request.getParameter("categoryID");
        String name = request.getParameter("name");
        String isString = request.getParameter("isString");
        if(categoryID != null && name != null) {
            try {
                long id = Long.parseLong(categoryID, 10);

                if(catCtrl.categoryExists(id)) {
                    if(name.isEmpty()) {
                        request.setAttribute("error", "Name is required.");
                        this.doGet(request, response);
                        return; 
                    }
                    AttributeTypeControl attrTypeCtrl = new AttributeTypeControl();
                    if(attrTypeCtrl.createAttributeType(id, name, (isString != null))) {
                        response.sendRedirect("/admin/categories/edit?id=" + new Long(id).toString());
                        return;
                    }else{
                        if(catCtrl.hasError()) {
                            request.setAttribute("error", "Error: " + catCtrl.getError());
                            request.setAttribute("returnTo", "/admin/categories");
                            request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                            return;
                        }else{
                            request.setAttribute("error", "An unknown error occurred.");
                            request.setAttribute("returnTo", "/admin/categories");
                            request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                            return;
                        }
                    }
                }else{
                    request.setAttribute("error", "Category doesn't exist.");
                    request.setAttribute("returnTo", "/admin/categories");
                    request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                    return; 
                }
            }
            catch(NumberFormatException e) {
                request.setAttribute("error", "Invalid category given: Not a number.");
                request.setAttribute("returnTo", "/admin/categories");
                request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                return; 
            }
        }else {
            request.setAttribute("error", "Missing parameters.");
            request.setAttribute("returnTo", "/admin/categories");
            request.getRequestDispatcher("/genericError.ftl").forward(request, response);
            return; 
        }
    }

    @Override
    public String getServletInfo() {
        return "Category UI";
    }// </editor-fold>

}