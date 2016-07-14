/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "Get_User_History", urlPatterns = {"/Get_User_History"})
public class Get_User_History extends HttpServlet {

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
            
            String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
            
            //Get the user's meal activity for the requested date
            date = request.getParameter("requestedDate");
            
            //If no requested date found, then get meal activity for current date
            if (date == null || date.equals("")) {
                date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
            }
            
            //Get calory count of todays meals  
            double caloriesConsumedToday = db.getFullCaloryCount(currentUser.getUsername(), date);
            request.setAttribute("caloriesConsumedToday", caloriesConsumedToday);
            
            //Get individual meal activity
            ResultSet breakfastHistory = db.getSustenanceInMealType(currentUser.getUsername(), date, "breakfast");
            ResultSet lunchHistory = db.getSustenanceInMealType(currentUser.getUsername(), date, "lunch");
            ResultSet dinnerHistory = db.getSustenanceInMealType(currentUser.getUsername(), date, "dinner");
            ResultSet snacksHistory = db.getSustenanceInMealType(currentUser.getUsername(), date, "snacks");
            request.setAttribute("breakfastHistory", breakfastHistory);
            request.setAttribute("lunchHistory", lunchHistory);
            request.setAttribute("dinnerHistory", dinnerHistory);
            request.setAttribute("snacksHistory", snacksHistory);
            
            //Get the user's exercise history
            ResultSet exerciseHistory = db.getUserExerciseHistory(currentUser);
            request.setAttribute("exerciseHistory", exerciseHistory);
            
            //Send user to the user history page
            request.getRequestDispatcher("history.jsp").forward(request, response);
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
