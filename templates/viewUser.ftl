<#import "default.ftl" as default>
<@default.mainLayout "DawgTrades - View User: ${viewUser.getFirstName()} ${viewUser.getLastName()}">
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
		<span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
		<span class="sr-only">Message:</span>
		${message}
	</div>
</div>
</#if>
<h1>Viewing Profile: ${viewUser.getFirstName()} ${viewUser.getLastName()}</h1>
<#assign phoneNumber = viewUser.getPhone()>
<div class="row">
	<div class="col-md-12">
		<p><span class="glyphicon glyphicon-phone" aria-hidden="true"></span> (${phoneNumber?substring(0, 3)}) ${phoneNumber?substring(3, 6)}-${phoneNumber?substring(6)} (<#if viewUser.getCanText()>Accepts Texts<#else>Does Not Accept Texts</#if>)</p>
	</div>
</div>
<div class="row">
	<div class="col-md-3">
		<div class="panel panel-default">
		  <div class="panel-heading">
		  Side Panel
		  </div>
		</div>
	</div>
	<div class="col-md-9">
		<div class="panel panel-default">
		  <div class="panel-heading">
		  	Auctions
		  </div>
		  <div class="panel-body">
			<#if userAuctions?size gt 0>
				<#list userAuctions?chunk(3) as auctionRow>
					<div class="row">
						<#list auctionRow as auction>
						<#assign auctionID = "${auction.getId()}">
							<div class="col-md-4">
								<h5><a href="/auction?id=${auction.getId()}">${items[auctionID].getName()}</a></h5>
								<p><#if auction.getIsClosed()>Ended<#else>Ends</#if> on ${auction.getExpiration()?datetime}</p>
								<p>Minimum bid: $${auction.getMinPrice()}</p>
								<p>Current bid: <#if auctionBids[auctionID]??>$${auctionBids[auctionID].getAmount()}<#else>None</#if></p>
								<p>Status: <#if auction.getIsClosed()>Closed<#else>Open</#if></p>
							</div>
						</#list>
					</div>
				</#list>
			<#else>
				<p class="lead lead-no-bottom-margin text-center">No auctions found.</p>
			</#if>
		  </div>
	</div>
</div>
</@default.mainLayout>