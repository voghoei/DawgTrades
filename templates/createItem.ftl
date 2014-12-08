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

	<form role="form" action="createItem" method="post">
		<!-- create item stuff goes here -->
		
		<div class="form-group">
			<label for="name"> Item Name </label>
			<input type="text" class="form-control" name="name">
		</div>
		<div class="form-group">
			<label for="desc"> Item Description</label>
			<input type="textarea" class="form-control" name="desc">
		</div>
		<div class="form-group">
			<label for="category"> Category </label>
			<select onChange="window.location.href=this.value">
			<option value="#" disabled> Select a Category </option>
			<#if categoryList??>					
					<#list categoryList as category><option value="/createItem?id=${category.getId()}">${category.getName()}</option>
					</#list>
			<#else>
			<option value="#" disabled> No Categories </option>
			</#if>
			<#if attributes??>
				<#list attributes as attribute>
				<label for="attribute">${attribute.getName()}</label>
				<input type="text" class="form-control" name="attribute">
				</#list>
			<#else>
				<p> No Attributes </p>
			</#if>

		</div>
		<button type="submit" class="btn btn-default"> Submit </button>
	</form>
</div>
</@default.mainLayout>
