package edu.uga.dawgtrades.control;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persistence.Persistence;
import edu.uga.dawgtrades.persistence.impl.DbUtils;
import edu.uga.dawgtrades.persistence.impl.PersistenceImpl;
import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpSession;

public class ExperienceReportControl {

    private Connection conn = null;
    private ObjectModel objectModel = null;
    private Persistence persistence = null;
    private String error = "Error Unknown";

    private void connect() throws DTException {

        conn = DbUtils.connect();
        objectModel = new ObjectModelImpl();
        persistence = new PersistenceImpl(conn, objectModel);
        objectModel.setPersistence(persistence);

    }

    private void close() {
        try {
            conn.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }
    }

    public Iterator<RegisteredUser> getAllUsers() throws DTException {
        Iterator<RegisteredUser> userIter = null;
        try {
            connect();
            RegisteredUser modelUser = objectModel.createRegisteredUser();
            userIter = objectModel.findRegisteredUser(modelUser);

        } catch (DTException e) {
            error = e.getMessage();

        } finally {
            close();
        }
        return userIter;
    }
    
    public Iterator<ExperienceReport> getAllRepotsOfUser(RegisteredUser user) throws DTException {
        Iterator<ExperienceReport> reportIter = null;
        try {
            connect();
            ExperienceReport modelReport = objectModel.createExperienceReport();
            modelReport.setReviewed(user);
            reportIter = objectModel.findExperienceReport(modelReport);

        } catch (DTException e) {
            error = e.getMessage();

        } finally {
            close();
        }
        return reportIter;
    }
    

    public boolean attemptToWriteExperienceReport(RegisteredUser user, int rate, String report, HttpSession session) throws DTException {
        ExperienceReport modelExperienceReport = null;
        try {

            RegisteredUser currentUser = (RegisteredUser) session.getAttribute("currentSessionUser");
            connect();
            if (currentUser != null) {
                modelExperienceReport = objectModel.createExperienceReport(currentUser, user, rate, report, new Date());

            } else {
                return false;
            }
            objectModel.storeExperienceReport(modelExperienceReport);

            return true;
        } catch (DTException e) {
            error = e.getMessage();
            return false;
        } finally {
            close();
        }

    }

    public String getError() {
        return error;
    }

}
