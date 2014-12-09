<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
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

<#if value??>
value is ${value}
</#if>

<#if userselct??>
        ${userselct}
</#if>

<div class="row">
    <form id="f1" role="form" action="experience" method="post">
    <div class="form-group">
        
        <#if users??>   
            <select id = "listId" name = "listId" class="form-control">
            <#list users as user>  
                <#if userselct?? && userselct == user.getId()?string>          
                    <option selected value= ${user.getId()} >${user.getFirstName()}  ${user.getLastName()}</option>
                <#else>
                    <option value=${user.getId()}>${user.getFirstName()}  ${user.getLastName()}</option>
                </#if>
            </#list>
            </select>
        </#if>

        <div class="form-group">
            
            <input type="text" name="report" class="form-control" >
	</div>
        <button id= "save" name="save" type="submit" > Save </button>
        <button id= "show" name="show" type="submit" > Show Reports </button>

    </div>

    <div class="form-group">
    <h4>Report History</h4>
      <table class="table table-striped">

        <tr>
            <th>Report</th>  <th>Rate</th> <th>Date</th>
        </tr>
        <#if reports??>

            <#list reports as report>
                <tr>
                    <td>${report.getReport()}</td> <td>${report.getRating()}</td> <td>${report.getDate()}</td>
                </tr>
            </#list>
        </#if>
      </table>
    </div>


    </form>
</div>


</div>

</@default.mainLayout>
