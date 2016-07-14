/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stuart
 */
@WebServlet(name = "New_Group_Invitation", urlPatterns = {"/New_Group_Invitation"})
public class New_Group_Invitation extends HttpServlet {

    //Connection settings for the Health Tracker's email address
    //We are using googlemail for now
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "vitplushealth";
    private static final String SMTP_AUTH_PWD = "vitpassword";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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
            message.setSubject("Vit+ Group Invitation");

            String content;
            content = "<html>"
                    + "<body>"
                    + "You have been invited to join group: <b>" + request.getParameter("jGroupName") + "</b><br><br>"
                    + "To accept this invitation, please click the link below.<br><br>"
                    + "<a href='http://localhost:8084/Health%20Website/Join_Group?jGroupName=" + request.getParameter("jGroupName") + "'>Accept Invitation</a>"
                    + "</body>"
                    + "</html>";

            message.setText(content, "utf-8", "html");

            //Get details of user to invite
            String username = request.getParameter("jUsername");

            Database db = new Database();
            User aUser = db.getUser(username);

            //Adding recipients to message
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(aUser.getEmail()));

            //Sending message
            transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            transport.close();

            //Redirect user to their group management page
            response.sendRedirect("Group_Management");

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
