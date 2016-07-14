<%@page import="Models.Membership"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <title>ViTracker+ - Group Management Page</title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
        <meta name="keywords" content="ViTracker+" /> 
        <meta name="description" content="Welcome to ViTracker+" /> 
        <meta name="Author" content="Jimi Vine" />
        <script src="js/formatdate.js" type="text/javascript"></script>
        <link href="style2.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery.js"></script>
        <script src="js/knockout-3.1.0.js"></script>
        <script src="js/profile.js"></script>
    </head>
       
    <body> 

        <!--
        ****************************************************
        *                      Header                      *
        ****************************************************
        -->		 
        <div id="header">
            <article id="disclamer">
                Disclaimer:
            This application is not a commercial application and does not provide
            insurance. This is a study project that is part of a Computing Science module taught at the
            University of East Anglia, Norwich, UK. If you have any questions, please contact the
            module coordinator, Joost Noppen, at j.noppen@uea.ac.uk
            </article>
            <a href="View_Profile">
                <img id="logo" src="Images/Logo.png" alt="ViTracker+ Logo"/>
            </a>
            <div id="todays_date" data-bind="text: date"></div>
            <div id="time_now" data-bind="html: currentTime"></div>
        </div>
          <%
                    Models.User user3 = (Models.User) session.getAttribute("loggedInUser");
                     if (user3 == null) {
                       response.sendRedirect("index.jsp");
                   }
                    ArrayList<Models.Group> groups2 = (ArrayList<Models.Group>) request.getAttribute("createdGroups");

                  %>
        <div id="maincontainer">
            <div class="labels1">
                <article><h4 class="group-ul">GROUP MANAGEMENT</h4></article>
                
                <article class="creategroup">
                    <h2>Create Group</h2>
                    <form action="Register_Group" method="POST">
                        <h4>Enter Group Name:</h4><input type="text" name="rGroupName" required placeholder="Group Name">
                        <h4></h4>
                        <input type="submit" class="button-link2" value="Create Group">
                    </form>
                </article>
                
                <article class="groupgoal">
                    <h2>Create A Group Goal</h2>
                    <form action ="Create_Group_Goal" method="POST">
                        <h4>Group Name:</h4><select name="groupName"/>
                  <%      if (groups2 != null) {
                       for (int j = 0; j < groups2.size(); j++) {
                  %>

                   <option value="<%=groups2.get(j).getGroupName()%>"> <%=groups2.get(j).getGroupName()%> </option>

                 <%
                          }
                      }
                  %>
            </select>
                        <h4>Target Date:</h4><input type="date" name="targetDate"  required placeholder="Target Date"/>
                        <h4>Description:</h4><input type="text" name="description"   placeholder="Description"/>
                        <h4>Target Weight:</h4><input type="number" name="targetWeight" required placeholder="Target Weight"/>
                        <h4></h4>
                        <input type="submit" class="button-link2" value="Create Goal">
                    </form>
                </article>
                
                <article class="invitegroup">
                    <h2>Invite to a Group</h2>
                    <form action="New_Group_Invitation" method="POST">
                        <h4>Username:</h4><input type="text" name="jUsername" required placeholder="Username of user"><br>
                        <h4>Group Name:</h4>
                             <select name="jGroupName">
                  <%      if (groups2 != null) {
                    for (int j = 0; j < groups2.size(); j++) {
                    %>

                   <option value="<%=groups2.get(j).getGroupName()%>"> <%=groups2.get(j).getGroupName()%> </option>

                       <%
                            }
                       }
                     %>
                    </select>
                            <h4></h4>
                        <input type="submit" class="button-link2" value="Send Invite">
                    </form>
                </article>
                
                <article class="managegroup">
                    <h1>Manage Group Memberships</h1>
                    <h4>Created Groups</h4>

                    <%
                        Models.User user2 = (Models.User) session.getAttribute("loggedInUser");
                        if (user2 == null) {
                            response.sendRedirect("index.jsp");
                        }

                        ArrayList<Models.Group> groups = (ArrayList<Models.Group>) request.getAttribute("createdGroups");

                        if (groups != null) {
                    %>

                    <table border="1">
                        <thead>
                            <tr>
                                <th>Group Name</th>
                                <th></th>
                            </tr>
                        </thead>
                        <%
                            for (int i = 0; i < groups.size(); i++) {
                        %>
                        <tbody>
                            <tr>
                                <td><%=groups.get(i).getGroupName()%></td>
                            <form action="Delete_Group" method="POST">
                                <input type ="hidden" value="<%=groups.get(i).getGroupName()%>" name="dGroupName">
                                <td><input type="submit" value="Delete Group"></td>     
                            </form>
                            </tr>
                        </tbody>
                        <%
                            }
                        %>
                    </table>
                    <%
                        }
                    %>

                    <h4>Joined Groups</h4>

                    <%
                        ArrayList<Models.Membership> memberships = (ArrayList<Models.Membership>) request.getAttribute("activeMemberships");

                        if (memberships != null) {
                            %>

                    <table border="1">
                        <thead>
                            <tr>
                                <th>Group Name</th>
                                <th></th>
                            </tr>
                        </thead>
                        <%
                            for (int i = 0; i < memberships.size(); i++) {
                    %>
                            
                        <tbody>
                            <tr>
                                <td><%=memberships.get(i).getGroupName()%></td>
                            <form action="Leave_Group" method="POST">
                                <input type ="hidden" value="<%=memberships.get(i).getMembershipID()%>" name="dMembershipID">
                                <td><input type="submit" value="Leave Group"></td>     
                            </form>
                            </tr>
                        </tbody>
                        <%
                            }
                        %>
                  </table>
                    <%
                        }
                    %>
                </article>
            </div>
        </div>
   
        <!--
        ****************************************************
        *                     Footer                       *
        ****************************************************
        -->
        <div id="footer">        
            <section class="tabs">            
                <div class="buffer2"></div>
                <span class="anchor" id="view"></span>
                <div class="panel">
                    <a class="tab-link" onclick="myApp.viewProfile()" href="View_Profile">
                        <img src="Images/Profile.png" alt="View Profile"/>
                        <h1>VIEW</h1>
                        <h1>PROFILE</h1>
                        <p>Check out your profile page and view your health related information</p>
                    </a>
                </div>
                

                <div class="buffer3"></div>                 
                <span class="anchor" id="history"></span>
                <div class="panel">
                    <a class="tab-link" href="Get_User_History">
                        <img src="Images/Join.png" alt="View History"/>
                        <h1>VIEW</h1>
                        <h1>HISTORY</h1>
                        <p>View history of your activities</p>
                    </a>  
                </div>
                
                <div class="buffer3"></div>
                <span class="anchor" id="activity"></span>
                <div class="panel">
                    <a class="tab-link" href="Activity_Management">
                        <img src="Images/FindF.png" alt="Record Activity"/>
                        <h1>RECORD</h1>
                        <h1>ACTIVITY</h1>
                        <p>Register your daily calorie intake or exercise</p>
                    </a>
                </div> 

                <div class="buffer3"></div>
                <span class="anchor" id="goals"></span>
                <div class="panel">
                    <a class="tab-link" href="Goal_Management">
                        <img id="imgweight" src="Images/weight.png" alt="Goals"/>
                        <h1>GOAL</h1>
                        <h1>MANAGEMENT</h1>
                        <p>Set yourself a goal for losing or gaining weight</p>
                    </a>
                </div>

                   <div class="buffer3"></div>
                   <span class="anchor" id="findu"></span>
                   <div class="panel">
                       <a class="tab-link" href="Group_Management">
                           <img src="Images/FindU.png" alt="Group Management"/>
                           <h1>GROUP</h1>
                           <h1>MANAGEMENT</h1>
                           <p>Find details about a group. If you can't find it let us know</p>
                       </a>
                   </div>  

                <div class="buffer3"></div>
                <span class="anchor" id="logout"></span>
                <div class="panel">
                    <a class="tab-link" href="Log_out">
                        <img src="Images/Logout.png" alt="Logout"/>
                        <h2>LOGOUT</h2>
                    </a>
                </div>                    
            </section>            
            <div class="buffer2"></div>
        </div>

        <script>
            $(function() {
                myApp.vm = new myApp.ProfileViewModel();
                ko.applyBindings(myApp.vm);
                myApp.vm.username(window.location.search.split("=")[1]);
//* this gets username from the URL and binds it to the viewmodel in profile.jsp
            });
        </script>
    </body>
</html>