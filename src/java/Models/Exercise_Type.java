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
public class Exercise_Type extends Activity {
    private int exerciseID;
    private String name;
    private double caloriesPerMinute;

   
    
    //Default constructor
    public Exercise_Type(){}
    
    public Exercise_Type(int givenID, String gName, double gCPM){
        exerciseID = givenID;
        name = gName;
        caloriesPerMinute = gCPM;
    }
    
    //Accessors
    public int getExerciseID(){
        return exerciseID;
    }
    public String getName(){
        return name;
    }
    
    public double getCaloriesPerMinute() {
        return caloriesPerMinute;
    }
    //Mutators
    public void setName(String name){
        this.name = name;
    }
     public void setCaloriesPerMinute(double caloriesPerMinute) {
        this.caloriesPerMinute = caloriesPerMinute;
    }

}
