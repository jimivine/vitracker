<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <title>ViTracker+ - Activity Page</title>
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

        <!--
        ****************************************************
        *                   Main Body                      *
        ****************************************************
        -->

        <div id="maincontainer">
             <div class="labels1"> 
                        <article><h4 id="record-ul">RECORD ACTIVITY</h4></article>
                        <article class="exercise">    
                            <h2>Register Exercise</h2>
                            <form action="Register_Exercise" method="POST">
                                <h4>Exercise: </h4>
                                <select name="exerciseID">
                                    <%
                                        Models.User user2 = (Models.User) session.getAttribute("loggedInUser");
                                        if (user2 == null) {
                                            response.sendRedirect("index.jsp");
                                        }

                                        ArrayList<Models.Exercise_Type> availExercises = (ArrayList<Models.Exercise_Type>) request.getAttribute("availExercises");
                                        if (availExercises != null) {

                                            for (Models.Exercise_Type e : availExercises) {%>
                                    <option value="<%=e.getExerciseID()%>"><%=e.getName()%></option>
                                    <%      }//endfor
                                        }//endif%>
                                </select>
                                <br>
                                <h4>Duration: </h4><input type="number" placeholder="0 minutes" required name="duration"></input>
                                <br>
                                <h4>Distance: </h4><input type="number" placeholder="0 km/miles" required name="distance"></input>
                                <h4></h4>
                                <input type="submit" class="button-link2" value="Register Exercise"></input>
                                <h4> </h4>
                            </form>
                         </article>

                        <article class="meal">            
                            <h2>Register Meal</h2>
                            <form action="Register_Meal" method="POST">
                                <h4>Date: </h4><input type="date" required name ="rMealDate"> <h4>Type: </h4><select required name="rMealType">
                                    <option value="breakfast">Breakfast</option>
                                    <option value="lunch">Lunch</option>    
                                    <option value="dinner">Dinner</option>
                                    <option value="snacks">Snack</option>
                                </select>
                                <h4></h4>
                                <h4>Meal Item:</h4>
                                <select name="sustenanceID">
                                    <%
                                        ArrayList<Models.Sustenance> availSustenances = (ArrayList<Models.Sustenance>) request.getAttribute("availSustenances");
                                        if (availSustenances != null) {

                                            for (Models.Sustenance s : availSustenances) {%>
                                    <option value="<%=s.getSustenanceID()%>"><%=s.getName()%></option>
                                    <%      }//endfor
                                        }//endif%>
                                </select>
                                <h4>Calories Consumed: </h4><input type="number" min="0" required name="caloriesConsumed" placeholder="Calories">
                                <h4></h4>
                                <input type="submit" class="button-link2" value="Add Item to Meal"></input>
                                <h4></h4> 
                            </form>
                        </article>

                        <article class="sustenance">
                            <h2>Add Custom Food/Drink</h2>
                            <form action="AddCustomSustenance" method="POST">
                                <h4>Name: </h4><input type="text" required placeholder="Name" name="customName"></input>
                                <h4></h4>
                                <input type="submit" class="button-link2" value="Add Custom Food/Drink"></input>
                            </form>
                            <h4></h4>
                        </article>
                        <img id="bkactimage" src="Images/FindF_big.png" alt="Activity"/>
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


