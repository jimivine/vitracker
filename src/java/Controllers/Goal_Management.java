/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Exercise_Type;
import Models.Goal;
import Models.Past_Goal;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
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
 * @author adam
 */
@WebServlet(name = "Goal_Management", urlPatterns = {"/Goal_Management"})
public class Goal_Management extends HttpServlet {
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
            //Get the current session's user
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("loggedInUser");
            
            if(currentUser == null){
                throw new Exception("No logged in user...");
            }

            Database db = new Database();

            //Get any requested goal history
            String temp = request.getParameter("requestedGoalID");
            if (temp != null) {
                int requestGoalID = Integer.valueOf(temp);
                ArrayList<Past_Goal> goalHistory = db.getGoalHistory(requestGoalID);
                request.setAttribute("goalHistory", goalHistory);
            }

            //Get the user's active goals
            ArrayList<Goal> activeGoals = db.getStatusGoals(currentUser.getUsername(), "ACTIVE");
            request.setAttribute("activeGoals", activeGoals);

            //Get the user's expired goals
            ArrayList<Goal> expiredGoals = db.getStatusGoals(currentUser.getUsername(), "EXPIRED");
            request.setAttribute("expiredGoals", expiredGoals);

            //Get the user's successful goals
            ArrayList<Goal> successfulGoals = db.getStatusGoals(currentUser.getUsername(), "SUCCESSFUL");
            request.setAttribute("successfulGoals", successfulGoals);

            //Send user to the goal management page
            request.getRequestDispatcher("goal.jsp").forward(request, response);
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
