<#import "default.ftl" as default>
<@default.mainLayout "Settings">
<h1>Account Settings</h1>
<#if error??>
        <div class="col-md-12 alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only"> Error: </span>
                ${error}
        </div>
</#if>
<#include "sideNav.ftl">
<div class="row">
	<label> Username: </label>
	<form role="form" action="settings" method="post">
		<div class="form-group">
			<label> First Name:</label>
			<input type="text" class="form-control" name="fname" placeholder="${loggedInUser.getFirstName()}">
		</div>		



	</form>
</div>
</@default.mainLayout>
