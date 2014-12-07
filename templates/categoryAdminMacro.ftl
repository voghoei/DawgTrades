<#macro categoryRow categoryMap categoryID="0" indent=0>
<#list categoryMap[categoryID] as category>
	<#assign stringID="${category.getId()}">
	<li class="list-group-item category-list-item">
		<div class="list-group-item-heading clearfix">
			<a href="/admin/editCategory?id=${category.getId()}" class="btn btn-link">${category.getName()}</a>
			<a class="btn btn-danger pull-right" href="/admin/deleteCategory?id=${category.getId()}"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Delete</a>
		</div>
	<#if categoryMap[stringID]??>
		<ul class="list-group list-group-item-text lead-no-bottom-margin">
			<@categoryRow categoryMap=categoryMap categoryID=stringID />
		</ul>
	</#if>
	</li>
</#list>
</#macro>