<#macro categoryList categoryMap categoryID="0" indent=0>
<#list categoryMap[categoryID] as category>
	<option value="${category.getId()}"><#list 1..indent as i>  </#list>${category.getName()}</option>
	<#assign stringID="${category.getId()}">
	<#if categoryMap[stringID]??>
			<@categoryList categoryMap=categoryMap categoryId=stringID indent=indent+1 />
	</#if>
</#list>
</#macro>