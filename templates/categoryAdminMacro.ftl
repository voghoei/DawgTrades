<#macro categoryRow categoryMap categoryID="0">
<#list categoryMap[categoryID] as category>
	<li class="list-group-item category-list-item">
		<a href="/admin/editCategory?id=${category.getId()}">${category.getName()}</a>
		<a class="btn btn-default pull-right" href="/admin/deleteCategory?id=${category.getId()}">Delete</a>
		<#assign stringID="${category.getId()}">
		<#if categoryMap[stringID]??>
			<ul class="list-group">
				<@categoryRow categoryMap=categoryMap categoryId=stringID />
			</ul>
		</#if>
	</li>
</#list>
</#macro>