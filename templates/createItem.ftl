<#import "default.ftl" as default>
<@default.mainLayout "Create Item">
<h1> Create Item </h1>

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
			<#if categoryChosen??>
				<label for="category"> Category:</label>
 				<select class="form-control" onChange="window.location.href=this.value" required>
                        	<option value="#"> Select a Category </option>
                        	<#if categoryList??>
                                        <#list categoryList as category>
					<#if category.getId() == categoryChosen.getId()>
					<option value="/createItem?id=${category.getId()}" selected>${category.getName()}</option>
					<#else>
					<option value="/createItem?id=${category.getId()}">${category.getName()}</option>
					</#if>
                                        </#list>
				</#if>
				</select>
			<#else>
				<label for="category"> Category </label>
				<select class="form-control" onChange="window.location.href=this.value" required>
				<option value="#"> Select a Category </option>
				<#if categoryList??>					
					<#list categoryList as category><option value="/createItem?id=${category.getId()}">${category.getName()}</option>
					</#list>
				<#else>
					<option value="#" disabled> No Categories </option>
				</#if>
				</select>
			</#if>
		<div class="form-group">
                        <label for="name"> Item Name </label>
                        <input type="text" class="form-control" name="name" required>
                </div>
                <div class="form-group">
                        <label for="desc"> Item Description</label>
                        <textarea class="form-control" rows="3" name="desc"></textarea>
                </div>

			<#if attributes??>
				<div class="form-group">
					<#list attributes as attribute>
					<label class="control-label" for="attribute">${attribute.getName()}</label>
					<input type="text" class="form-control" name="attribute" required>
					</#list>
				</div>
			</#if>

		</div>
		<button type="submit" value="submit" class="btn btn-default"> Submit </button>
	</form>
</div>
</@default.mainLayout>
