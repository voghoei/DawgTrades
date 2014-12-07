<#macro categoryRow categoryMap categoryID="0">
<#list categoryMap[categoryID] as category>
	<li class="list-group-item category-list-item clearfix">
		<a href="/admin/editCategory?id=${category.getId()}" class="btn btn-link">${category.getName()}</a>
		<a class="btn btn-danger pull-right" href="/admin/deleteCategory?id=${category.getId()}">Delete</a>
		<#assign stringID="${category.getId()}">
		<#if categoryMap[stringID]??>
			<ul class="list-group">
				<@categoryRow categoryMap=categoryMap categoryId=stringID />
			</ul>
		</#if>
	</li>
</#list>
</#macro>