package edu.uga.dawgtrades.model;

import java.util.Date;

public interface Auction
        extends Persistable {

    float getMinPrice();

    void setMinPrice(float minPrice);

    Date getExpiration();

    void setExpiration(Date expiration);

    boolean getIsClosed();

    float getSellingPrice();

    long getItemId();

    void setItemId(long itemId);
}
