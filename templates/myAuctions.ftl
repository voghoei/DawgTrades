<#import "default.ftl" as default>
<@default.mainLayout "My Auctions">
<h1> My Auctions </h1>

        <#if error??>
        <div class="row">
                <div class="col-md-12 alert alert-danger" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="sr-only">Error:</span>
                        ${error}
                </div>
        </div>
        </#if>


<div class="row">
	<#if auctions??>
		<div class="list-group">
		<#list items as item>
			<#assign itemId = "${item.getId()}">
			<#if auctions[itemId]??>
				<a href="/auction?id=${auctions[itemId].getId()}" class="list-group-item <#if auctions[itemId].getIsClosed()>list-group-item-danger<#else>list-group-item-success</#if>">${item.getName()} (<#if auctions[itemId].getIsClosed()>Closed<#else>Open</#if>)</a>
			</#if>
		</#list>
		</div>
	<#else>
		<h2> You don't have any active auctions</h2>
	</#if>
</div>
</@default.mainLayout>
