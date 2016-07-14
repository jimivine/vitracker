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

/**
 *
 * @author Stuart
 */
@WebServlet(name = "Register_User", urlPatterns = {"/Register_User"})
public class Register_User extends HttpServlet {
    
    private Database database = new Database(); 
    
    //For JUnit
    protected void setDatabase(Database given){
        database = given;
    }
    //End JUnit
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String givenUsername = request.getParameter("rUsername");
            String givenPass = request.getParameter("rPassword");
            String givenFName = request.getParameter("rFirstName");
            String givenLName = request.getParameter("rLastName");
            String gEmail = request.getParameter("rEmail");
            double gHeight = Double.valueOf(request.getParameter("rHeight"));
            double gWeight = Double.valueOf(request.getParameter("rWeight"));
            String ggender = request.getParameter("rGender");
            String gactiveLevel = request.getParameter("rActiveLevel");
            int gage = Integer.valueOf(request.getParameter("rAge")); 
            
            //If a user with this username already exists in the database
            if(database.getUser(givenUsername) != null){
                throw new Exception("User with this username already exists...");
            } else {
                //Active level not provided in registration
                if(gactiveLevel == null || gactiveLevel.equals("")){
                    //set automatically to 
                    gactiveLevel = "Sedentary";
                }
                //Otherwise the username is free and the user can be made
                Models.User newUser = new Models.User(givenUsername, givenPass, 
                        givenFName, givenLName, gEmail, gWeight, gHeight,gage,ggender,gactiveLevel);
                
                //Register the user to the database
                database.registerUser(newUser);
                
                //redirect to index page
                response.sendRedirect("index.jsp");
            }
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
