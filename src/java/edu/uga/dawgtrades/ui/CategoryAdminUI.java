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
public class CategoryAdminUI extends HttpServlet {

    protected HashMap<String, ArrayList> populateHashmapWithCategories(long id) {
        CategoryControl catCtrl = new CategoryControl();
        ArrayList<Category> subCats = catCtrl.getCategoriesWithParentID(id);
        HashMap<String, ArrayList> children = new HashMap<String, ArrayList>();
        if(subCats != null && !subCats.isEmpty()) {
            children.put(Long.valueOf(id).toString(), subCats);
            for(Category cat : subCats) {
                children.putAll(this.populateHashmapWithCategories(children, cat.getId()));
            }
        }
        return children;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session
        HttpSession session = request.getSession(true);
        LoginControl loginCtrl = new LoginControl();
        if(!ctrl.checkIsLoggedIn(session)){
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
        HashMap<String, ArrayList> children = this.populateHashmapWithCategories(0);
        request.setAttribute("categoriesMap", children);
        request.getRequestDispatcher("/categoryAdmin.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Category UI";
    }// </editor-fold>

}
