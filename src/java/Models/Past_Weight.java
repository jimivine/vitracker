package Models;

public class Past_Weight {
    
    private int weight_history_id;
    private String user_name;
    private double recorded_weight;
    private String date_recorded;

    public Past_Weight(int pastID, String user, double recordedWeight, String date) {
        weight_history_id = pastID;
        user_name = user;
        recorded_weight = recordedWeight;
        date_recorded = date;
    }

    /**
     * @return the weight_history_id
     */
    public int getWeight_history_id() {
        return weight_history_id;
    }

    /**
     * @param weight_history_id the weight_history_id to set
     */
    public void setWeight_history_id(int weight_history_id) {
        this.weight_history_id = weight_history_id;
    }

    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * @return the recorded_weight
     */
    public double getRecorded_weight() {
        return recorded_weight;
    }

    /**
     * @param recorded_weight the recorded_weight to set
     */
    public void setRecorded_weight(double recorded_weight) {
        this.recorded_weight = recorded_weight;
    }

    /**
     * @return the date_recorded
     */
    public String getDate_recorded() {
        return date_recorded;
    }

    /**
     * @param date_recorded the date_recorded to set
     */
    public void setDate_recorded(String date_recorded) {
        this.date_recorded = date_recorded;
    }
    
}
