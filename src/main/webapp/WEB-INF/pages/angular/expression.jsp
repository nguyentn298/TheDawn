<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<h4>
	The ng-app directive defines an AngularJS application.<br/>

	The ng-model directive binds the value of HTML controls (input, select, textarea) to application data.<br/>

	The ng-bind directive binds application data to the HTML view. <br/>
	
	The ng-app directive tells AngularJS that the <div> element is the "owner" of an AngularJS application.<br/>

	The ng-model directive binds the value of the input field to the application variable name.<br/>

	The ng-bind directive binds the innerHTML of the <p> element to the application variable name. <br/>
	
	The ng-init directive initializes AngularJS application variables<br/>
</h4>
<div ng-app="" ng-init="firstName='one';lastName='piece 2'" style="color:white">
	<h3>Test expression 1 (operator)</h3>
	<p>{{5 + 5}}</p>
	
	<h3>Test expression 2 (assign variable)</h3>
	<p >{{firstName + " " + lastName}}</p>
	<p ng-bind="firstName + ' ' + lastName"></p>
	
	<h3>Test expression 3 (Change color)</h3>
	<p ng-init="test='green'">
		<input type="text" ng-model="test" value="{{ test }}" style="background-color: {{test}}"/>
		<p ng-bind="test"></p>
	</p>
	
	<h3>Test expression 4 (init object)</h3>
	<p ng-init="person={firstName:'one',lastName:'piece 3'}"></p>
	<p>ng-init="person={firstName:'one',lastName:'piece 3'}" ======> {{ person.lastName }}</p>
	
	<h3>Test expression 5 (init array)</h3>
	<p ng-init="myArray=[1,3,5,7,9]"></p>
	<p>ng-init="myArray=[1,3,5,7,9]" ======> {{ myArray[4] }}</p>
	
</div>

</body>
</html>