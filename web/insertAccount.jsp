<%-- 
    Document   : insertAccount
    Created on : Sep 19, 2022, 7:59:37 AM
    Author     : MSI
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-3.6.1.min.js"></script>
        <script type="text/javascript" src="js/jquery.validate.js"></script>
        <script type="text/javascript" src="js/additional-methods.js"></script>
        <title>INSERTING ACCOUNT PAGE</title>
    </head>
    <body>      

        <script type="text/javascript">
            $(function () {
                $("#registerForm").validate({
                    rules: {
                        txtUserIDUp: {
                            required: true,
                            rangelength: [6, 20]
                        },
                        txtPasswordUp: {
                            required: true,
                            rangelength: [6, 20]
                        },
                        txtReUp: {
                            equalTo: "#txtPass"
                        },
                        txtUsernameUp: {
                            required: true,
                            rangelength: [2, 100]
                        },
                        txtEmailUp: {
                            required: true,
                            email: true
                        },
                        txtPhoneUp: {
                            required: true,
                            mobileVN: true
                        },
                        fileUp: {
                            required: true,
                            maxsize: 2000000,
                            extension: "png|jpg|jpeg"
                            
                        }
                    }
                });
            });
        </script>
        <c:set var="admin" value="${sessionScope.ADMIN}"/>
        <c:if test="${not empty admin}">
            <c:if test="${not empty sessionScope.USERNAME}">
                <font color="red">
                Welcome, ${sessionScope.USERNAME}
                </font>
            </c:if>
            <form action="MainController">
                <input type="submit" value="Logout" name="btnAction" />
            </form>
            <h1>Welcome to Insert Account Page</h1>

            <a href="search.jsp">Go back Search Page</a> <br/>
            <a href="promotionPage.jsp">Click here to move to Promotion Page</a> <br/> <br/>

            <c:set var="result" value="${requestScope.INSERTEDACCOUNT}"/>
            <c:if test="${not empty result}">
                <h1>
                    <font color="red">
                    ${result}
                    </font>
                </h1>
            </c:if>
            <form action="MainController" method="POST" enctype="multipart/form-data" id="registerForm">
                UserID <input type="text" name="txtUserIDUp" value="" /> <br/> <br/>
                Password <input type="password" name="txtPasswordUp" value="" id="txtPass"/> <br/> <br/>
                Retype Password <input type="password" name="txtReUp" value="" /> <br/> <br/>

                Rolename 
                <c:set var="roleSystem" value="${sessionScope.DEFAULTROLENAME}"/>
                <c:if test="${not empty roleSystem}">
                    <select name="txtRolenameUp">
                        <c:forEach var="role" items="${roleSystem}">
                            <option selected="selected" value="${role}">${role}</option> 
                        </c:forEach>
                    </select>
                </c:if> <br/> <br/>
                Username <input type="text" name="txtUsernameUp" value="" /> <br/> <br/>
                Email <input type="text" name="txtEmailUp" value="" /> <br/> <br/>
                Phone <input type="text" name="txtPhoneUp" value="" /> <br/> <br/>
                Photo <input type="file" name="fileUp" value="" /> <br/> <br/>
                <br/>
                <input type="submit" value="Insert" name="btnAction" />
                <input type="reset" value="Clear" />
            </form>
        </c:if>
        <c:if test="${empty admin}">
            <c:redirect url="/search.jsp"/>
        </c:if>
    </body>
</html>
