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
public class Meal_Sustenance {
    private int meal_Sustenance_ID;
    private int sustenanceID;
    private double calories_consumed;
    
    //Default constructor
    public Meal_Sustenance(){}
    
    //Accessors
    public int getMealSustenanceID(){
        return meal_Sustenance_ID;
    }
    public int getSustenanceID(){
        return sustenanceID;
    }
    public double getCaloriesConsumed(){
        return calories_consumed;
    }
    //Mutators
}
