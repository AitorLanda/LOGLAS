<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container" id="usertable">
	<h1>
		<spring:message code="label.userTableTitle" />
	</h1>
	<p>
		<spring:message code="label.userTableMessage" />
   	</p>
   
    	<h1><spring:message code="label.userTableList" /></h1>
        <h3>
            <a id="newUserButton" href="${pageContext.request.contextPath}/register"><spring:message code="label.userTableRegister" /></a>
        </h3>
        <table>
        	<tr>
        		<th><spring:message code="label.userTableId" /></th>
            	<th><spring:message code="label.username" /></th>
            	<th><spring:message code="label.password" /></th>
           	 	<th><spring:message code="label.userTableFname" /></th>
            	<th><spring:message code="label.userTableLname" /></th>
           	 	<th><spring:message code="label.userEmail" /></th>
            	<th><spring:message code="label.userTableAdmin" /></th>
            	<th><spring:message code="label.userTableAction" /></th>
 			</tr>
            <c:forEach items="${userList}" var="user">
                <tr>
 					
 					<td>${user.getId()}</td>
 					<td>${user.getUserName()}</td>
                    <td>${user.getPassword()}</td>
                    <td>${user.getFirstName()}</td>
                    <td>${user.getLastName()}</td>
                    <td>${user.getEmail()}</td>
                    <td>${user.isAdmin()}</td>
                    <td><a id="editUserButton${user.getId()}" href="${pageContext.request.contextPath}/editUser?id=${user.getId()}"><spring:message code="label.userTableEdit" /></a>
                        <a id="deleteUserbutton${user.getId()}" href="${pageContext.request.contextPath}/deleteUser?id=${user.getId()}"><spring:message code="label.userTableDelete" /></a>
                    </td>
 
                </tr>
            </c:forEach>
        </table>
   
   		<div class="my-button">
			<a id="questionTableButton" class="my-button-text" href="${pageContext.request.contextPath}/admin/questionTable">
				<span class="my-button-span">
					<spring:message code="label.userTableManageButton" />
				</span>
			</a>
		</div>
   
</div>
