package edu.uga.dawgtrades.control;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persistence.Persistence;
import edu.uga.dawgtrades.persistence.impl.DbUtils;
import edu.uga.dawgtrades.persistence.impl.PersistenceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;
import java.text.SimpleDateFormat;

public class CreateAuctionCtrl{

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
	
	public boolean attemptAuctionCreate(Map<String,String[]> parameters){
		boolean created = true;
		try{
			this.connect();
			//create empty auction
			Auction auction = objectModel.createAuction();
			//formate for datetime: yyyy-MM-dd HH:mm:ss
			String date = parameters.get("expiration")[0];
			String time = parameters.get("expiration-time")[0];
			String datetime = date+" "+time;
			double minPrice = Double.parseDouble(parameters.get("minPrice")[0]);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
			long itemId = Long.parseLong( parameters.get("id")[0]);
			Date expDate = formatter.parse(datetime);
			auction.setMinPrice(minPrice);
			auction.setExpiration(expDate);
			auction.setItemId(itemId);
			this.objectModel.storeAuction(auction);
		}catch(DTException e){
			this.error = e.getMessage();
			this.hasError = true;
			created = false;
		}finally{
			this.close();
		}
		return created;
	}
	

	public Item getItem(long id){
		try{
			this.connect();
			Item item = this.objectModel.createItem();
			item.setId(id);
			Iterator<Item> result = this.objectModel.findItem(item);
			while(result.hasNext()){
				item = result.next();
			}
			return item;				
		}catch(DTException e){
			this.error = e.getMessage();
			this.hasError = true;
			return null;
		}finally{
			this.close();
		}
	
	}
	public boolean hasError(){
		return hasError;
	}
	public String getError(){
		return error;
	}

}
