<#import "default.ftl" as default>
<@default.mainLayout "Create Auction">
<h1> Create Auction </h1>
<div class="row">
	<#assign keys = categoryList?keys>
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
			<form name="categoryForm" action="categorySelect" method="POST">
				<select id="category" name="category" onchange="document.categoryForm.submit();">
					<option value="#" disabled>Select Category</option>
					<#list keys as key><option value="${key}">${categoryList[key]}</option>
					</#list>
				</select>
			</form>
		</div>
		<button type="submit" class="btn btn-default"> Create Item</button>
	</form>
</div>
</@default.mainLayout>
