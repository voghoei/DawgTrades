<#import "default.ftl" as default>
<@default.mainLayout "DawgTrades: Auction">
<h1>Delete Auction</h1>
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
<div class="row">
	<div class="col-md-12">
		<p>Are you sure you want to delete this auction?</p>
		<form role="form" action="delete" method="post" class="clearfix">
			<input type="hidden" name="id" id="id" value="${toDelete}" />
			<div class="form-group pull-right">
				<a class="btn btn-default" href="${baseContext}/auction?id=${toDelete}">Cancel</a> <button type="submit" class="btn btn-danger">Confirm</button>
			</div>
		</form>
	</div>
</div>
</@default.mainLayout>
