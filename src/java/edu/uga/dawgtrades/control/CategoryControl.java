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
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author reanimus
 */
public class CategoryControl {

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
    public Category getCategoryWithID(long id) {
        try {
            this.connect();
            Category toFind = this.objectModel.createCategory();
            toFind.setId(id);
            Iterator<Category> results = this.objectModel.findCategory(toFind);
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

    public long getCategoryItemCount(long id) {
        long count = 0;
        try {
            this.connect();
            Category toFind = this.objectModel.createCategory();
            toFind.setId(id);
            Iterator<Category> results = this.objectModel.findCategory(toFind);
            if(results.hasNext()) {
                toFind = results.next();
                Item itemSearch = this.objectModel.createItem();
                itemSearch.setCategoryId(toFind.getId());
                ItemIterator items = (ItemIterator) this.objectModel.findItem(itemSearch);
                while(items.hasNext()) {
                    Item item = items.next();
                    Auction auction = this.objectModel.createAuction();
                    auction.setItemId(item.getId());
                    Iterator<Auction> auctionResult = this.objectModel.findAuction(auction);
                    if(auctionResult.hasNext()) {
                        if(!auctionResult.next().getIsClosed()) {
                            count++;
                        }
                    }
                }
                ArrayList<Category> subCats = this.getCategoriesWithParentID(toFind.getId());
                for(Category cat : subCats) {
                    long catCount = this.getCategoryItemCount(cat.getId());
                    if(catCount > 0) {
                        count += catCount;
                    }
                }
                return count;
            }else{
                return -1;
            }
        }
        catch(DTException e) {
            this.hasError = true;
            this.error = e.getMessage();
            return -1;
        }
        finally {
            this.close();
        }
    }

    public ArrayList<Auction> getCategoryAuctions(long id) {
        long count = 0;
        try {
            this.connect();
            return null;
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

    public ArrayList<Category> getCategoriesWithParentID(long id) {
        try {
            this.connect();
            Category toFind = this.objectModel.createCategory();
            toFind.setParentId(id);
            Iterator<Category> results = this.objectModel.findCategory(toFind);
            ArrayList<Category> out = new ArrayList<Category>();
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

}
