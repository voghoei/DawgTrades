package edu.uga.dawgtrades.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.impl.ItemImpl;
import edu.uga.dawgtrades.model.ObjectModel;

public class ItemIterator implements Iterator<Item> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;

    public ItemIterator(ResultSet rs, ObjectModel objectModel)
            throws DTException {

        this.rs = rs;
        this.objectModel = objectModel;

        try {
            this.more = rs.next();
        } catch (Exception e) {
            throw new DTException("ItemIterator: Cannot create bid iterator; root cause: " + e);
        }

    }

    public boolean hasNext() {
        return this.more;
    }

    public Item next() throws NoSuchElementException {
		// SQL result structure: (long) id, (long) user_id, (long) Category_id, (long) Attribute_id,
        // (string) code, (string) name, (string) description
        Item nextItem = new ItemImpl();
        if (this.more) {
            try {
                nextItem.setId(rs.getLong(1));
                nextItem.setOwnerId(rs.getLong(2));
                nextItem.setCategoryId(rs.getLong(3));
                nextItem.setCode(rs.getString(4));
                nextItem.setName(rs.getString(5));
                nextItem.setDescription(rs.getString(6));
                this.more = rs.next();
                return nextItem;
            } catch (Exception e) {
                throw new NoSuchElementException("ItemIterator: No next item; Root Cause: " + e);
            }
        } else {
            throw new NoSuchElementException("ItemIterator: No next item. Did you call next() without checking hasNext()?");
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
