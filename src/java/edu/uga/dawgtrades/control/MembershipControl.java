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

    public String getError() {
        return error;
    }

}
