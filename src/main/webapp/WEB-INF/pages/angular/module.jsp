<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h4>
		An AngularJS module defines an application.

		The module is a container for the different parts of an application.
	
		The module is a container for the application controllers.
	
		Controllers always belong to a module.
	</h4>
	<div ng-app="myApp" ng-controller="myController">
		{{ first + " " + lastName }}
		<script>
			var app = angular.module("myApp", []);
			app.controller(myController, function(scope)) {
				$scope.firstName = "one";
				$scope.lastName = "piece";
			}
		</script>
	</div>
</body>
</html>