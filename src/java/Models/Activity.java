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
public abstract class Activity {
    protected int ActivityID;
    protected String username;
    protected String date;

    //Default constructor
    public Activity(){}
    
    public Activity(int id, String gUsername, String gDate){
        ActivityID = id;
        username = gUsername;
        date = gDate;
    }
    
    
    //Accessors
    public int getActivityID(){
        return ActivityID;
    }
    public String getUsername(){
        return username;
    }
    public String getDate(){
        return date;
    }
    
    //Mutators
    public void setUsername(String given){
        username = given;
    }
    public void setDate(String given){
        date = given;
    }
    
}
