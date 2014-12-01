<%@page import="edu.uga.dawgtrades.model.RegisteredUser"%>
<%@ page session="true" %>
<!doctype html>
<html lang=''>
    <head>
        <meta charset='utf-8'>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="menu.css">
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <script src="script.js"></script>
        <title>CSS MenuMaker</title>
    </head>
    <body>
        <%
            String name="";
            RegisteredUser runningUser = null;
            if (session.getAttribute("currentSessionUser") != null){
             runningUser = (RegisteredUser) session.getAttribute("currentSessionUser");
             name = runningUser.getFirstName();
            }
            else
                name = "User";
                
        %>

        <div id='cssmenu'>
            <ul>    
                <li class='active'><a href='#'><span>Home</span></a>
                <li class='has-sub'><a href='#'><span> Hi <%= name%> !</span></a>
                    <ul>
                        <li><a href='#'><span>Profile</span></a></li>
                        <li><a href='#'><span>My Items</span></a></li>
                        <li class='last'><a href='#'><span>Logout</span></a></li>
                    </ul>
                </li>

                <li class='has-sub'><a href='#'><span>About</span></a>
                    <ul>
                        <li><a href='#'><span>Company</span></a></li>
                        <li class='last'><a href='#'><span>Contact</span></a></li>
                    </ul>
                </li>
                <li class='last'><a href='#'><span>Contact</span></a></li>
                <li class='last'><a href='login.jsp'><span>Login</span></a></li>
            </ul>
        </div>
        <div>
            <link rel="stylesheet" type="text/css" href="style.css" />
            <div class="container">
	<section id="content">
            <form method="POST" action="LoginServlet">
			<h1>--</h1>
                        
		</form><!-- form -->		
	</section><!-- content -->
</div><!-- container -->

        </div>

    </body>
    <html>
