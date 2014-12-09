package edu.uga.dawgtrades.ui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.*;
import edu.uga.dawgtrades.ui.*;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import edu.uga.dawgtrades.model.*;
import java.util.*;


/**
 *
 * @author reanimus
 */
public class SearchUI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session
        HttpSession session = request.getSession(true);
        LoginControl ctrl = new LoginControl();
        if(ctrl.checkIsLoggedIn(session))
        {
            RegisteredUser currentUser = (RegisteredUser) session.getAttribute("currentSessionUser");
            request.setAttribute("loggedInUser", currentUser);
        }else{
            request.setAttribute("loggedInUser", "");;
            request.removeAttribute("loggedInUser");
        }

        String chooseCategoryToSearch = request.getParameter("category");
        String selectedCategoryToSearch = request.getParameter("searchCategory");
        if(chooseCategoryToSearch != null) {

        }else if(selectedCategoryToSearch != null){

        }else{
            CategoryControl catCtrl = new CategoryControl();
            HashMap<String, ArrayList> children = catCtrl.populateHashmapWithCategories(0);
            if(children != null) {
                request.setAttribute("categoriesMap", children);
                request.getRequestDispatcher("/searchChooseCategory.ftl").forward(request, response);
            }else{
                if(catCtrl.hasError()) {
                    request.setAttribute("error", "Error getting categories: " + catCtrl.getError());
                    request.setAttribute("returnTo", "/");
                    request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                    return;
                }else{
                    request.setAttribute("error", "Internal error getting categories.");
                    request.setAttribute("returnTo", "/");
                    request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                    return;
                }

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Search UI";
    }// </editor-fold>

}
