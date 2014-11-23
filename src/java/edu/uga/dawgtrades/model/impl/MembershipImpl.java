package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.*;
import java.util.Date;

public class MembershipImpl extends Persistent implements Membership {

    private float price = 0;
    private Date date;

    public MembershipImpl() {
    }

    public MembershipImpl(float price) {
        this.price = price;
    }

    public MembershipImpl(float price, Date date) {
        this.price = price;
        this.date = date;
    }

    
    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return "Menbership [" + getId() + "] " + getPrice() + " " + getDate();
    }

    public boolean equals(Object otherClub) {
        if (otherClub == null) {
            return false;
        }
        if (otherClub instanceof Membership) // name is a unique attribute
        {
            return getPrice() == ((Membership) otherClub).getPrice();
        }
        return false;
    }

}
