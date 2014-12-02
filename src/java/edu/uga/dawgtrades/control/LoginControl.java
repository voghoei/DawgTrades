package edu.uga.dawgtrades.control;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.LoginControl;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sahar
 */
public class LoginControl {

    public boolean checkIsLoggedIn(HttpSession session)
            throws ServletException, IOException {
        
        // Get current session
        RegisteredUser currentUser = (RegisteredUser) session.getAttribute("currentSessionUser");

        if(currentUser != null)
        {
            return true;
        }
        return false;
    }

    public boolean attemptLogin(String username, String password, HttpSession session)
            throws ServletException, IOException, DTException {
        Connection conn = null;
        ObjectModel objectModel = null;
        Persistence persistence = null;
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
                return true;
            } else {
                return false;
            }

        } 
        finally {
            try {
                conn.close();
            } catch (Exception e) {
                System.err.println("Exception: " + e);
            }
        }
    }

}
