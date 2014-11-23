package edu.uga.dawgtrades.model;

import java.util.Date;

public interface Membership extends Persistable {

    float getPrice();

    void setPrice(float price);

    Date getDate();

}
