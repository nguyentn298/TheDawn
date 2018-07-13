<%@ include file="taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<nav class="navbar navbar-inverse" data-spy="affix"
	data-offset-top="197">
	<ul class="nav navbar-nav">
		<li class="active"><a href="#">Basic Topnav</a></li>
		<li><a href="${contextPath}/admin">admin</a></li>
		<li><a href="${contextPath}/user">user</a></li>
		<li><a href="#">Page 3</a></li>
	</ul>
	<div style="float: right; width: 20%">
		<div>
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<h4 style="margin-top: 15px">
					${pageContext.request.userPrincipal.name} | <a
						href="javascript:formSubmit()"> Logout</a>
				</h4>
			</c:if>
		</div>
	</div>

	<form action="<c:url value='/logoutSuccess' />" method="POST" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="logout"
			value="Good bye" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
</nav>
<!-- <hr /> -->
