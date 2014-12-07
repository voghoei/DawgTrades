package edu.uga.dawgtrades.control;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.impl.CategoryImpl;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persistence.Persistence;
import edu.uga.dawgtrades.persistence.impl.DbUtils;
import edu.uga.dawgtrades.persistence.impl.PersistenceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.HttpSession;
public class CreateItemCtrl{
	
	private Connection conn = null;
	private ObjectModel objectModel = null;
	private Persistence persistence = null;
	private String error="Error Unknown";
	private boolean hasError = false;
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

		boolean created=true;
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
			created = false;
		}finally{
			close();
		}
		return created;
	}
	
	private void addAttributes(Set<Attribute> attributes){	
		// add attributes for this item				
	}
	public ArrayList<Category> getCategoryList(){
		try{
			this.connect();
			Category modelCategory = this.objectModel.createCategory();
			Iterator<Category> categories = this.objectModel.findCategory(modelCategory);
			ArrayList<Category> categoriesMap = new ArrayList<Category>();
			while(categories.hasNext()){
				
				categoriesMap.add(categories.next());
			}
			error = new Integer(categoriesMap).toString();		
			return categoriesMap;	
		}catch(DTException e){
			error = e.getMessage();
			hasError=true;
			return null;
		}finally{
			this.close();
		}
	}
	public String getError(){
		return error;
	}	
	public boolean hasError(){
		return hasError;
	}
}
