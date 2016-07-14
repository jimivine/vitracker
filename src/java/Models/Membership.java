/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

/**
 *
 * @author Stuart
 */
public class Membership {
    private int membershipID;
    private String groupName;
    private String userName;

    public Membership() {
    }

    public Membership(int membershipID, String groupName, String userName) {
        this.membershipID = membershipID;
        this.groupName = groupName;
        this.userName = userName;
    }
    
    public Membership(String groupName, String userName) {
        this.groupName = groupName;
        this.userName = userName;
    }

    /**
     * @return the membershipID
     */
    public int getMembershipID() {
        return membershipID;
    }

    /**
     * @param membershipID the membershipID to set
     */
    public void setMembershipID(int membershipID) {
        this.membershipID = membershipID;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
}
