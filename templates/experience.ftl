<#import "default.ftl" as default>
<@default.mainLayout "Settings">
<h1>Experience Report</h1>
<form role="form" action="experience" method="post">

<div class="form-group">
    <#if value??>
	<label for="value">  value = ${value} </label>
    <#else>
        <label for="value">  value =  </label>
    </#if>
</div>
    <button type="submit" > Submit </button>
</div>
<div class="form-group">
    <#if mlist??>   
        <select id = "listId" name = "listId" class="form-control">
        <#list mlist as membership>            
                <option value=${membership.getId()}>${membership.getPrice()}</option>
            
        </#list>
        </select>
    </#if>
  
</div>

</form>
</@default.mainLayout>
