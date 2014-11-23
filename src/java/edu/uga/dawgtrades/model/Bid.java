package edu.uga.dawgtrades.model;

import java.util.Date;

public interface Bid
        extends Persistable {

    float getAmount();

    void setAmount(float amount);

    Date getDate();

    void setDate(Date date);

    boolean isWinning();

    Auction getAuction();

    RegisteredUser getRegisteredUser();
    
    void setRegisteredUser(RegisteredUser registereduser);
    
    public void setAuction(Auction auction);
}
