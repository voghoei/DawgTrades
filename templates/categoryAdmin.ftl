<#import "default.ftl" as default>
<#import "categoryAdminMacro.ftl" as catAdmin>
<@default.mainLayout "DawgTrades: Admin Panel - Categories">
<h1>Category Admin</h1>
<#if error??>
<div class="row">
	<div class="col-md-12 alert alert-danger" role="alert">
		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
		<span class="sr-only">Error:</span>
		${error}
	</div>
</div>
</#if>

<#if message??>
<div class="row">
	<div class="col-md-12 alert alert-success" role="alert">
		<span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
		<span class="sr-only">Message:</span>
		${message}
	</div>
</div>
</#if>
<#include "adminNav.ftl">
	<#if categoriesMap??>
		<ul class="list-group">
			<@catAdmin.categoryRow categoryMap=categoriesMap categoryId="0">
		</ul>
	</#if>
	</div>
</div>
</@default.mainLayout>
