/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
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
@WebServlet(name = "Create_Group_Goal", urlPatterns = {"/Create_Group_Goal"})
public class Create_Group_Goal extends HttpServlet {

    //Connection settings for the Health Tracker's email address
    //We are using googlemail for now
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "vitplushealth";
    private static final String SMTP_AUTH_PWD = "vitpassword";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            response.setContentType("text/html;charset=UTF-8");

            //Configuring email settings for Googlemail
            Properties props = new Properties();

            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.host", SMTP_HOST_NAME);
            props.put("mail.smtps.auth", "true");

            Session mailSession = Session.getDefaultInstance(props);
            mailSession.setDebug(true);
            Transport transport = mailSession.getTransport();

            //Creating message to send
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("Vit+ Group Goal");
            
            String groupName = request.getParameter("groupName");
            String targetDate = request.getParameter("targetDate");
            String targetWeight = request.getParameter("targetWeight");
            String desc = request.getParameter("description");
            
            String url = "http://localhost:8084/Health%20Tracker/acceptGroupGoal.jsp?"
                    + "gGroup=" + groupName + ""
                    + "&gDate=" + targetDate + ""
                    + "&gDesc=" + desc + ""
                    + "&gWeight=" + targetWeight + "";
            
            message.setContent(
                      "<html><body>"
                    + "A group goal has been created for <b>" + groupName + "</b>. <br><br>"
                    + "Target Weight: " + targetWeight + " <br>"
                    + "Target Date: " + targetDate +  "<br><br>"
                    + "Description: " + desc + "<br><br>"
                    + "Click the following link to accept this goal..."
                    + "<br><br>"
                    + "<a href='" + url + "'>Accept Goal</a>"
                    + "</html></body>"
                    
                    , "text/html");

            //Get all users belonging to this group
            Database db = new Database();
            ArrayList<User> groupMembers = db.getGroupMembers(request.getParameter("groupName"));

            //If group members were found
            if (groupMembers != null) {
                //For all users founds
                for (User aUser : groupMembers) {
                    //Add this user as a recipient using their email address
                    message.addRecipient(Message.RecipientType.TO,
                            new InternetAddress(aUser.getEmail()));
                }

                //Sending message
                transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

                transport.sendMessage(message,
                        message.getRecipients(Message.RecipientType.TO));
            }
            
            transport.close();
            //Redirect user to their profile page
            response.sendRedirect("View_Profile");

        } catch (MessagingException ex) {
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
