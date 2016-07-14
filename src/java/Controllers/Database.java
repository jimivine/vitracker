package Controllers;

import Models.Activity;
import Models.Exercise_Session;
import Models.Exercise_Type;
import Models.Goal;
import Models.Goal.Type;
import Models.Group;
import Models.Membership;
import Models.Past_Goal;
import Models.Past_Weight;
import Models.Registered_Meal;
import Models.Sustenance;
import Models.User;
import java.net.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;

public class Database {

    //Default constructor
    public Database() {
    }

    //DATABASE LOGIC METHODS BELOW
    //Checks if the provided credentials appear in our database
    public boolean validateUser(String userName, String password) {
        try {
            String sql = "SELECT user_name,password FROM users\n"
                    + "WHERE user_name = '" + userName
                    + "' AND password ='" + password + "'";
            ResultSet rs;
            rs = runQuery(sql, getConnection());

            //If the result set contains an entry and thus the user exists
            if (rs.first()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("validateUser error:" + ex);
        } catch (ServletException ex) {
            System.out.println("validateUser error:" + ex);
        }
        return false;
    }

    //Gets the user's details from the database and
    //returns them as a User object
    public User getUser(String username) {
        User aUser;
        try {
            String sql = "SELECT * FROM users"
                    + " WHERE user_name = '" + username + "';";
            ResultSet rs;
            rs = runQuery(sql, getConnection());

            //If nothing found return null
            if (!rs.first()) {
                return null;
            }

            String password = rs.getString("password");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String email = rs.getString("email");
            double weight = rs.getDouble("weight");
            double height = rs.getDouble("height");
            int age = rs.getInt("age");
            String activeLevel = rs.getString("activeLevel");
            String gender = rs.getString("gender");
            aUser = new User(username, password, firstName, lastName, email, weight, height, age, gender, activeLevel);
        } catch (ServletException ex) {
            System.out.println("getUser error:" + ex);
            return null;
        } catch (SQLException ex) {
            System.out.println("getUser error:" + ex);
            return null;
        }
        return aUser;
    }

    //Registers a new user into the database
    public void registerUser(User user) {
        String sql;
        sql = "INSERT INTO users(user_name, password,\"firstName\", \"lastName\", email, weight,\"activeLevel\",gender,age, height) "
                + "VALUES('" + user.getUsername() + "', '" + user.getPassword() + "', '"
                + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getEmail() + "', '"
                + user.getWeight() + "', '" + user.getActiveLevel() + "', '" + user.getGender() + "', '" + user.getAge() + "', '" + user.getHeight() + "');";
        try {
            runUpdateQuery(sql, getConnection());
        } catch (ServletException ex) {
            System.out.println("Register User error: " + ex);
        }
    }

    public void updateUser(User user) {
        try {
            String sql;
            sql = "UPDATE users \n"
                    + "SET \"firstName\"='" + user.getFirstName()
                    + "', \"lastName\"='" + user.getLastName()
                    + "', email='" + user.getEmail()
                    + "', weight='" + user.getWeight()
                    + "', age='" + user.getAge()
                    + "', gender='" + user.getGender()
                    + "', \"activeLevel\"='" + user.getActiveLevel()
                    + "', height='" + user.getHeight() + "'\n"
                    + "WHERE user_name = '" + user.getUsername() + "';";
            runUpdateQuery(sql, getConnection());
        } catch (ServletException ex) {
            System.out.println("updateUser error: " + ex);
        }
    }

    //Returns true if this user needs to update their weight
    //Otherwise returns false
    public boolean weightCheckRequired(String username) {
        try {
            String sql;
            //See if the database has a weight entry for the past two weeks
            //for this user
            sql = "SELECT * FROM past_weight "
                    + "WHERE user_name = '" + username + "' "
                    + "AND date_recorded >= now()::date - 14";
            ResultSet rs = runQuery(sql, getConnection());

            //If no entry was found
            //User requires a new weight entry
            if (rs.next()) {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("checkWeight error: " + ex);
            return false;
        }
        return true;
    }

    //Returns true if success
    //Else false if error
    public boolean recordPastWeight(String username, double weight) {
        try {
            String sql;
            sql = "INSERT INTO past_weight(user_name, recorded_weight, date_recorded) "
                    + "VALUES ('" + username + "', '" + weight + "', now()::date)";
            runUpdateQuery(sql, getConnection());
            return true;
        } catch (ServletException ex) {
            System.out.println("recordPastWeight error: " + ex);
            return false;
        }
    }

    //Returns the past weights recorded for a given username
    public ArrayList<Past_Weight> getPastWeights(String username) {
        try {
            String sql;
            sql = "SELECT * FROM past_weight "
                    + "WHERE user_name = '" + username + "' "
                    + "ORDER BY date_recorded ASC";
            ResultSet rs = runQuery(sql, getConnection());

            ArrayList<Past_Weight> pastWeights = new ArrayList();
            while (rs.next()) {
                int pastID = rs.getInt("weight_history_id");
                String user = rs.getString("user_name");
                double recordedWeight = rs.getDouble("recorded_weight");
                String date = rs.getString("date_recorded");

                Past_Weight aPastWeight = new Past_Weight(pastID, user, recordedWeight, date);

                pastWeights.add(aPastWeight);
            }
            return pastWeights;
        } catch (Exception ex) {
            System.out.println("getPastWeights error: " + ex);
            return null;
        }
    }

    //For getting all available exercise types from the database
    public ArrayList<Exercise_Type> getAvailableExercises() {
        try {
            String sql;
            sql = "SELECT * FROM exercise_type";
            ResultSet rs;
            rs = runQuery(sql, getConnection());

            //For storing all the available exercises
            ArrayList<Exercise_Type> exercises = new ArrayList();

            while (rs.next()) {
                //Get the exercise ID
                int exerciseID = Integer.valueOf(rs.getString("exerciseID"));
                //Get the name of the exercise
                String name = rs.getString("name");
                //Get the calories burned per minute of the exercise
                double caloriesPerMinute = Double.valueOf(rs.getString("caloriesPerMinute"));
                //Add a new exercise object to our arraylist
                exercises.add(new Exercise_Type(exerciseID, name, caloriesPerMinute));
            }

            return exercises;

        } catch (Exception ex) {
            //Change this later
            System.out.println("getAvailableExercises error: " + ex);
        }
        return null;//garbage
    }

    //Add activity parts of an activity to the database
    //Returns the activity id of the created activity entry
    public int addActivity(Activity activity) {
        try {
            String sql;

            sql = "INSERT INTO activity(username, date) "
                    + "VALUES ('" + activity.getUsername() + "', '"
                    + activity.getDate() + "')"
                    + "RETURNING \"activityID\";";
            Database db = new Database();
            ResultSet rs = runQuery(sql, db.getConnection());

            rs.first();
            System.out.println("activity id: " + rs.getInt("activityID"));
            return rs.getInt("activityID");
        } catch (Exception ex) {
            System.out.println("addActivity error: " + ex);
        }
        return -1;//Failure return
    }

    //Add exercise activity to the database
    public boolean registerExercise(Exercise_Session e) {
        try {

            //Update the activity table
            int activityID = addActivity(e);

            //Update the exercise_session table
            String sql;
            sql = "INSERT INTO exercise_session(\"activityID\", \"exerciseID\", duration, distance) "
                    + "VALUES('" + activityID + "','"
                    + e.getExerciseID() + "', '"
                    + e.getDuration() + "', '"
                    + e.getDistance()
                    + "');";
            Database db = new Database();
            db.runUpdateQuery(sql, db.getConnection());

            //Return true for success
            return true;
        } catch (Exception ex) {
            System.out.println("registerExercise error: " + ex);
            //Return false for failure
            return false;
        }
    }

    //Get ResultSet of User's exercise history
    public ResultSet getUserExerciseHistory(User user) {
        try {
            String sql;
            //Get all the user's store exercise information
            sql = "SELECT * FROM activity \n"
                    + "INNER JOIN exercise_session ON (activity.\"activityID\" = exercise_session.\"activityID\") \n"
                    + "INNER JOIN exercise_type ON (exercise_session.\"exerciseID\" = exercise_type.\"exerciseID\")\n"
                    + "WHERE activity.username = '" + user.getUsername() + "'";
            ResultSet rs;
            Database db = new Database();
            rs = db.runQuery(sql, db.getConnection());
            return rs;
        } catch (Exception ex) {
            //There was an error
            System.out.println("getUserExerciseHistory error: " + ex);
            return null;
        }
    }

    //Get the sustenance options the user can choose from
    //Takes sustenance defined by SYSTEM and by given username
    public ArrayList<Sustenance> getSustenanceChoices(String username) {
        try {
            String sql;
            sql = "SELECT * FROM sustenance \n"
                    + "WHERE created_by = 'SYSTEM' OR created_by = '" + username + "'";
            ResultSet rs;
            Database db = new Database();
            rs = db.runQuery(sql, db.getConnection());

            //For storing all the available exercises
            ArrayList<Sustenance> sustenances = new ArrayList();

            //For each found sustenance
            while (rs.next()) {
                //Get details of sustenance
                int sustID = rs.getInt("sustenance_id");
                String name = rs.getString("name");
                Sustenance s = new Sustenance(sustID, name);

                //Add this to the list
                sustenances.add(s);
            }

            //Return list
            return sustenances;
        } catch (Exception ex) {
            System.out.println("getSustenanceChoices error: " + ex);
            return null;
        }
    }

    //Get the full calory count of all meals in a day for a given user and date
    public double getFullCaloryCount(String username, String date) {

        try {
            String sql;
            sql = "SELECT * FROM activity\n"
                    + "INNER JOIN registered_meal ON (registered_meal.\"activityID\" = activity.\"activityID\")\n"
                    + "INNER JOIN meal_sustenance ON (registered_meal.\"meal_sustenance_ID\" = meal_sustenance.\"meal_sustenance_ID\")\n"
                    + "WHERE activity.date = '" + date + "'\n"
                    + "AND activity.username = '" + username + "'";
            Database db = new Database();
            ResultSet rs = db.runQuery(sql, db.getConnection());

            double calorySum = 0;
            while (rs.next()) {
                calorySum += rs.getDouble("calories_consumed");
            }
            return calorySum;
        } catch (Exception ex) {
            System.out.println("getFullCaloryCount error: " + ex);
            return -1;
        }
    }

    //Get items in given meal
    //Username of user
    //Date given in format DD/MM/YY
    //Type chosen from 'breakfast', 'lunch', 'dinner', 'snacks'
    public ResultSet getSustenanceInMealType(String username, String date, String type) {
        try {
            String sql;
            sql = "SELECT sustenance.name, meal_sustenance.calories_consumed\n"
                    + "FROM sustenance\n"
                    + "INNER JOIN meal_sustenance ON sustenance.sustenance_id = meal_sustenance.sustenance_id\n"
                    + "INNER JOIN registered_meal ON meal_sustenance.\"meal_sustenance_ID\" = registered_meal.\"meal_sustenance_ID\"\n"
                    + "INNER JOIN activity ON registered_meal.\"activityID\" = activity.\"activityID\"\n"
                    + "WHERE registered_meal.type = '" + type + "'\n"
                    + "AND activity.date = '" + date + "'\n"
                    + "AND activity.username = '" + username + "'";
            ResultSet rs;
            Database db = new Database();
            rs = db.runQuery(sql, db.getConnection());
            return rs;
        } catch (Exception ex) {
            //There was an error
            System.out.println("getSustenanceInMeal error: " + ex);
            return null;
        }
    }

    public void addSustenanceToMeal(String username, String date, String type, int sustenanceID, double caloriesConsumed) {
        try {
            String sql;
            //Check for registered meals on given date and of given type belonging to this user
            sql = "SELECT registered_meal.\"meal_sustenance_ID\"\n"
                    + " FROM registered_meal\n"
                    + " INNER JOIN meal_sustenance ON meal_sustenance.\"meal_sustenance_ID\" = registered_meal.\"meal_sustenance_ID\"\n"
                    + " INNER JOIN activity ON registered_meal.\"activityID\" = activity.\"activityID\"\n"
                    + " WHERE registered_meal.type = '" + type + "'\n"
                    + " AND activity.date = '" + date + "'\n"
                    + " AND activity.username = '" + username + "'";
            ResultSet rs;
            Database db = new Database();
            rs = db.runQuery(sql, db.getConnection());

            //If a meal already exists for this date and type
            if (rs.first()) {
                System.out.println("Meal already made for this date and type...");
                //Add given sustenance to this registered meal's meal_sustenance_ID
                sql = "INSERT INTO meal_sustenance(\"meal_sustenance_ID\", sustenance_id, calories_consumed)\n"
                        + "VALUES (" + rs.getInt("meal_sustenance_ID") + ",'" + sustenanceID + "', '" + caloriesConsumed + "')";
                db.runUpdateQuery(sql, db.getConnection());
            } else {
                //Otherwise if meal does not exist

                //Create entry in activity table for this activity...
                Registered_Meal newMeal = new Registered_Meal(username, date, type);
                int activityID = addActivity(newMeal);

                //Create a registered_meal and return the meal sustenance id it creates...
                sql = "INSERT INTO registered_meal(\"activityID\", type)\n"
                        + "VALUES('" + activityID + "', '" + type + "')"
                        + "RETURNING \"meal_sustenance_ID\"";
                ResultSet result = db.runQuery(sql, db.getConnection());
                System.out.println("attempting to get meal id");
                result.first();
                int mealSustID = result.getInt("meal_sustenance_ID");

                //Add given sustenance to this registered meal using the obtained meal sustenance id...
                sql = "INSERT INTO meal_sustenance(\"meal_sustenance_ID\", sustenance_id, calories_consumed)\n"
                        + "VALUES ('" + mealSustID + "','" + sustenanceID + "', '" + caloriesConsumed + "')";
                db.runUpdateQuery(sql, db.getConnection());
            }
        } catch (Exception ex) {
            //There was an error
            System.out.println("addSustenanceToMeal error: " + ex);
        }
    }

    //Create and associate a custom sustenance with a user
    public boolean addCustomSustenance(Sustenance sustenance) {
        try {

            //Create custom sustenance
            String sql;
            sql = "INSERT INTO sustenance(name, created_by)\n"
                    + "VALUES('" + sustenance.getName() + "','"
                    + sustenance.getCreatedBy() + "')";
            Database db = new Database();
            db.runUpdateQuery(sql, db.getConnection());

            //Return true for success
            return true;
        } catch (Exception ex) {
            System.out.println("addCustomSustenance error: " + ex);
            //Return false for failure
            return false;
        }
    }

    //Register a new group
    public boolean registerGroup(Models.Group group) {
        try {
            //New entry in group table
            String sql = "INSERT INTO groups(group_name, admin_user)\n"
                    + " VALUES('" + group.getGroupName() + "', '"
                    + group.getAdmin_User() + "')";
            Database db = new Database();
            db.runUpdateQuery(sql, db.getConnection());

            //Return true for success
            return true;
        } catch (Exception ex) {
            System.out.println("registerGroup error: " + ex);
            return false;
        }
    }

    //Register a new group membership
    public boolean registerMembership(Models.Membership membership) {
        try {
            //Check if this membership already exists
            String sql = "SELECT * FROM group_membership "
                    + " WHERE group_name = '" + membership.getGroupName() + "' "
                    + "AND user_name = '" + membership.getUserName() + "'";

            Database db = new Database();
            ResultSet rs = db.runQuery(sql, db.getConnection());
            //If query returns a result
            if (rs.first()) {
                //Membership already exists so output error
                throw new Exception("Membership already exists for this group and user...");
            }

            //Check to see if the user is the creator of the group
            sql = "SELECT * FROM groups "
                    + " WHERE group_name = '" + membership.getGroupName() + "' "
                    + "AND admin_user = '" + membership.getUserName() + "'";
            rs = db.runQuery(sql, db.getConnection());
            //If query returns a result
            if (rs.first()) {
                //Membership already exists so output error
                throw new Exception("Group creator can't register a membership...");
            }

            //New entry in group membership table
            sql = "INSERT INTO group_membership(group_name, user_name)\n"
                    + " VALUES('" + membership.getGroupName() + "','"
                    + membership.getUserName() + "')";

            db.runUpdateQuery(sql, db.getConnection());
            return true;
        } catch (Exception ex) {
            System.out.println("registerMembership error: " + ex);
            return false;
        }
    }

    //Get a group's details from the group name
    public Group getGroup(String group_name) {
        try {
            String sql;
            sql = "SELECT * FROM groups"
                    + " WHERE group_name = '" + group_name + "'";
            Database db = new Database();
            ResultSet rs = db.runQuery(sql, db.getConnection());
            if (rs.next()) {
                String groupname = rs.getString("group_name");
                String admin_user = rs.getString("admin_user");
                Group group = new Group(groupname, admin_user);
                return group;
            }
            throw new Exception("Group not found");
        } catch (Exception ex) {
            System.out.println("getGroup error: " + ex);
            return null;
        }
    }

    //Get all user's in a given group
    public ArrayList<User> getGroupMembers(String groupName) {
        try {
            String sql;
            //Select all users belonging to given group name
            sql = "SELECT DISTINCT users.* from group_membership\n"
                    + "INNER JOIN users ON (users.user_name = group_membership.user_name)\n"
                    + "WHERE group_membership.group_name =  '" + groupName + "'";

            //Execute query
            Database db = new Database();
            ResultSet rs = db.runQuery(sql, db.getConnection());

            //Store all found users in an arraylist
            ArrayList<User> users = new ArrayList();

            while (rs.next()) {
                String user_name = rs.getString("user_name");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                double height = rs.getDouble("height");
                double weight = rs.getDouble("weight");
                User aUser = new User(user_name, firstName, lastName, email, height, weight);
                users.add(aUser);
            }

            //Return found users
            return users;
        } catch (Exception ex) {
            System.out.println("getGroupMembers error: " + ex);
            return null;
        }
    }

    //Get user's group memberships
    public ArrayList<Membership> getUserMemberships(String user_name) {
        try {
            String sql;
            sql = "SELECT * FROM group_membership "
                    + "WHERE user_name = '" + user_name + "'";
            Database db = new Database();
            ResultSet rs = db.runQuery(sql, db.getConnection());
            ArrayList<Membership> memberships = new ArrayList();
            while (rs.next()) {
                int id = rs.getInt("membership_id");
                String group_name = rs.getString("group_name");
                String username = rs.getString("user_name");
                Membership aMembership = new Membership(id, group_name, username);
                memberships.add(aMembership);
            }
            return memberships;
        } catch (Exception ex) {
            System.out.println("getUserMemberships error: " + ex);
            return null;
        }
    }

    //Get created groups
    public ArrayList<Group> getCreatedGroups(String user_name) {
        try {
            String sql;
            sql = "SELECT * FROM groups "
                    + "WHERE admin_user = '" + user_name + "'";
            Database db = new Database();
            ResultSet rs = db.runQuery(sql, db.getConnection());
            ArrayList<Group> groups = new ArrayList();
            while (rs.next()) {
                String group_name = rs.getString("group_name");
                String admin_user = rs.getString("admin_user");
                Group aGroup = new Group(group_name, admin_user);
                groups.add(aGroup);
            }
            return groups;
        } catch (Exception ex) {
            System.out.println("getCreatedGroups error: " + ex);
            return null;
        }
    }

    //Delete a membership given membership id
    public boolean deleteMembership(int membershipID) {
        try {
            String sql;
            Database database = new Database();
            sql = "DELETE FROM group_membership\n"
                    + "WHERE membership_id = '" + membershipID + "'";

            return database.runUpdateQuery(sql, database.getConnection());

        } catch (Exception ex) {
            System.out.println("deleteMembership error: " + ex);
            return false;
        }
    }

    //Delete a group given group name
    public boolean deleteGroup(String group_name) {
        try {
            String sql;
            sql = "DELETE FROM groups "
                    + "WHERE group_name = '" + group_name + "'";
            Database database = new Database();
            return database.runUpdateQuery(sql, database.getConnection());
        } catch (Exception ex) {
            System.out.println("deleteGroup error: " + ex);
            return false;
        }
    }

    //Create a goal
    public boolean createGoal(Goal e) {
        String sql;
        try {
            //If group name not provided
            if (e.getGroup_name() == null) {
                sql = "INSERT INTO goal(user_name, description, target_weight, target_date, type)"
                        + " VALUES('" + e.getUsername() + "','"
                        + e.getDescription() + "','"
                        + e.getTargetWeight() + "','"
                        + e.getTargetDate() + "','"
                        + e.getType() + "')";
            } else {
                //Otherwise if group name provided
                sql = "INSERT INTO goal(user_name, description, target_weight, target_date, group_name, type)"
                        + " VALUES('" + e.getUsername() + "','"
                        + e.getDescription() + "','"
                        + e.getTargetWeight() + "','"
                        + e.getTargetDate() + "','"
                        + e.getGroup_name() + "','"
                        + e.getType() + "')";
            }

            Database db = new Database();
            db.runUpdateQuery(sql, db.getConnection());
            return true;
        } catch (Exception ex) {
            System.out.println("createGoal error: " + ex);
            //failure
            return false;
        }
    }

    //Update an existing goal
    public boolean updateGoal(Goal updated) {
        try {
            Database db = new Database();
            String sql;

            //Get old goal
            sql = "SELECT * FROM goal"
                    + " WHERE goal_id = '" + updated.getGoal_ID() + "'";
            ResultSet rs = db.runQuery(sql, db.getConnection());
            rs.first();

            int oldGoalID = rs.getInt("goal_id");
            String username = rs.getString("user_name");
            String oldDesc = rs.getString("description");
            double oldTargWeight = rs.getDouble("target_weight");
            String oldDate = rs.getString("target_date");
            String oldGroup = rs.getString("group_name");

            if (oldGroup == null) {
                oldGroup = "";
            }

            //Save old goal under history
            sql = "INSERT INTO past_goal(goal_id, user_name, description, target_weight, target_date, group_name)"
                    + " VALUES('" + oldGoalID + "','"
                    + username + "','"
                    + oldDesc + "','"
                    + oldTargWeight + "','"
                    + oldDate + "','"
                    + oldGroup + "')";
            db.runUpdateQuery(sql, db.getConnection());

            //Update the current goal
            sql = "UPDATE goal "
                    + "SET description='" + updated.getDescription() + "', "
                    + "target_weight='" + updated.getTargetWeight() + "', "
                    + "target_date='" + updated.getTargetDate() + "', "
                    + "type='" + updated.getType() + "' "
                    + "WHERE goal_id = '" + updated.getGoal_ID() + "'";
            return db.runUpdateQuery(sql, db.getConnection());

        } catch (Exception ex) {
            System.out.println("updateGoal error: " + ex);
            return false;
        }
    }

    public ArrayList<Past_Goal> getGoalHistory(int goalID) {
        try {
            String sql;
            Database db = new Database();
            ResultSet rs;

            sql = "SELECT * FROM past_goal "
                    + "WHERE goal_id = '" + goalID + "'"
                    + " ORDER BY goal_history_id DESC";

            rs = db.runQuery(sql, db.getConnection());

            ArrayList<Past_Goal> goals = new ArrayList();

            while (rs.next()) {
                //Get attributes
                int histID = rs.getInt("goal_history_id");
                int foundGoalID = rs.getInt("goal_id");
                String username = rs.getString("user_name");
                String description = rs.getString("description");
                double target_weight = rs.getDouble("target_weight");
                String target_date = rs.getString("target_date");
                String group_name = rs.getString("group_name");

                //Create a past goal object
                Past_Goal goal = new Past_Goal(histID, foundGoalID, username,
                        description, target_weight, target_date, group_name);

                //Save it to an arraylist
                goals.add(goal);
            }

            //return arraylist of goal history found
            return goals;
        } catch (Exception ex) {
            System.out.println("getGoalHistory error: " + ex);
            return null;
        }
    }

    //Get all Goals which are set to pass in the next 7 or less days
    public ArrayList<Goal> getUpcomingGoals(String username) {
        try {
            String sql;
            //Get upcoming ACTIVE goals ordered by date ascending
            sql = "SELECT * FROM goal\n"
                    + "WHERE user_name = '" + username + "'\n"
                    + "AND target_date <= now()::date + 7\n"
                    + "AND status = 'ACTIVE'"
                    + "ORDER BY target_date ASC";
            Database db = new Database();
            ResultSet rs = db.runQuery(sql, db.getConnection());
            ArrayList<Goal> goals = new ArrayList();
            while (rs.next()) {
                String description = rs.getString("description");
                double targetWeight = rs.getDouble("target_weight");
                String targetDate = rs.getString("target_date");
                //String user_name,int targetWeight, String description, String targetDate
                Goal aGoal = new Goal(username, targetWeight, description, targetDate);
                goals.add(aGoal);
            }
            return goals;
        } catch (Exception ex) {
            System.out.println("getUpcomingGoals error: " + ex);
            return null;
        }
    }

    //Get all goals of a given status for this user
    public ArrayList<Goal> getStatusGoals(String username, String status) {
        try {
            String sql;
            sql = "SELECT * FROM goal\n"
                    + "WHERE user_name = '" + username + "'\n"
                    + "AND status = '" + status + "' "
                    + "ORDER BY target_date DESC";
            Database db = new Database();
            ResultSet rs = db.runQuery(sql, db.getConnection());
            ArrayList<Goal> foundGoals = new ArrayList();
            while (rs.next()) {
                String description = rs.getString("description");
                String targetDate = rs.getString("target_date");
                double targetWeight = rs.getDouble("target_weight");
                String groupName = rs.getString("group_name");
                Goal aGoal = new Goal(username, status, targetWeight, description, targetDate, groupName);
                aGoal.setGoal_ID(rs.getInt("goal_id"));
                aGoal.setType(Type.valueOf(rs.getString("type")));
                foundGoals.add(aGoal);
            }
            return foundGoals;
        } catch (Exception ex) {
            System.out.println("getStatusGoals error: " + ex);
            return null;
        }
    }

    //          MUST BE RUN BEFORE updateUserGoals()
    //Check for any currently marked active group goals which have passed
    //Then email group with success or failure
    public boolean checkGroupGoals(User user) {

        try {
            String sql;
            Database db = new Database();

            sql = "SELECT * FROM goal "
                    + "WHERE group_name <> 'SYSTEM' "
                    + "AND user_name = '" + user.getUsername() + "' "
                    + "AND target_date < now()::date "
                    + "AND status = 'ACTIVE' ";

            ResultSet rs = db.runQuery(sql, db.getConnection());

            //Connection settings for the Health Tracker's email address
            //We are using googlemail for now
            String SMTP_HOST_NAME = "smtp.gmail.com";
            int SMTP_HOST_PORT = 465;
            String SMTP_AUTH_USER = "vitplushealth";
            String SMTP_AUTH_PWD = "vitpassword";

            //Configuring email settings for Googlemail
            Properties props = new Properties();

            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.host", SMTP_HOST_NAME);
            props.put("mail.smtps.auth", "true");

            while (rs.next()) {
                Session mailSession = Session.getDefaultInstance(props);
                mailSession.setDebug(true);
                Transport transport = mailSession.getTransport();

                //Creating message to send
                MimeMessage message = new MimeMessage(mailSession);
                message.setSubject("Vit+ Group Goal Notification");
                String content;

                //If the goal target weight is less than user's current weight
                if (rs.getDouble("target_weight") < user.getWeight()) {
                    //Goal was a success

                    //Get all group member's for this group
                    ArrayList<User> groupMembers = db.getGroupMembers(rs.getString("group_name"));

                    //If group members were found
                    if (groupMembers != null) {
                        //For all users founds
                        for (User aUser : groupMembers) {
                            //Add this user as a recipient using their email address
                            message.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress(aUser.getEmail()));
                        }

                        content = "<html>"
                                + "<body>"
                                + user.getUsername() + ", "
                                + "User <b>" + user.getFirstName() + " " + user.getLastName() + "</b> "
                                + "was successful at meeting a group goal for group <b>" + rs.getString("group_name") + "</b>"
                                + "</body>"
                                + "</html>";

                        message.setText(content, "utf-8", "html");

                        //Sending message
                        transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
                        transport.sendMessage(message,
                                message.getRecipients(Message.RecipientType.TO));
                    }
                } else {
                    //Goal was a failure

                    ArrayList<User> groupMembers = db.getGroupMembers(rs.getString("group_name"));

                    //If group members were found
                    if (groupMembers != null) {
                        //For all users founds
                        for (User aUser : groupMembers) {
                            //Add this user as a recipient using their email address
                            message.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress(aUser.getEmail()));
                        }

                        content = "<html>"
                                + "<body>"
                                + "User <b>" + user.getFirstName() + " " + user.getLastName() + "</b> "
                                + "was unsuccessful at meeting a group goal for group <b>" + rs.getString("group_name") + "</b>"
                                + "</body>"
                                + "</html>";

                        message.setText(content, "utf-8", "html");

                        //Sending message
                        transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
                        transport.sendMessage(message,
                                message.getRecipients(Message.RecipientType.TO));
                    }
                }
            }

            return true;

        } catch (Exception ex) {
            System.out.println("checkGroupGoals error: " + ex);
            return false;
        }

    }

    //Update a given user's goals based on their current weight and current date
    //This method should be run everytime a user logs in
    public boolean updateUserGoals(User user) {
        try {
            String sql;
            Database db = new Database();

            //Expire any goals which were not met on time
            //For weight LOSS type goals
            sql = "UPDATE goal\n"
                    + "SET status = 'EXPIRED'\n"
                    + "WHERE target_date < now()::date\n"
                    + "AND target_weight < '" + user.getWeight() + "'"
                    + "AND user_name ='" + user.getUsername() + "'"
                    + "AND type = 'LOSS'";
            db.runUpdateQuery(sql, db.getConnection());
            //And weight GAIN type goals
            sql = "UPDATE goal\n"
                    + "SET status = 'EXPIRED'\n"
                    + "WHERE target_date < now()::date\n"
                    + "AND target_weight > '" + user.getWeight() + "' "
                    + "AND user_name ='" + user.getUsername() + "' "
                    + "AND type = 'GAIN'";
            db.runUpdateQuery(sql, db.getConnection());

            //Mark any successful goals as SUCCESSFUL
            //For weight LOSS type goals
            sql = "UPDATE goal\n"
                    + "SET status = 'SUCCESSFUL'\n"
                    + "WHERE target_date < now()::date\n"
                    + "AND target_weight >= '" + user.getWeight() + "' "
                    + "AND user_name = '" + user.getUsername() + "' "
                    + "AND type = 'LOSS'";
            db.runUpdateQuery(sql, db.getConnection());
            //And weight GAIN type goals
            sql = "UPDATE goal\n"
                    + "SET status = 'SUCCESSFUL'\n"
                    + "WHERE target_date < now()::date\n"
                    + "AND target_weight <= '" + user.getWeight() + "' "
                    + "AND user_name = '" + user.getUsername() + "' "
                    + "AND type = 'GAIN'";
            db.runUpdateQuery(sql, db.getConnection());

            return true;

        } catch (Exception ex) {
            System.out.println("updateUserGoals error: " + ex);
            return false;
        }
    }

    //DATABASE ACCESS METHODS BELOW
    //A method for creating database connection
    public Connection getConnection() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new ServletException(String.format("Error: Cannot find JDBC driver..."));
        }
        String username = "postgres"; //Username for database (postgres)
        String password = "dbpassword"; //Password for database (postgres)
        String url = "jdbc:postgresql://localhost/health tracker"; //Url to connect to database
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException ex) {
            throw new ServletException(String.format("Error: Connection to database failed..."));
        }
    }

    //For queries which will add/modify database
    public boolean runUpdateQuery(String sql, Connection connection) throws ServletException {
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(sql);
            connection.close();
            statement.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("runUpdateQuery error: " + ex);
            return false;
        }
    }

    public ResultSet runQuery(String sql, Connection connection) {

        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = statement.executeQuery(sql);
            connection.close();//close connection
        } catch (SQLException ex) {
            System.out.println("runQuery error: " + ex);
        }
        return rs;
    }

    public void executeQuery(String sql, Connection connection) {

        try {
            Connection con = getConnection();
            Statement s = con.createStatement();

            s.execute(sql);

            con.close();
            s.close();
        } catch (ServletException ex) {
            System.out.println("Execute Query error: " + ex);
        } catch (SQLException ex) {
            System.out.println("Execute Query error: " + ex);
        }
    }

}
