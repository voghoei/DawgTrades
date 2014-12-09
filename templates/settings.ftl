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
	<#if profile??>
	<label> Username: </label><p> ${loggedInUser.getName()}</p>
	<form role="form" id="settingsForm" action="settings" method="post">
		<div class="form-group">
			<label> First Name:</label>
			<input type="text" class="form-control" name="fname" placeholder="${loggedInUser.getFirstName()}">
		</div>		
		   <div class="form-group">
                        <label> Last Name:</label>
                        <input type="text" class="form-control" name="lname" placeholder="${loggedInUser.getLastName()}">
                </div>
		


	</form>
	<#elseif password??>
	<form role="form" id="settingsForm" action="settings" method="post">
                <div classs="form-group">
                        <label for="password">Enter a new password</label>
                        <input type="password" class="form-control" placeholder="Password" name="password">
                </div>
                <div classs="form-group">
                        <label for="passwordReEnter">Re-enter password</label>
                        <input type="password" class="form-control" placeholder="Re-enter Password" name="passwordRe">
                </div>
	</form>
	<#elseif other??>
		<form> some text</form>
	</#if>
	</#if>
	</div>
</div>
</@default.mainLayout>
