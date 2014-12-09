package edu.uga.dawgtrades.control;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persistence.Persistence;
import edu.uga.dawgtrades.persistence.impl.DbUtils;
import edu.uga.dawgtrades.persistence.impl.PersistenceImpl;
import edu.uga.dawgtrades.persistence.impl.ItemIterator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author reanimus
 */
public class AuctionControl {

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

    public long getCategoryIDForAuctionID(long id) {
        Item item = this.getItemForAuctionID(id);
        if(item != null) {
            return item.getCategoryId();
        }
        return -1;
    }

    public ArrayList<Attribute> getAttributesForAuctionID(long id) {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        Item item = this.getItemForAuctionID(id);
        if(item != null) {
            try {
                this.connect();
                Iterator<Attribute> results = this.objectModel.getAttribute(item);
                while(results.hasNext()) {
                    attributes.add(results.next());
                }
                return attributes;
            }
            catch(DTException e) {
                this.hasError = true;
                this.error = e.getMessage();
                return null;
            }
            finally {
                this.close();
            }
        } else {
            return null;
        }
    }

    public Auction getAuctionWithID(long id) {
        try {
            this.connect();
            Auction toFind = this.objectModel.createAuction();
            toFind.setId(id);
            Iterator<Auction> results = this.objectModel.findAuction(toFind);
            if(results.hasNext()) {
                return results.next();
            }else{
                return null;
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

    public ArrayList<AttributeType> getAttributeTypesForCategory(long id) {
        ArrayList<AttributeType> types = new ArrayList<AttributeType>();
        CategoryControl catCtrl = new CategoryControl();
        // We need to get parent's (if any) as well
        long parentID = catCtrl.getParentCategoryIDForID(id);
        Category current = catCtrl.getCategoryWithID(id);
        if(current != null) {
            try {
                this.connect();
                Iterator<AttributeType> results = this.objectModel.getAttributeType(current);
                while(results.hasNext()) {
                     types.add(results.next());
                }
                if(parentID > 0) {
                    ArrayList<AttributeType> parentTypes = this.getAttributeTypesForCategory(parentID);
                    if(parentTypes != null) {
                        types.addAll(parentTypes);
                    }else{
                        return null;
                    }
                }
                return types;
            }
            catch(DTException e) {
                this.hasError = true;
                this.error = e.getMessage();
                return null;
            }
            finally {
                this.close();
            }
        } else {
            return null;
        }

    }

    public ArrayList<Bid> getBidsForAuctionID(long id) {
        Auction auction = this.getAuctionWithID(id);
        if(auction != null) {
            ArrayList<Bid> out = new ArrayList<Bid>();
            try {
                this.connect();
                Bid bidFind = this.objectModel.createBid();
                bidFind.setAuction(auction);
                Iterator<Bid> results = this.objectModel.findBid(bidFind);
                while(results.hasNext()) {
                    out.add(results.next());
                }
                return out;
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
        return null;
    }

    public RegisteredUser getOwnerForAuctionID(long id) {
        Item item = this.getItemForAuctionID(id);
        if(item != null) {
            try {
                this.connect();
                return this.objectModel.getRegisteredUser(item);
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
        return null;
    }
    public Item getItemForAuctionID(long id) {
        Auction auction = this.getAuctionWithID(id);
        if(auction != null) {
            try {
                this.connect();
                return this.objectModel.getItem(auction);
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
        return null;

    }

}
