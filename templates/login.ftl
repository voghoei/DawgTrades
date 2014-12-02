<#import "default.ftl" as default>
<@default.mainLayout "DawgTrades - Login">
<h1>Login</h1>
<#if error??>
<div class="row">
	<div class="col-md-12 alert alert-danger" role="alert">
		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
		<span class="sr-only">Error:</span>
		${error}
	</div>
</div>
</#if>
<div class="row">
	<form role="form" action="login">
		<div class="form-group">
			<label for="username">Username</label>
			<input type="text" class="form-control" placeholder="username" name="username" id="username">
		</div>
		<div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" placeholder="Password" id="password" name="password">
        </div>
		<button type="submit" class="btn btn-default">Submit</button>
	</form>
</div>
</@default.mainLayout>