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
	<#if loggedInUser??>
	<label> Username: </label><p> ${loggedInUser.getName()}</p>
	<form role="form" id="settingsForm" action="settings" method="post">
		<div class="form-group">
			<label> First Name:</label>
			<input type="text" class="form-control" name="fname" placeholder="${loggedInUser.getFirstName()}">
		</div>		
		   <div class="form-group">
                        <label> Last Name:</label>
                        <input type="text" class="form-control" name="lname" placeholder="${loggedInUser.getFirstName()}">
                </div>
		


	</form>
	</#if>
	</div>
</div>
</@default.mainLayout>
