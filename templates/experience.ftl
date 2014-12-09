<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

<script>
$( document ).ready(function() {
    $("#listId").change(function() {
    $("#show").click();
    });
});
</script>

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
    <div class="form-group">
        <#if users??>   
            <select id = "listId" name = "listId" class="form-control">
            <#list users as user>            
                    <option value=${user.getId()}>${user.getFirstName()}  ${user.getLastName()}</option>
            </#list>
            </select>
        </#if>

        <div class="form-group">
            
            <input type="text" name="report" class="form-control" >
	</div>
        <button id= "save" name="save" type="submit" > Submit </button>
        <button id= "show" name="show" type="submit" style="display: none;"> Submit </button>

    </div>

    </form>
</div>


</div>

</@default.mainLayout>
