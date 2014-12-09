<#import "default.ftl" as default>
<#import "categoryListMacro.ftl" as catList>
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
                        <#if categoryChosen??>
                                <label for="category"> Category:</label>
                                <select name="catId" class="form-control" onChange="window.location.href='createAuction?id=' + this.value" required>
                        <option value="#" disabled> Select a Category </option>
                        <#if categoriesMap??>
                                                <@catList.categoryList categoryMap=categoriesMap selected="${categoryChosen.getId()}" />
                                        <#else>
                                                <option value="#" disabled> No Categories </option>
                                        </#if>
                                </select>
                        <#else>
                                <label for="category"> Category </label>
                                <select name="catId" class="form-control" onChange="window.location.href='createItem?id=' + this.value" required>
                        <option value="#" disabled> Select a Category </option>
                        <#if categoriesMap??>
                                                <@catList.categoryList categoryMap=categoriesMap/>
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
                                        <input type="text" class="form-control" name="${attribute.getId()}" required>
                                        </#list>
                                </div>
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
