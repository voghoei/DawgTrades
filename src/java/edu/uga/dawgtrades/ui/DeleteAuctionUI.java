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
public class DeleteAuctionUI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session
        HttpSession session = request.getSession(true);
        LoginControl loginCtrl = new LoginControl();
        AuctionControl auctionCtrl = new AuctionControl();
        if(!loginCtrl.checkIsLoggedIn(session)){
            response.sendRedirect("/login");
            return;
        }else{
            RegisteredUser currentUser = (RegisteredUser)session.getAttribute("currentSessionUser");
            request.setAttribute("loggedInUser",currentUser);
        }
        String auctionID = request.getParameter("id");
        if(auctionID != null) {
            try {
                long id = Long.parseLong(auctionID, 10);
                if(auctionCtrl.auctionExists(id)) {
                    if(!auctionCtrl.userCanDelete(currentUser, id)) {
                        request.setAttribute("error", "You are not authorized to delete this auction.");
                        request.setAttribute("returnTo", "/auction?id=" + auctionID);
                        request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                        return; 
                    }
                    request.setAttribute("toDelete", auctionID);
                    request.getRequestDispatcher("/auctionDelete.ftl").forward(request, response);
                }else{
                    request.setAttribute("error", "Auction doesn't exist.");
                    request.setAttribute("returnTo", "/category");
                    request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                    return; 
                }
            }
            catch(NumberFormatException e) {
                request.setAttribute("error", "Bad ID passed.");
                request.setAttribute("returnTo", "/category");
                request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                return;
            }
        }else{
            response.sendRedirect("/category");
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
            request.setAttribute("loggedInUser",currentUser);
        }
        String auctionID = request.getParameter("id");
        if(auctionID != null) {
            try {
                long id = Long.parseLong(auctionID, 10);
                if(auctionCtrl.auctionExists(id)) {
                    if(!auctionCtrl.userCanDelete(currentUser, id)) {
                        if(auctionCtrl.hasError()) {
                            request.setAttribute("error", "Error: " + auctionCtrl.getError());
                            request.setAttribute("returnTo", "/auction?id=" + auctionID);
                            request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                            return;
                        }else{
                            request.setAttribute("error", "You are not authorized to delete this auction.");
                            request.setAttribute("returnTo", "/auction?id=" + auctionID);
                            request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                            return; 
                        }
                    }
                    if(auctionCtrl.deleteAuction(id)) {
                        response.sendRedirect("/category");
                    }else{
                        if(auctionCtrl.hasError()) {
                            request.setAttribute("error", "Error: " + auctionCtrl.getError());
                            request.setAttribute("returnTo", "/auction?id=" + auctionID);
                            request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                            return;
                        }else{
                            request.setAttribute("error", "An unknown error occurred.");
                            request.setAttribute("returnTo", "/auction?id=" + auctionID);
                            request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                            return;
                        }
                    }
                }else{
                    if(auctionCtrl.hasError()) {
                        request.setAttribute("error", "Error: " + auctionCtrl.getError());
                        request.setAttribute("returnTo", "/category");
                        request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                        return;
                    }else{
                        request.setAttribute("error", "Auction doesn't exist.");
                        request.setAttribute("returnTo", "/category");
                        request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                        return; 
                    }
                }
            }
            catch(NumberFormatException e) {
                request.setAttribute("error", "Bad ID passed.");
                request.setAttribute("returnTo", "/category");
                request.getRequestDispatcher("/genericError.ftl").forward(request, response);
                return;
            }
        }else{
            response.sendRedirect("/category");
            return;
        }
    }

    @Override
    public String getServletInfo() {
        return "Category UI";
    }// </editor-fold>

}
