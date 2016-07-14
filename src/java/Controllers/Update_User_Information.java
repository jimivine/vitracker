/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Models.User;
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
@WebServlet(name = "Update_User_Information", urlPatterns = {"/Update_User_Information"})
public class Update_User_Information extends HttpServlet {

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
            HttpSession session = request.getSession();
            //Get logged in user from current session
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            
            if(loggedInUser == null){
                throw new Exception("No logged in user...");
            }
            
            //Grab modifed attributes
            String editedFirst = request.getParameter("eFirstName");
            String editedLast = request.getParameter("eLastName");
            double weight = Double.valueOf(request.getParameter("eWeight"));
            double height = Double.valueOf(request.getParameter("eHeight"));
            int editedage = Integer.valueOf(request.getParameter("eAge"));
            String editedactiveLevel = request.getParameter("eActiveLevel");
            String editedgender = request.getParameter("eGender");
            
            //Update Database details
            
            //If the user's weight has changed
            if(weight != loggedInUser.getWeight()){
                //Record the weight in the Past_Weight table
                database.recordPastWeight(loggedInUser.getUsername(), weight);
            }
            
            //Update logged in user
            loggedInUser.setFirstName(editedFirst);
            loggedInUser.setLastName(editedLast);
            loggedInUser.setWeight(weight);
            loggedInUser.setHeight(height);
            loggedInUser.setActiveLevel(editedactiveLevel);
            loggedInUser.setAge(editedage);
            loggedInUser.setGender(editedgender);
            
            //Update database details of loggedInUser
            database.updateUser(loggedInUser);
            
            //Send back to profile page
            response.sendRedirect("View_Profile");
            
        } catch (Exception ex) {
            System.out.println(ex);
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
