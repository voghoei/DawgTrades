<#macro categoryRow categoryMap categoryID="0">
<#list categoryMap[categoryID] as category>
	<li class="list-group-item category-list-item clearfix">
		<h5><a href="/admin/editCategory?id=${category.getId()}">${category.getName()}</a></h5>
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