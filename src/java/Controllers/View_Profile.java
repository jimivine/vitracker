/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Goal;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
@WebServlet(name = "View_Profile", urlPatterns = {"/View_Profile"})
public class View_Profile extends HttpServlet {

    Database database = new Database();

    //For JUnit testing
    protected void setDatabase(Database given) {
        database = given;
    }
    //End JUnit related

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
        try {

            //Get the currently logged in user
            HttpSession session = request.getSession();
            Models.User current = (Models.User) session.getAttribute("loggedInUser");

            //If the user could not be found
            if (current == null) {
                response.sendRedirect("index.jsp");
            } else {
                String username = current.getUsername();

                System.out.println("error1");
                //Check for any expiring/successful group goals 
                //  (must come before updateUserGoals
                database.checkGroupGoals(current);
                //Update all the user's goals
                database.updateUserGoals(current);

                //Get any of the user's upcoming goals
                ArrayList<Goal> upcomingGoals;
                upcomingGoals = database.getUpcomingGoals(username);
                request.setAttribute("upcomingGoals", upcomingGoals);

                //Check if the user requires a new weight record
                boolean checkRequired = database.weightCheckRequired(username);

                request.setAttribute("checkRequired", checkRequired);

                //Get calories left for user to consume to meet their recommended count
                String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                double caloriesConsumedToday = database.getFullCaloryCount(username, date);
                double caloriesLeft = current.recommendedCalorieIntake() - caloriesConsumedToday;

                request.setAttribute("caloriesLeftToday", caloriesLeft);

                //Redirect user to their profile page
                request.getRequestDispatcher("profile.jsp").forward(request, response);
            }

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
