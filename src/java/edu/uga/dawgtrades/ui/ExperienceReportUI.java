package edu.uga.dawgtrades.ui;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.ExperienceReportControl;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.control.RegisterControl;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.RegisteredUser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sahar
 */

public class ExperienceReportUI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get current session
        HttpSession session = request.getSession(true);
        LoginControl ctrl = new LoginControl();
        RegisteredUser runningUser = ctrl.getLoggedInUser(session);

        ExperienceReportControl reportCtrl = new ExperienceReportControl();

        if (runningUser == null) {
            request.setAttribute("error", "Session expired. Please login again. ");
            request.getRequestDispatcher("/login.ftl").forward(request, response);
        }

        // Fill List of Users
        try {

            Map<String, String> userList = new LinkedHashMap<String, String>();

            Iterator<RegisteredUser> userIter = reportCtrl.getAllUsers();

            while (userIter.hasNext()) {
                runningUser = userIter.next();
                userList.put(String.valueOf(runningUser.getId()), (runningUser.getFirstName() + " " + runningUser.getLastName()));
            }
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("/report.ftl").forward(request, response);

        } catch (DTException e) {
            request.setAttribute("error", "An exception occurred: " + e.getMessage());
            request.getRequestDispatcher("/report.ftl").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        LoginControl ctrl = new LoginControl();
        RegisteredUser runningUser = ctrl.getLoggedInUser(session);
        Map parameters = request.getParameterMap();
        long user_id = Long.parseLong(((String[]) parameters.get("user"))[0]);

            if (user_id > 0) {
                
                int rate = Integer.parseInt(request.getParameter("rate"));
		String report = request.getParameter("report");
		
		ExperienceReportControl reportCtrl = new ExperienceReportControl();		
		if(report == null){
                        
			request.setAttribute("error","Report can not be empty.");
			request.getRequestDispatcher("/report.ftl").forward(request,response);
			return;		
		}
		try{
                    
			if(reportCtrl.attemptToWriteExperienceReport(runningUser,runningUser, rate, report)){
                            
                            response.sendRedirect("/login");
				//need to add a success message
			}else{
				request.setAttribute("error","Registration failed: "+reportCtrl.getError());
				request.getRequestDispatcher("/register.ftl").forward(request,response);
			}
		
		}catch(DTException e){
			request.setAttribute("error","An exception occured: " + e.getMessage());
			request.getRequestDispatcher("/register.ftl").forward(request,response);
		}

            } else {

            }        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public void fillreportList(HttpServletRequest request,HttpServletResponse response, long user_id)
    throws ServletException, IOException {

        ExperienceReportControl reportCtrl = new ExperienceReportControl();
        ExperienceReport report = null;
        Map parameters = request.getParameterMap();

        try {
            List<ExperienceReport> reportList = new ArrayList<ExperienceReport>();
            Iterator<ExperienceReport> reportIter = reportCtrl.getAllRepotsOfUser(user_id);

            while (reportIter.hasNext()) {
                report = reportIter.next();
                reportList.add(report);
            }
            request.setAttribute("userList", ((String[]) parameters.get("user"))[1]);
            request.setAttribute("reportList", reportList);
            request.getRequestDispatcher("/report.ftl").forward(request, response);
        } catch (DTException e) {
            request.setAttribute("error", "An exception occurred: " + e.getMessage());
            request.getRequestDispatcher("/report.ftl").forward(request, response);
        }
    }

}
