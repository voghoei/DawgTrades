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
