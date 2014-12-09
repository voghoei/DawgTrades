package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.*;
import java.util.*;
import java.security.MessageDigest;

/**
 * RegisteredUserImpl generated by hbm2java
 */
public class RegisteredUserImpl extends Persistent implements RegisteredUser {

    private String name;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private boolean canText = false;
    private boolean isAdmin = false;
    private boolean isApproved = false;


    public RegisteredUserImpl() {
    }

    public RegisteredUserImpl(String firstName, String lastName, String name, String password, String email, String phone, boolean canText, boolean isAdmin, boolean isApproved) {

        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
             sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            this.password = sb.toString();
        }
        catch(Exception e)
        {
            this.password = password;
        }
        this.email = email;
        this.canText = canText;
        this.isAdmin = isAdmin;
        this.isApproved = isApproved;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
             sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            this.password = sb.toString();
        }
        catch(Exception e)
        {
            this.password = password;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getCanText() {
        return canText;
    }

    public void setCanText(boolean canText) {
        this.canText = canText;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String toString() {
        return "RegisteredUser[" + getId() + "] " + getFirstName() + " " + getLastName() + " " + getName() + " " + getPassword() + " " + getEmail() + " " + getPhone() + " " + getCanText() + " " + getIsAdmin() + " " + getIsApproved();
    }

    public boolean equals(Object otherRegisteredUser) {
        if (otherRegisteredUser == null) {
            return false;
        }
        if (otherRegisteredUser instanceof RegisteredUser) // name is a unique attribute
        {
            return (getName().equals(((RegisteredUser) otherRegisteredUser).getFirstName())
                    && getName().equals(((RegisteredUser) otherRegisteredUser).getLastName())
                    && getName().equals(((RegisteredUser) otherRegisteredUser).getName())
                    && getName().equals(((RegisteredUser) otherRegisteredUser).getEmail())
                    && getName().equals(((RegisteredUser) otherRegisteredUser).getPassword()));
        }
        return false;
    }

}
