package edu.uga.dawgtrades.control;

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
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

public class CommonControl{
	
	public boolean isAdmin(RegisteredUser user)throws ServletException, IOException,DTException{
		Connection conn = null;
		ObjectModel objectModel = null;
		Persistence persistence = null;
		try{
			conn = DbUtils.connect();
			objectModel = new ObjectModelImpl();
			persistence = new PersistenceImpl(conn,objectModel);
			objectModel.setPersistence(persistence);

			Iterator<RegisteredUser> userIter = null;
			RegisteredUser runningUser = null;
			userIter = objectModel.findRegisteredUser(user);
			if(userIter.hasNext()){
				runningUser = userIter.next();
				return runningUser.getIsAdmin();
			}
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){
				System.err.println("Exception: "+e);
			}
		}	
		
		return false;
				
	}
	
}