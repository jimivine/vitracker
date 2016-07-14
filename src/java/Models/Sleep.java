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
public class Sleep extends Activity{
    private int sleepID;
    private int startTime;
    private int endTime;

    public Sleep() {
    }

    public Sleep(int sleepID, int startTime, int endTime) {
        this.sleepID = sleepID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * @return the sleepID
     */
    public int getSleepID() {
        return sleepID;
    }

    /**
     * @param sleepID the sleepID to set
     */
    public void setSleepID(int sleepID) {
        this.sleepID = sleepID;
    }

    /**
     * @return the startTime
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    
}
