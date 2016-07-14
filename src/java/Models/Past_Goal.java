package Models;

public class Past_Goal {
    private int goal_history_id;
    private int goal_id;
    private String user_name;
    private String description;
    private double target_weight;
    private String target_date;
    private String group_name;

    
    //Default Constructor
    public Past_Goal(){}
    
    public Past_Goal(int givenHistID, int givenGoalID, String givenUsername,
            String givenDesc, double givenWeight, String givenDate, 
            String givenGroup){
        goal_history_id = givenHistID;
        goal_id = givenGoalID;
        user_name = givenUsername;
        description = givenDesc;
        target_weight = givenWeight;
        target_date = givenDate;
        group_name = givenGroup;
    }
    
    
    /**
     * @return the goal_history_id
     */
    public int getGoal_history_id() {
        return goal_history_id;
    }

    /**
     * @param goal_history_id the goal_history_id to set
     */
    public void setGoal_history_id(int goal_history_id) {
        this.goal_history_id = goal_history_id;
    }

    /**
     * @return the goal_id
     */
    public int getGoal_id() {
        return goal_id;
    }

    /**
     * @param goal_id the goal_id to set
     */
    public void setGoal_id(int goal_id) {
        this.goal_id = goal_id;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the target_weight
     */
    public double getTarget_weight() {
        return target_weight;
    }

    /**
     * @param target_weight the target_weight to set
     */
    public void setTarget_weight(double target_weight) {
        this.target_weight = target_weight;
    }

    /**
     * @return the target_date
     */
    public String getTarget_date() {
        return target_date;
    }

    /**
     * @param target_date the target_date to set
     */
    public void setTarget_date(String target_date) {
        this.target_date = target_date;
    }

    /**
     * @return the group_name
     */
    public String getGroup_name() {
        return group_name;
    }

    /**
     * @param group_name the group_name to set
     */
    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}
