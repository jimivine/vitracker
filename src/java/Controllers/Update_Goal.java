/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Models.Goal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stuart
 */
@WebServlet(name = "Update_Goal", urlPatterns = {"/Update_Goal"})
public class Update_Goal extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Grab goal id to modify
            int goal_id = Integer.valueOf(request.getParameter("eGoalID"));
            //Grab attributes to modify
            String description = request.getParameter("eDescription");
            String target_date = request.getParameter("eTargetDate");
            double target_weight = Double.valueOf(request.getParameter("eTargetWeight"));
            
            //Create a goal object from modified details
            Goal aGoal = new Goal();
            aGoal.setDescription(description);
            aGoal.setTargetDate(target_date);
            aGoal.setTotalWeight(target_weight);
            aGoal.setGoal_ID(goal_id);
            
            HttpSession session = request.getSession();
            Models.User current = (Models.User) session.getAttribute("loggedInUser");
            
            if(current == null){
                throw new Exception("No logged in user...");
            }
            
            //If the user weighs more than the target weight
            if(current.getWeight() > target_weight){
                //set the goal to a LOSS type goal
                aGoal.setType(Goal.Type.LOSS);
            } else {
                //Otherwise they are trying to gain weight
                aGoal.setType(Goal.Type.GAIN);
            }
            
            //Apply modifications to goal
            Database db = new Database();
            db.updateGoal(aGoal);
            
            //Send user back to their goal management page
            response.sendRedirect("Goal_Management");
        } catch (Exception ex) {
            request.setAttribute("errors", ex);
            request.getRequestDispatcher("errors.jsp").forward(request, response);
        } finally {
            out.close();
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
