<#macro categoryList categoryMap categoryID="0" indent=0>
<#list categoryMap[categoryID] as category>
	<option value="${category.getId()}"><#if indent gt 0><#list 1..indent as i>--</#list> </#if>${category.getName()}</option>
	<#assign stringID="${category.getId()}">
	<#if categoryMap[stringID]??>
			<@categoryList categoryMap=categoryMap categoryID=stringID indent=indent+1 />
	</#if>
</#list>
</#macro>