<#import "default.ftl" as default>
<#if specificCategory??>
	<#assign title = "DawgTrades - Browse: ${specificCategory.getName()}">
<#else>
	<#assign title = "DawgTrades - Browse">
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
		<div class="panel panel-default">
		  <div class="panel-heading">
			<#if specificCategory??>
				${specificCategory.getName()}
			<#else>
				Categories
			</#if>
			</div>
			<#if subCategories??>
				<div class="list-group">
					<#list subCategories as subCategory>
						<a href="category?id=${subCategory.getId()}" class="list-group-item">
							<#assign idx = subCategories?seq_index_of(subCategory)>
							<#if subCategoryCounts[idx] gte 0><span class="badge">${subCategoryCounts[idx]}</span></#if>
							${subCategory.getName()}
						</a>
					</#list>
				</div>
			<#else>
				<ul class="list-group">
    				<li class="list-group-item">No <#if specificCategory??>sub</#if>categories.</li>
				</ul>
			</#if>
		</div>
	</div>
</div>
</@default.mainLayout>