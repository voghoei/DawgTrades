/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persistence.Persistence;
import edu.uga.dawgtrades.persistence.impl.DbUtils;
import edu.uga.dawgtrades.persistence.impl.PersistenceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
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
        RegisteredUser currentUser = (RegisteredUser) session.getAttribute("currentSessionUser");

        if(currentUser != null)
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
        RegisteredUser currentUser = (RegisteredUser) session.getAttribute("currentSessionUser");

        if(currentUser != null)
        {
            // Already logged in, redirect.
            response.sendRedirect("/");
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        Connection conn = null;
        ObjectModel objectModel = null;
        Persistence persistence = null;
        ServletContext sc = getServletContext();
        try {

            // get a database connection
            conn = DbUtils.connect();

            // obtain a reference to the ObjectModel module      
            objectModel = new ObjectModelImpl();

            // obtain a reference to Persistence module and connect it to the ObjectModel        
            persistence = new PersistenceImpl(conn, objectModel);

            // connect the ObjectModel module to the Persistence module
            objectModel.setPersistence(persistence);

            Iterator<RegisteredUser> userIter = null;
            RegisteredUser runningUser = null;
            RegisteredUser modelUser = objectModel.createRegisteredUser();
            modelUser.setName(username);
            modelUser.setPassword(password);

            userIter = objectModel.findRegisteredUser(modelUser);

            if (userIter.hasNext()) {
                runningUser = userIter.next();
                session.setAttribute("currentSessionUser", runningUser);
                response.sendRedirect("/");
            } else {
                request.setAttribute("error", "Invalid username/password given.");
                request.getRequestDispatcher("/login.ftl").forward(request, response);
            }

        } catch (DTException e) {
            request.setAttribute("error", "An exception occurred: " + e.getMessage());
            request.getRequestDispatcher("/login.ftl").forward(request, response);
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                System.err.println("Exception: " + e);
            }

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
