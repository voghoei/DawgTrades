<#macro mainLayout title="DawgTrades">
	<!doctype html>
	<html class="no-js" lang="">
		<head>
			<meta charset="utf-8">
			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<title>${title}</title>
			<meta name="description" content="">
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<!-- Bootstrap -->
			<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
			<!-- Font-Awesome -->
			<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
			
			<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
			<!--[if lt IE 9]>
				<script src="//cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.2/html5shiv.min.js"></script>
				<script src="//cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
			<![endif]-->
			<script src="//cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js"></script>
		</head>
		<body>
			<div class="container">
				<#nested/>
			</div>
			
			<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
			<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
			<script>window.jQuery || document.write('<script src="//ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.1.min.js"><\/script>')</script>
			<!-- Include all compiled plugins (below), or include individual files as needed -->
			<script src="//netdna.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
			
		        <!-- Google Analytics: change UA-XXXXX-X to be your site's ID. -->
		        <script>
		            (function(b,o,i,l,e,r){b.GoogleAnalyticsObject=l;b[l]||(b[l]=function(){(b[l].q=b[l].q||[]).push(arguments)});b[l].l=+new Date;e=o.createElement(i);r=o.getElementsByTagName(i)[0];e.src='//www.google-analytics.com/analytics.js';r.parentNode.insertBefore(e,r)}(window,document,'script','ga'));
		            ga('create','UA-XXXXX-X');ga('send','pageview');
		        </script>
		</body>
	</html>
</#macro>