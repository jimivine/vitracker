 "use strict";
            var myViewModel = {
                schools: ko.observableArray([]),
                userName: ko.observable(),
                password: ko.observable()
            };
            ko.applyBindings(myViewModel);
            
            var getSchoolsData = function(){
                $.getJSON( "services/schools.json", function( data ) {
                  console.log(data); 
                  myViewModel.schools(data.schools);
                
                });
            };
            getSchoolsData();
            
           
            
            var getLogin = function(){
                $.getJSON( "services/login.json?userName=" + myViewModel.userName() 
                        + "&password=" + myViewModel.password(), function( data ) {
                    
        // TO DO make login service
        //            if(data.response === "success"){

                    if(myViewModel.userName() !== "jimivine" && myViewModel.password() !== "123"){
                        alert("Invalid Username or Password");
                    }else{
                        window.location.href="profile.html";
                    }
                });
            };
            
            /*
            var getNewUser = function(){
                $.getJSON( "services/test.json", function( data ) {
                    var newUser = {} // a new user object

                    object.user.push({
                    // add a new user to the set
                    data.push(newUser);      
                  });
              };
     */
            