<#import "default.ftl" as default>
<@default.mainLayout "Create Auction">
<h1> Create Auction </h1>
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
			<input type="date" class="form-control" name="expiration">
		</div>
               <div class="form-group">
                        <label for="expiration-time"> Auction Expiration Time </label>
                        <input type="time" class="form-control" name="expiration-time">
                </div>

		<div class="form-group">
			<label for="minPrice"> Minimum Price for Auction Item </label>
			<input type="number" class="form-control" name="minPrice">
		</div>
		<button type="submit" class="btn btn-default"> Create Auction</button>
	</form>
</div>
</@default.mainLayout>
