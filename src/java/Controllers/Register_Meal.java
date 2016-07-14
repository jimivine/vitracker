/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

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
@WebServlet(name = "Register_Meal", urlPatterns = {"/Register_Meal"})
public class Register_Meal extends HttpServlet {

    
    private Database database = new Database(); 
    
    //For JUnit
    protected void setDatabase(Database given){
        database = given;
    }
    //End JUnit
    
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
            
            if(current == null){
                throw new Exception("No logged in user...");
            }
            
            String username = current.getUsername();
            
            
            //Get id of sustenance to add to meal
            int sustenanceID = Integer.valueOf(request.getParameter("sustenanceID"));
            //Get type of meal
            String type = request.getParameter("rMealType");
            //Get date of meal
            String date = request.getParameter("rMealDate");
            
            double caloriesConsumed = Double.valueOf(request.getParameter("caloriesConsumed"));
            
            //Register this sustenance to the meal
            database.addSustenanceToMeal(username, date, type, sustenanceID, caloriesConsumed);
            
            //Send user back to their activity management page
            response.sendRedirect("Activity_Management");
            
        } catch (Exception ex) {
            System.out.println(ex);
            request.setAttribute("errors", ex);
            request.getRequestDispatcher("errors.jsp").forward(request, response);
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
