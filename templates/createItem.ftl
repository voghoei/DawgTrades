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
			<#if categoryList??>
			<form name="categoryForm" action="createItem" method="POST">
				<select id="category" name="category" onchange="document.categoryForm.submit();">
					<option value="#" disabled>Select Category</option>
					<#list categoryList as category><option value="${category.getId()}">${category.getName()}</option>
					</#list>
				</select>
			</form>
			<#else>
			<p> No categories found</p>
			</#if>
			<#if attributes??>
				<#list attributes as attribute>
				<label for="attribute"></label>
				<input type="text" class="form-control" name="attribute">
				</#list>
			</#if>

		</div>
		<button type="submit" class="btn btn-default"> Submit </button>
	</form>
</div>
</@default.mainLayout>
