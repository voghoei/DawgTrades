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

public class RegisterControl{

	private Connection conn = null;
	private ObjectModel objectModel = null;
	private Persistence persistence = null;


	private void connect() throws DTException{
		try{
			conn = DbUtils.connect();
			objectModel = new ObjectModelImpl();
			persistence = new PersistenceImpl(conn,objectModel);
			objectModel.setPersistence(persistence);
		}
	}
	private void close(){
		try{
			conn.close();
		}catch (Exception e){
			System.err.println("Exception: "+e);
		}
	}
	/** 
		attemptToRegister
		@return true if user was successfully added. false is an error occurred
	*/
	public boolean attemptToRegister(String name, String firstName, String lastName, String password, String email, String phone, boolean canText ){
		connect();
		RegisteredUser modelUser = objectModel.createRegisteredUser();
		modelUser.setName(name);
		modelUser.setFirstName(firstName);
		modelUser.setLastName(lastName);
		modelUser.setPassword(password);
		modelUser.setEmail(email);
		modelUser.setPhone(phone);
		modelUser.setIsAdmin(false);
		modelUser.setCanText(canText);
		
		try{
			persistence.saveRegisteredUser(modelUser);			
		}catch(DTException e){
			return false;
		}finally{
			close();
		}
		return true;
	}
	
	
	

}
