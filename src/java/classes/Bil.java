/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.sql.rowset.CachedRowSet;

@ManagedBean(name = "Bil", eager = true)
@RequestScoped
public class Bil {

    String subscriptionNo;
    int id;
    int subscriptionId;
    String status;
    String billDate;
    long value;

    public Bil(String subscriptionNo, int id, String status, String billDate, long value) {
        this.subscriptionNo = subscriptionNo;
        this.id = id;
        this.status = status;
        this.billDate = billDate;
        this.value = value;
    }

    public Bil() {
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionNo() {
        return subscriptionNo;
    }

    public void setSubscriptionNo(String subscriptionNo) {
        this.subscriptionNo = subscriptionNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void payBill(int billId) throws SQLException, ClassNotFoundException {
        Connection conn = DbConnection.getConnection();
        PreparedStatement stBills = conn.prepareStatement("UPDATE bils SET STATUS=Paid WHERE id= ?");
        stBills.setInt(1, billId);
        stBills.executeUpdate();
        goToBillsForSub(23);
     }

    public String CheckIfBillHasPayed(int billId) throws SQLException, ClassNotFoundException {

        Connection conn = DbConnection.getConnection();

        String selectquarey = "SELECT * from bils WHERE id=" + billId + "AND STATUS='Not Paided'";
        Statement stat = conn.createStatement();
        ResultSet rsMessage = stat.executeQuery(selectquarey);
        if (rsMessage.next()) {
            return "false";
        } else {
            return "true";
        }
    }

    public String CheckIfThereBilForSub(int subId) throws SQLException, ClassNotFoundException {

        Connection conn = DbConnection.getConnection();

        String selectquarey = "SELECT * from bils WHERE SUBSCRIPTIONID=" + subId + "AND STATUS='Not Paided'";
        Statement stat = conn.createStatement();
        ResultSet rsMessage = stat.executeQuery(selectquarey);
        if (rsMessage.next()) {
            return "true";
        } else {
            return "false";
        }
    }

    public ResultSet showAllBilsForSub(int subId) throws SQLException, ClassNotFoundException {

        Connection conn = DbConnection.getConnection();

        String selectquarey = "SELECT * from bils WHERE SUBSCRIPTIONID=" + subId;
        Statement stat = conn.createStatement();
        ResultSet rsBills = stat.executeQuery(selectquarey);
        CachedRowSet rowSet = null;

        rowSet = new com.sun.rowset.CachedRowSetImpl();
        rowSet.populate(rsBills);
        return rowSet;
    }

    public String addNewBill(int subId) throws SQLException, ClassNotFoundException {

        Connection conn = DbConnection.getConnection();
        long valueLong = (long) (Math.random() * 100L);
        String selectquarey = "INSERT INTO bils(status,SUBSCRIPTIONID,value) VALUES('Not Paided'," + subId + "," + valueLong + ")";

        Statement stat = conn.createStatement();

        stat.executeUpdate(selectquarey, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stat.getGeneratedKeys();
        if (rs.next()) {
            return "";
        } else {
            return "";
        }
    }

    public void goToBillsForSub(int subId) {
        this.subscriptionId = subId;
    }

    public ResultSet getAllBillsForSub(int subId) throws SQLException, ClassNotFoundException {
        Connection conn = DbConnection.getConnection();
        String subscriptionQuarey = "SELECT "
                + "  bils.*, "
                + "  bils.id as billId, "
                + "Subscriptions.SUBSCRIPTIONNO, "
                + "users.firstname, "
                + "users.lastname "
                + "FROM bils "
                + "JOIN Subscriptions "
                + "  ON bils.SUBSCRIPTIONID = Subscriptions.id "
                + "JOIN clients "
                + "  ON Subscriptions.clientid = clients.id "
                + "JOIN users "
                + "  ON users.id = clients.userId"
                + "  WHERE bils.SUBSCRIPTIONID=" + subId
                +"ORDER BY bils.CREATEDAT desc";

        Statement stat = conn.createStatement();
        ResultSet rsSubscription = stat.executeQuery(subscriptionQuarey);
        CachedRowSet rowSet = null;

        rowSet = new com.sun.rowset.CachedRowSetImpl();
        rowSet.populate(rsSubscription);
        return rowSet;
    }

}
