package edu.uga.dawgtrades.ui;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.ExperienceReportControl;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.control.MembershipControl;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.RegisteredUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExperienceReportUI extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId="";
//        HttpSession session = request.getSession(true);
//        LoginControl ctrl = new LoginControl();
//        if (!ctrl.checkIsLoggedIn(session)) {
//            response.sendRedirect("/login");
//            request.setAttribute("loggedInUser", "");
//            request.removeAttribute("loggedInUser");
//            return;
//        } else {
//            RegisteredUser currentUser = (RegisteredUser) session.getAttribute("currentSessionUser");
//            request.setAttribute("loggedInUser", currentUser);
//        }
        fillUsersList(request);
        
        if (request.getParameter("show") != null) {
            userId = request.getParameter("listId");
            request.setAttribute("userselct", userId);
            fillreportList(request,Long.parseLong(userId));  
            
        }
        
//        if (request.getParameter("save") != null) {
//        String reportvalue = request.getParameter("report");
//        request.setAttribute("reportvalue", reportvalue);
//        }
        
        request.getRequestDispatcher("/experience.ftl").forward(request, response);

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
//        HttpSession session = request.getSession(true);
//        LoginControl ctrl = new LoginControl();
//        RegisteredUser runningUser = ctrl.getLoggedInUser(session);
//        Map parameters = request.getParameterMap();
//        long user_id = Long.parseLong(((String[]) parameters.get("user"))[0]);
//
//            if (user_id > 0) {
//                
//                int rate = Integer.parseInt(request.getParameter("rate"));
//		String report = request.getParameter("report");
//		
//		ExperienceReportControl reportCtrl = new ExperienceReportControl();		
//		if(report == null){
//                        
//			request.setAttribute("error","Report can not be empty.");
//			request.getRequestDispatcher("/report.ftl").forward(request,response);
//			return;		
//		}
//		try{
//                    
//			if(reportCtrl.attemptToWriteExperienceReport(runningUser,runningUser, rate, report)){
//                            
//                            response.sendRedirect("/login");
//				//need to add a success message
//			}else{
//				request.setAttribute("error","Registration failed: "+reportCtrl.getError());
//				request.getRequestDispatcher("/register.ftl").forward(request,response);
//			}
//		
//		}catch(DTException e){
//			request.setAttribute("error","An exception occured: " + e.getMessage());
//			request.getRequestDispatcher("/register.ftl").forward(request,response);
//		}
//
//            } else {
//
//            }        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public void fillUsersList(HttpServletRequest request) {
        ExperienceReportControl userCtrl = new ExperienceReportControl();
        ArrayList<RegisteredUser> userList;
        
        try {
            userList = userCtrl.getAllUsers();
            if (userList != null) {
                request.setAttribute("users", userList);
            } else if (userCtrl.hasError()) {
                request.setAttribute("error", "Error: " + userCtrl.getError());
            }

        } catch (DTException ex) {
            Logger.getLogger(ExperienceReportUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillreportList(HttpServletRequest request, long user_id)
            throws ServletException, IOException {

        ExperienceReportControl reportCtrl = new ExperienceReportControl();
        ArrayList<ExperienceReport> reportList;

        try {
            reportList = reportCtrl.getAllRepotsOfUser(user_id);
            if (reportList != null) {
                request.setAttribute("reports", reportList);
                
            } else if (reportCtrl.hasError()) {
                request.setAttribute("error", "Error: " + reportCtrl.getError());
            }

        } catch (DTException ex) {
            Logger.getLogger(ExperienceReportUI.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }

}
