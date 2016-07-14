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
public class Group  {
    
    private String group_name;
    private String admin_user;

    public Group() {
    }

    public Group(String name, String username) {
        this.group_name = name;
        this.admin_user = username;
    }

    /**
     * @return the group_name
     */
    public String getGroupName() {
        return group_name;
    }

    /**
     * @param name the name to set
     */
    public void setGroupName(String name) {
        this.group_name = name;
    }

    /**
     * @return the admin_user
     */
    public String getAdmin_User() {
        return admin_user;
    }

    /**
     * @param admin_User the admin_User to set
     */
    public void setAdmin_User(String admin_User) {
        this.admin_user = admin_User;
    }
    
    
}
