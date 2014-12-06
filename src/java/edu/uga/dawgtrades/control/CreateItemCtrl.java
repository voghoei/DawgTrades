package edu.uga.dawgtrades.control;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persistence.Persistence;
import edu.uga.dawgtrades.persistence.impl.DbUtils;
import edu.uga.dawgtrades.persistence.impl.PersistenceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpSession;
public class CreateItemCtrl{
	
	private Connection conn = null;
	private ObjectModel objectModel = null;
	private Persistence persistence = null;
	private String error="Error Unknown";
	private LoginControl ctrl = new LoginControl();
	private void connect() throws DTException{
		conn = DbUtils.connect();
		objectModel = new ObjectModelImpl();
		persistence = new PersistenceImpl(conn,objectModel);
		objectModel.setPersistence(persistence);
	}
	private void close(){
		try{
			conn.close();
		}catch(Exception e){
			System.err.println("Exception: "+e);
		}
	}
	
	public boolean attemptItemCreate(HttpSession session,Set<Attribute> attributes,Set<Category> categories,String name, String code, String description)throws DTException, ServletException,IOException{
		try{
			connect();
			Item item = objectModel.createItem();
			item.setName(name);
			item.setCode(code);
			item.setDescription(description);
			RegisteredUser currentUser = ctrl.getLoggedInUser(session);
			item.setOwnerId(currentUser.getId());
			//objectModel.storeItem(item);
		}catch(DTException e){
			error = e.getMessage();
			return false;
		}finally{
			close();
		}
		return true;
	}
	
	private void addAttributes(Set<Attribute> attributes){	
		// add attributes for this item				
	}
	
}
