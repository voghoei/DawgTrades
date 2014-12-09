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

        CategoryControl catCtrl = new CategoryControl();

        String chooseCategoryToSearch = request.getParameter("category");
        String searchCategory = request.getParameter("searchCategory");
        if(chooseCategoryToSearch != null) {
            try {
                long id = Long.parseLong(chooseCategoryToSearch, 10);
                Category toSearch = catCtrl.getCategoryWithID(id);
                if(toSearch != null) {
                    request.setAttribute("searchCategory", toSearch);

                    AuctionControl auctionCtrl = new AuctionControl();
                    ArrayList<AttributeType> attributeTypes = auctionCtrl.getAttributeTypesForCategory(id);
                    if(attributeTypes == null) {
                        if(catCtrl.hasError()) {
                            request.setAttribute("error", "Error fetching attributes: " + catCtrl.getError());
                            request.setAttribute("returnTo", "/");
                            request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                        }else{
                            request.setAttribute("error", "Internal error while fetching attributes.");
                            request.setAttribute("returnTo", "/");
                            request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                        }
                    }
                    request.setAttribute("attributeTypes", attributeTypes);
                    request.getRequestDispatcher("/searchCategory.ftl").forward(request, response);
                    return;
                }else{
                    if(catCtrl.hasError()) {
                        request.setAttribute("error", catCtrl.getError());
                        request.setAttribute("returnTo", "/search");
                        request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                    }else{
                        request.setAttribute("error", "Category does not exist.");
                        request.setAttribute("returnTo", "/search");
                        request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                    }
                }
            }
            catch(NumberFormatException e) {
                request.setAttribute("error", "Invalid category ID. Please try again.");
            }

        }else if(searchCategory != null){

        }else{
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
