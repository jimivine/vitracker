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
public class Exercise_Session extends Activity{
    private int exercise_ID;
    private int duration;
    private int distance;
    
    //Default constructor
    public Exercise_Session(){}
    
    //When activity ID is not known
    public Exercise_Session(String username, String date, int exercise_ID, int duration, int distance){
        this.username = username;
        this.date = date;
        this.exercise_ID = exercise_ID;
        this.duration = duration;
        this.distance = distance;
    }
    
    //When activity ID is known
    public Exercise_Session(int activityID, String username, String date, int exercise_ID, int duration, int distance){
        this.ActivityID = activityID;
        this.username = username;
        this.date = date;
        this.exercise_ID = exercise_ID;
        this.duration = duration;
        this.distance = distance;
    }
    
    //Accessors
    public int getExerciseID(){
        return exercise_ID;
    }
    public int getDuration(){
        return duration;
    }
    public int getDistance(){
        return distance;
    }
    //Mutators
    public void setDuration(int given){
        duration = given;
    }
    public void setDistance(int given){
        distance = given;
    }
}
