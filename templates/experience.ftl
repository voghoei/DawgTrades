<#import "default.ftl" as default>
<@default.mainLayout "Settings">
<h1>Experience Report</h1>
<#if error??>
        <div class="col-md-12 alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only"> Error: </span>
                ${error}
        </div>
</#if>
<div class="row">
	<form role="form" action="experience" method="post">
		<!-- create item stuff goes here -->
		
		<div class="form-group">
                        <#if value??>
			<label for="value"> ${value} </label>
                        </#if>
			
		</div>
                <button type="submit" class="btn btn-default"> Submit </button>
        </form>
 </br></br>
<div class="form-group">
<h4>Membership History</h4>
  
    <#if mlist??>   
        <select name = "listId" class="form-control">
        <#list mlist as membership>            
                <option>${membership.getPrice()}</option>
            
        </#list>
        </select>
    </#if>
  
</div>

</div>
</@default.mainLayout>
