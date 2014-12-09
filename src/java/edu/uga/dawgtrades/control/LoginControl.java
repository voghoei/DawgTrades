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

    private Connection conn = null;
    private ObjectModel objectModel = null;
    private Persistence persistence = null;
    private boolean hasError = false;
    private String error;

    public boolean hasError() {
        return this.hasError;
    }

    public String getError() {
        String err = null;
        if (this.hasError) {
            err = this.error;
            this.error = null;
            this.hasError = false;
        }
        return err;
    }

    private void connect() throws DTException{
            this.close();
            this.conn = DbUtils.connect();
            this.objectModel = new ObjectModelImpl();
            this.persistence = new PersistenceImpl(conn,objectModel);
            this.objectModel.setPersistence(persistence);
        
    }
    private void close(){
        try{
            if(this.conn != null) {
                this.conn.close();
            }

        }catch (Exception e){
            System.err.println("Exception: "+e);
        }
    }

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
    
    public RegisteredUser getLoggedInUser(HttpSession session)
            throws ServletException, IOException {
        
        // Get current session
        RegisteredUser currentUser = (RegisteredUser) session.getAttribute("currentSessionUser");
        return currentUser;
    }

    public boolean attemptLogin(String username, String password, HttpSession session) {
        try {
            this.connect()

            Iterator<RegisteredUser> userIter = null;
            RegisteredUser runningUser = null;
            RegisteredUser modelUser = this.objectModel.createRegisteredUser();
            modelUser.setName(username);
            modelUser.setPassword(password);

            userIter = this.objectModel.findRegisteredUser(modelUser);

            if (userIter.hasNext()) {
                runningUser = userIter.next();
                if(runningUser.getIsApproved()) {
                    session.setAttribute("currentSessionUser", runningUser);
                    return true;
                }
                this.hasError = true;
                this.error = "Your account has not been approved yet. Please pay the membership fee and the administrator will approve it.";
                return false;
            } else {
                return false;
            }

        }
        catch(DTException e) {
            this.hasError = true;
            this.error = e.getMessage();
            return null;
        }
        finally {
            this.close();
        }
    }

}
