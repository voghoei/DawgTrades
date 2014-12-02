<#import "default.ftl" as default>
<@default.mainLayout "Register">
<h1>Register for DawgTrades</h1>
<div class="row">
	<form role="form">
		<div classs="form-group">
			<label for="name">Name</label>
			<input type="text" class="form-control" placeholder="Name">
		</div>
		<div classs="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" placeholder="Email">
                </div>
		<div classs="form-group">
                        <label for="password">Enter a password</label>
                        <input type="password" class="form-control" placeholder="Password">
                </div>
                <div classs="form-group">
                        <label for="passwordReEnter">Re-enter password</label>
                        <input type="password" class="form-control" placeholder="Re-enter Password">
                </div>
                <div classs="form-group">
                        <label for="phone">Phone Number</label>
                        <input type="tel" class="form-control" placeholder="Phone Number">
                </div>



	</form>
</div>
</@default.mainLayout>
