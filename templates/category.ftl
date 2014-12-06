<#import "default.ftl" as default>
<#if specificCategory??>
	<#set title = "DawgTrades - Browse: ${specificCategory.getName()}">
<#else>
	<#set title = "DawgTrades - Browse">
</#if>
<@default.mainLayout title>
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
		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
		<span class="sr-only">Message:</span>
		${message}
	</div>
</div>
</#if>
<div class="row">
	<div class="col-md-4">
		<#if subCategories??>
			<div class="list-group">
				<#list subCategories as subCategory>
					<a href="category?id=${subCategory.getId()}" class="list-group-item">
						<#if subCategoryCounts[subCategory] > -1><span class="badge">${subCategoryCounts[subCategory]}</span></#if>
						${subCategory.getName()}
					</a>
				</#list>
			</div>
		<#else>
			<em>No <#if specificCategory??>sub</#if>categories</em>
		</#if>
	</div>
</div>
</@default.mainLayout>