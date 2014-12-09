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
		<ul class="list-group">
		<#list auctions as auction>
			<a href="/auction?id=${auction.getId()}"><li class="list-group-item">${auction.getId()}</li></a>
		</#list>
		</ul>
	<#else>
		<h2> You don't have any active auctions</h2>
	</#if>
</div>
</@default.mainLayout>
