<#import "default.ftl" as default>
<@default.mainLayout "Create Auction">
<h1> Create Auction </h1>
<div class="row">
	<form role="form" action="createAuction" method="post">
		<!-- create item stuff goes here -->
		<div class="form-group">
		<#if item??>
			<h3>${item.getName()}</h3>
			<p> ${item.getDescription()}</p>
		<#else>
			<p class="form-control-static"><a id="createItemLink"  href="/createItem">Add an Item</a></p>
		</#if>
		</div>
		<div class="form-group">
			<label for="expiration"> Auction Expiration Date </label>
			<input type="datetime" class="form-control" name="expiration">
		</div>
		<div class="form-group">
			<label for="minPrice"> Minimum Price for Auction Item </label>
			<input type="number" class="form-control" name="minPrice">
		</div>
		<button type="submit" class="btn btn-default"> Create Auction</button>
	</form>
</div>
</@default.mainLayout>
