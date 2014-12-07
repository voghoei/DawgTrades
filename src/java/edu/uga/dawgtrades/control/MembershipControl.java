package edu.uga.dawgtrades.control;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persistence.Persistence;
import edu.uga.dawgtrades.persistence.impl.DbUtils;
import edu.uga.dawgtrades.persistence.impl.PersistenceImpl;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpSession;

public class MembershipControl {

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

    public ArrayList<Membership> getAllMembershipPrices() throws DTException {
        Iterator<Membership> membershipIter = null;
        ArrayList<Membership> membershipMap = new ArrayList<Membership>();
        try {
            connect();
            Membership modelMembership = objectModel.createMembership();
            membershipIter = objectModel.findMembership(modelMembership);            
			while(membershipIter.hasNext()){				
				membershipMap.add(membershipIter.next());
			}

        } catch (DTException e) {
            error = e.getMessage();

        } finally {
            close();
        }
        return membershipMap;	
    }

    public Iterator<ExperienceReport> getAllRepotsOfUser(long user_id) throws DTException {
        Iterator<ExperienceReport> reportIter = null;
        Iterator<RegisteredUser> userIter = null;
        RegisteredUser runningUser = null;
        try {
            connect();

            RegisteredUser modelUser = objectModel.createRegisteredUser();
            modelUser.setId(user_id);
            userIter = objectModel.findRegisteredUser(modelUser);
            if (userIter.hasNext()) {

                runningUser = userIter.next();
                ExperienceReport modelReport = objectModel.createExperienceReport();
                modelReport.setReviewed(runningUser);
                reportIter = objectModel.findExperienceReport(modelReport);

            } else {
                error = "user not found";
            }

        } catch (DTException e) {
            error = e.getMessage();

        } finally {
            close();
        }
        return reportIter;
    }

    public boolean attemptToWriteExperienceReport(RegisteredUser reviewer, RegisteredUser reviewed, int rate, String report) throws DTException {
        ExperienceReport modelExperienceReport = null;
        try {

            connect();

            modelExperienceReport = objectModel.createExperienceReport(reviewer, reviewed, rate, report, new Date());

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
