<%--
    Document   : index
    Created on : March 09, 2015, 5:01:21 PM
    Author     : Jimi Vine
--%>
<%--
            Disclaimer:
            This application is not a commercial application and does not provide
            insurance. This is a study project that is part of a Computing Science module taught at the
            University of East Anglia, Norwich, UK. If you have any questions, please contact the
            module coordinator, Joost Noppen, at j.noppen@uea.ac.uk
--%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <title>ViTracker+ - Home Page</title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<meta name="keywords" content="Health Tracker" /> 
	<meta name="description" content="Welcome to ViTracker+." /> 
	<meta name="Author" content="Jimi Vine" />
        <link href="style1.css" rel="stylesheet" type="text/css"/>
        <script src="js/formatdate.js" type="text/javascript"></script>
        <script src="js/jquery.js"></script>
        <script src="js/knockout-3.1.0.js"></script>
        <script src="js/login.js"></script>
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
            <a href="#">
                <img id="logo" src="Images/Logo.png" alt="logo"/>
            </a>
            <div id="todays_date" data-bind="text: date"></div>
            <div id="time_now" data-bind="html: currentTime"></div>
        </div>

        <div class="vbuffer1"></div>
        <!--
        ****************************************************
        *               Background Transition              *
        ****************************************************
        -->
        <div id="bg_transition">
            <img src="Images/gym.jpg" alt="gym"/>
            <img src="Images/fitness.jpg" alt="fitness"/>
            <img src="Images/fit4.png" alt="rock climbing"/>
            <img src="Images/fitness2.jpg" alt="yoga"/>
            <img src="Images/veg1.jpg" alt="vegetables"/>
            <img src="Images/fit2.jpg" alt="yoga"/> <!--First, backwards-->
        </div>


        <!--
        ****************************************************
        *                     Login Bar                    *
        ****************************************************
        -->
        <div id="login">
            <div class="medium_text">
                <form action="Log_in" method="POST">
                    Username:
                    <input name="userName" id="username" type="text" placeholder=" Username" size="20" required tabindex="1" value="" />					
                    Password:
                    <input name="password" id="password" type="password" placeholder=" Password" size="20" required tabindex="2" value="" />
                    <input class="button-link" name="submit" onclick="myApp.getLogin()" type="submit" tabindex="3" value="Login" />
                    <a href="mailto:support@vitracker.com?Subject=Forgotten%20Password" target="_top" tabindex="4" 
                       style="text-decoration:underline;color:white;font-weight:normal">
                        Forgotten password?
                    </a>
                </form>
            </div>
        </div>

        <!--
        ****************************************************
        *                     Footer                       *
        ****************************************************
        -->
        <div id="footer">
            <section class="tabs">            
                <div class="buffer1"></div>
                <span class="anchor" id="about"></span>
                <div class="panel">
                    <a class="tab-link" href="#about">
                        <img src="Images/About.png" alt="About"/>
                        <h1>ABOUT ViTRACKER+</h1>
                        <p>Find out about our free service and how to keep track of your fitness and health.</p>
                    </a>
                    <div class="labels1"> 
                        <article>
                            <h4 id='about-ul'>ABOUT ViTRACKER+</h4>
                            <p>ViTracker+ helps you keep track of your weight and fitness. 
                                It's the best way to monitor progress and join groups
                                with people who are also fitness focused.</p>
                            <p>ViTracker+ is a free service. You sign up, login, build a profile, 
                                find a group and then you are ready to become an 
                                active member of ViTracker+, the latest application 
                                in social health tracking.</p>
                        </article> 
                        <img src="Images/About_big.png" alt="About"/>
                    </div>
                </div>                    
                <div class="buffer2"></div>
                <span class="anchor" id="join"></span>
                <div class="panel">
                    <a class="tab-link" href="#join">
                        <img src="Images/Join.png" alt="Join"/>
                        <h1>JOIN ViTRACKER+</h1>
                        <p>Join up to the fastest growing health tracker network 
                            for students on the internet and it's free!</p>
                    </a>                    
                    <div class="labels1"> 
                        <article>
                            <h4 id='join-ul'>JOIN ViTRACKER+</h4>
                            <p>Please complete the form with a username and password between 
                                8 and 20 characters long.</p>

                            <p>Once you've successfully created an account you can set up a 
                                profile, find groups and start tracking!</p>
                        </article>
                        <article class="signup">
                            <img src="Images/Join_big.png" alt="Join"/>
                            <form name="joinus" action="Register_User" method="POST"> 
                                <h2>Create an account</h2>
                                <h4>Username&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Forename</h4>
                                <input type="text" name="rUsername" id="newUsername" placeholder="Username" required data-bind="value: newUsername"/>
                                <input type="text" name="rFirstName" id="newFirstname" placeholder="Forename" required data-bind="value: newFullname"/>
                                <h4>Email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Surname</h4>
                                <input type="text" name="rEmail" placeholder="Email Address" required data-bind="value: newEmail"/>
                                <input type="text" name="rLastName" id="newLastname" placeholder="Surname" required data-bind="value: newLastname" />
                                <h4>Height&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Weight</h4>
                                <input type="text" name="rHeight" id="newHeight" required placeholder="Height (metres)" />
                                <input type="text" name="rWeight" id="newWeight" required placeholder="Weight (kg)" />
                                <h4>Age&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gender</h4>
                                <input type="number" name="rAge" min="0" id="newAge" required placeholder="Age" />
                                <select name="rGender">
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                            </select>
                                <h4>Password</h4>
                                <input type="password" name="rPassword" id="newPassword" type="password" required placeholder="Password" data-bind="value: newPassword" /> 
                                <h4>Please confirm your Password</h4>
                                <input name="newConfirm" id="check" type="password" placeholder="Re-enter password" required data-bind="value: newConfirm" />
                                <h4></h4>   
                                <input name="newCheck" type="checkbox" required data-bind="checked: newCheck"/>               
                                I agree with the <a href="#" onclick="return displayTandC();" style="text-decoration:underline;color:#164f7d;font-weight:bold">terms and conditions</a>
                                <h4></h4> 
                                <input name="newSubmit" class="button-link2" type="submit" value="Sign Up" onclick="myApp.checkDetails()" />
                                <h4> </h4> 
                            </form>
                        </article>                        
                    </div>
                </div>
                <script>
                 
                 
                       
                    
                </script>
                <div class="buffer2"></div>                 
                <span class="anchor" id="roll"></span>
                <div class="panel">
                    <a class="tab-link" href="#roll">
                        <img class="icon" src="Images/Roll.png" alt="Roll"/>
                        <h1>FEATURES</h1>
                        <p>See what we have to offer.</p>
                    </a>                    
                    <div class="labels1"><article><h4 id='roll-ul'>FEATURES</h4></article>                        
                        <article id="uniroll">
                            <ul>
                                <li>BMI Calculator</li>
                                <li>Record Exercise</li>
                                <li>Record Food Diary</li>
                                <li>Add Custom Food/Drink to Diary</li>
                                <li>Set yourself Weight Goals</li>
                                <li>View History of Activities</li>
                                <li>View History of Goals set and Achieved</li>
                                <li>View Graphs and Charts on Weight Loss/Gain</li>
                            </ul>
                        </article>
                        <img src="Images/Roll_big.png" alt="Roll"/>
                    </div>
                </div>            
                <div class="buffer1"></div>

            </section>    
        </div>
        <script>
            $(function() {
                myApp.vm = new myApp.LoginViewModel();
                ko.applyBindings(myApp.vm);
            });
        </script>
    </body>
</html>
