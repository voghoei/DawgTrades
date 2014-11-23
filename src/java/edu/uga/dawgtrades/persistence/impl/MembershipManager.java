package edu.uga.dawgtrades.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;
import java.sql.PreparedStatement;


class MembershipManager {

    private ObjectModel objectModel = null;
    private Connection conn = null;

    public MembershipManager(Connection conn, ObjectModel objectModel) {
        this.conn = conn;
        this.objectModel = objectModel;
    }

    public void save(Membership membership)
            throws DTException {
        String insertMembershipSql = "insert into Membership (price, date ) values ( ?, ? )";
        String updateMembershipSql = "update Membership set price = ?, date = ? where id = ?";
        PreparedStatement stmt = null;
        int inscnt;
        long membershipId;
        java.util.Date mDate = null;

        try {

            if (!membership.isPersistent()) {
                stmt = (PreparedStatement) conn.prepareStatement(insertMembershipSql);
            } else {
                stmt = (PreparedStatement) conn.prepareStatement(updateMembershipSql);
            }

            if (membership.getPrice() != 0.0) // price is unique and non null
            {
                stmt.setFloat(1, membership.getPrice());
            } else {
                throw new DTException("MembershipManager.save: can't save a Membership: price undefined");
            }

            if (membership.getDate() != null) {
                mDate = membership.getDate();
                java.sql.Date sDate = new java.sql.Date(mDate.getTime());
                stmt.setDate(2, sDate);
            } else {
                stmt.setNull(2, java.sql.Types.DATE);
            }

            inscnt = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DTException("MembershipManager.save: failed to save a Membership: " + e);
        }
    }

    public Membership restore()
            throws DTException {
        String selectMembershipSql = "select id, price, date from membership where date = (select Max(date) from membership)";
        Statement stmt = null;
        Membership membership = null;

        try {

            stmt = conn.createStatement();

            if (stmt.execute(selectMembershipSql)) {
                ResultSet r = stmt.getResultSet();
                r.next();
                membership = objectModel.createMembership(r.getFloat(2), r.getDate(3));
                membership.setId(r.getLong(1));
                return membership;
            }
        } catch (Exception e) {      // just in case...
            e.printStackTrace();
            throw new DTException("MembershipManager.restore: Could not restore persistent Membership object; Root cause: " + e);
        }

        throw new DTException("MembershipManager.restore: Could not restore persistent Membership object");
    }

    public void delete(Membership membership)
            throws DTException {
        String deleteMembershipSql = "delete from Membership where id = ?";
        PreparedStatement stmt = null;
        int inscnt;

        if (!membership.isPersistent()) // is the Club object persistent?  If not, nothing to actually delete
        {
            return;
        }

        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteMembershipSql);
            stmt.setLong(1, membership.getId());
            inscnt = stmt.executeUpdate();
            if (inscnt == 1) {
                return;
            } else {
                throw new DTException("MembershipManager.delete: failed to delete a Club");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DTException("MembershipManager.delete: failed to delete a Club: " + e);
        }
    }

}
