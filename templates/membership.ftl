<#import "default.ftl" as default>
<@default.mainLayout "Settings">
<h1>Membership</h1>
<#if error??>
        <div class="col-md-12 alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only"> Error: </span>
                ${error}
        </div>
</#if>
<div class="row">
	<form role="form" action="membership" method="post">
		<!-- create item stuff goes here -->
		
		<div class="form-group">
			<label for="price"> Price </label>
			<input type="text" class="form-control" name="price">
		</div>
                <button type="submit" class="btn btn-default"> Submit </button>
        </form>
 </br></br>
<div class="form-group">
<h4>Membership History</h4>
  <table class="table table-striped">
   
    <tr>
        <th>Price</th>  <th>Date</th>
    </tr>
    <#if membershipList??>
    
        <#list membershipList as membership>
            <tr>
                <td>${membership.getPrice()}</td> <td>${membership.getDate()}</td>
            </tr>
        </#list>
    </#if>
  </table>
</div>

</div>
</@default.mainLayout>
