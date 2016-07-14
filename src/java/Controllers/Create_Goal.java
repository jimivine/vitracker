/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Goal;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author adam
 */
@WebServlet(name = "Create_Goal", urlPatterns = {"/Create_Goal"})
public class Create_Goal extends HttpServlet {
    
    
    Database database;
    Goal goal;
    
    //Created for Unit testing purposes
    public void setDatabase(Database given){
        database = given;
    }
    public Goal getGoal(){
        //For testing goal is being set correctly to LOSS and GAIN in JUnit
        return goal;
    }
    //End of Unit testing related

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            database = new Database();

            HttpSession session = request.getSession();
            Models.User current = (Models.User) session.getAttribute("loggedInUser");
            
            if(current == null){
                throw new Exception("No logged in user...");
            }
            
            String username = current.getUsername();

            //Get details provided by jsp
            int targetWeight = Integer.valueOf(request.getParameter("targetWeight"));
            String groupName = request.getParameter("groupName");
            String targetDate = request.getParameter("targetDate");
            String description = request.getParameter("description");

            //Create a goal object from the details provided
            goal = new Models.Goal(username, targetWeight, description, targetDate, groupName);
          
            //If the user weighs more than the target weight
            if(current.getWeight() > targetWeight){
                //set the goal to a LOSS type goal
                goal.setType(Goal.Type.LOSS);
            } else {
                //Otherwise they are trying to gain weight
                goal.setType(Goal.Type.GAIN);
            }

            //Register the goal to the database
            if(!database.createGoal(goal)){
                throw new Exception("Failed to create goal...");
            }
            
            response.sendRedirect("Goal_Management");

        } catch (Exception ex) {
            request.setAttribute("errors", ex);
            request.getRequestDispatcher("errors.jsp").forward(request, response);
        } finally {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
