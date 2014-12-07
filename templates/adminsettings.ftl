<#import "default.ftl" as default>
<@default.mainLayout "Settings">
<h1>Admin Panel</h1>
<#if error??>
<div class="row">
    <div class="col-md-12 alert alert-danger" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            <span class="sr-only"> Error: </span>
            ${error}
    </div>
</div>
</#if>
<#include "adminNav.ftl">
	<#if loggedInUser??>
	<label> Welcome, </label><p> ${loggedInUser.getName()}</p>
	</#if>
	</div>
</div>
</@default.mainLayout>
