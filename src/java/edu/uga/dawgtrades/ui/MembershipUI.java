/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.dawgtrades.ui;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.CreateItemCtrl;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.control.MembershipControl;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.RegisteredUser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sahar
 */
public class MembershipUI extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("**********************");
//        HttpSession session = request.getSession(true);
        LoginControl ctrl = new LoginControl();
//        if (!ctrl.checkIsLoggedIn(session)) {
//            response.sendRedirect("/login");
//            request.setAttribute("loggedInUser", "");
//            request.removeAttribute("loggedInUser");
//            return;
//        } else {
//            RegisteredUser currentUser = (RegisteredUser) session.getAttribute("currentSessionUser");
//            request.setAttribute("loggedInUser", currentUser);
//        }

        MembershipControl membershipCtrl = new MembershipControl();
        ArrayList<Membership> membership;

        String price = request.getParameter("price");
        
//        ArrayList<String> list = new ArrayList<String>();
//        list.add("sahar");
//        list.add("arash");
//        if ( price != null)
//            list.add(price);
//        request.setAttribute("membershipList", list);
        
        

        try {

            if (price != null) {
                if (!membershipCtrl.attemptToCreateMembership(Float.valueOf(price))) {
                    request.setAttribute("error", "Error: " + membershipCtrl.getError());
                }
            }
            membership = membershipCtrl.getAllMembershipPrices();
            if (membership != null) {
                request.setAttribute("membershipList", membership);
            } else if (membershipCtrl.hasError()) {
                request.setAttribute("error", "Error: " + membershipCtrl.getError());
            }

        } catch (DTException ex) {
            Logger.getLogger(MembershipUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("/membership.ftl").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
