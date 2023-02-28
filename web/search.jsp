<%-- 
    Document   : search
    Created on : Sep 12, 2022, 1:02:30 PM
    Author     : MSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <title>SEARCHING PAGE</title>
    </head>
    <body>

        <script type="text/javascript" src="js/jquery-3.6.1.min.js"></script>
        <script type="text/javascript" src="js/jquery.validate.js"></script>
        <script type="text/javascript" src="js/additional-methods.js"></script>
        <script type="text/javascript" src="js/common.js"></script>

        <script type="text/javascript">

            function validateForm(oForm) {
                var userName = document.forms["searchForm"]["txtUsername"].value;
                var userEmail = document.forms["searchForm"]["txtEmail"].value;
                var userPhone = document.forms["searchForm"]["txtPhone"].value;

                if (userName.length < 2) {
                    alert("Username length must be greater than 2");
                    return false;
                }

                var emailRgEx = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;

                if (!emailRgEx.test(userEmail)) {
                    alert("Please fill out a valid email address");
                    return false;
                }

                var phoneRgEx = /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/;

                if (!phoneRgEx.test(userPhone)) {
                    alert("Please fill out a valid phone");
                    return false;
                }

                var _validFileExtensions = [".jpg", ".jpeg", ".png"];
                var arrInputs = oForm.getElementsByTagName("input");
                for (var i = 0; i < arrInputs.length; i++) {
                    var oInput = arrInputs[i];
                    if (oInput.type === "file") {
                        var sFileName = oInput.value;
                        if (sFileName.length > 0) {
                            var blnValid = false;
                            for (var j = 0; j < _validFileExtensions.length; j++) {
                                var sCurExtension = _validFileExtensions[j];
                                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() === sCurExtension.toLowerCase()) {
                                    blnValid = true;
                                    break;
                                }
                            }

                            if (!blnValid) {
                                alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
                                return false;
                            }
                        }
                    }
                }
            }

        </script>


        <c:if test="${not empty sessionScope.USERNAME}">
            <font color="red">
            Welcome, ${sessionScope.USERNAME}
            </font>
            <form action="MainController">
                <input type="submit" value="Logout" name="btnAction" />
            </form>
            <h1>Searching Page</h1>           
            <c:if test="${not empty sessionScope.ADMIN}">
                <a href="insertAccount.jsp">Click here to insert account</a> <br/>
                <a href="promotionPage.jsp">Click here to move to Promotion Page</a> <br/>
                <c:set var="result" value="${sessionScope.ROLENAME}"/>
                <c:if test="${not empty result}">
                    <c:forEach var="roleName" items="${result}">
                        <c:set var="marked" value="${sessionScope.MARKED}"/>
                        <c:if test="${marked == roleName}">
                            <c:url var="urlRewritting" value="MainController">         
                                <c:param name="btnAction" value="Search"/>
                                <c:param name="chosenRoleName" value="${roleName}"/>
                            </c:url>
                            <!-- Set var bên ngoài c:url vì c:url là bên phía Server, không thể set Param -->
                            <c:set var="chosenRole" value="${param.chosenRoleName}"/>
                            <a href="${urlRewritting}">
                                <font color="red">
                                ${roleName}
                                </font>
                            </a>
                        </c:if>
                        <c:if test="${marked != roleName}">
                            <c:url var="urlRewritting" value="MainController">         
                                <c:param name="btnAction" value="Search"/>
                                <c:param name="chosenRoleName" value="${roleName}"/>
                            </c:url>
                            <!-- Set var bên ngoài c:url vì c:url là bên phía Server, không thể set Param -->
                            <c:set var="chosenRole" value="${param.chosenRoleName}"/>
                            <a href="${urlRewritting}">
                                ${roleName}
                            </a>
                        </c:if>
                    </c:forEach>   
                </c:if>
                <br/>
                <form action="MainController">
                    Search Username <input type="text" name="txtSearch" 
                                           value="${param.txtSearch}" />
                    <input type="submit" value="Search" name="btnAction" />
                    <input type="hidden" name="lastChosenRole" value="${chosenRole}" />
                </form>
                <br/>
                <c:set var="searchValue" value="${param.txtSearch}"/>
                <c:set var="result" value="${sessionScope.RESULT}"/>
                <c:if test="${not empty result}">
                    <c:set var="count" value="${1}"/>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>UserID</th>
                                <th>Rolename</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Email</th>
                                <th>Photo</th>
                                <th>Phone</th>
                                <th>Delete</th>
                                <th>Update</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dto" items="${result}">
                            <form action="MainController" method="POST" 
                                  enctype="multipart/form-data">
                                <tr>
                                    <td>
                                        ${dto.userID}
                                        <input type="hidden" name="txtUserID" 
                                               value="${dto.userID}" /> 
                                    </td>
                                    <td>
                                        <c:if test="${dto.userID == sessionScope.USERID}">                                      
                                            ${dto.roleName}
                                        </c:if>
                                        <c:if test="${dto.userID != sessionScope.USERID}">
                                            <c:set var="roleSystem" value="${sessionScope.DEFAULTROLENAME}"/>
                                            <c:if test="${not empty roleSystem}">  
                                                <select name="txtRolename">
                                                    <c:forEach var="roleUser" items="${roleSystem}">
                                                        <c:if test="${roleUser == dto.roleName}">
                                                            <option selected="selected" value="${roleUser}">${roleUser}</option>
                                                        </c:if>
                                                        <c:if test="${roleUser != dto.roleName}">
                                                            <option value="${roleUser}">${roleUser}</option> 
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </c:if>
                                            <c:if test="${empty roleSystem}">
                                                <h1>
                                                    <font color="red">
                                                    Something wrong, can not get a list of RoleName
                                                    </font>
                                                </h1>
                                            </c:if>      
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:set var="inputUsername" value="${'inputUsername'}${count}"/>
                                        <input id="${inputUsername}" type="text" name="txtUsername" value="${dto.username}" required/>
                                    </td>
                                    <td>
                                        <c:set var="inputPassword" value="${'inputPassword'}${count}"/>
                                        <input id="${inputPassword}" type="password" name="txtPassword" placeholder="Input new password if you want to change" 
                                               size="40" value="" onchange="changePasswordByAdmin('${inputPassword}')" onblur="cancelToChangePassword('${inputPassword}')"/>
                                    </td>
                                    <td>
                                        <c:set var="inputEmail" value="${'inputEmail'}${count}"/>
                                        <input id="${inputEmail}" type="text" name="txtEmail" value="${dto.email}" required/>
                                    </td>
                                    <td>
                                        <img src="data:image/jpeg;base64,${dto.photo}" alt="None Image" width="150" height="150"/>
                                        <br/>
                                        <c:set var="inputPhoto" value="${'inputPhoto'}${count}"/>
                                        <input id="${inputPhoto}" type="file" name="fileUser" value="" />
                                    </td>
                                    <td>
                                        <c:set var="inputPhone" value="${'inputPhone'}${count}"/>
                                        <input id="${inputPhone}" type="text" name="txtPhone" value="${dto.phone}" required/>
                                    </td>
                                    <td>
                                        <c:url var="urlRewritting" value="MainController">
                                            <c:param name="btnAction" value="Delete"/>
                                            <c:param name="pk" value="${dto.userID}"/>
                                            <c:param name="lastSearchValue" value="${searchValue}"/>
                                        </c:url>
                                        <a href="${urlRewritting}">Delete</a>
                                        <br/>
                                        <c:set var="deletedFail" value="${requestScope.DELETEERROR}"/>
                                        <c:if test="${sessionScope.USERID == dto.userID}">
                                            <c:if test="${not empty deletedFail}">
                                                <font color="red">
                                                ${deletedFail}
                                                </font> 
                                            </c:if>
                                        </c:if>
                                    </td>
                                    <td>
                                        <input type="submit" value="Update" name="btnAction" 
                                               onclick="return checkUserInput('${inputUsername}', '${inputEmail}', '${inputPhone}', '${inputPhoto}')" />
                                        <input type="hidden" name="lastSearchValue" value="${searchValue}" />
                                    </td>
                                </tr>    
                            </form>
                            <c:set var="count" value="${count + 1}"/>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty result}">
                <font color="red">
                <h1>
                    No result
                </h1>
                </font>
            </c:if>
        </c:if>
        <c:if test="${empty sessionScope.ADMIN}">
            <c:set var="userInfo" value="${sessionScope.USERINFO}"/>
            <c:if test="${not empty userInfo}">
                <form action="MainController" method="POST" 
                      enctype="multipart/form-data" name="searchForm" onsubmit="return validateForm(this)">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>UserID</th>
                                <th>Rolename</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Email</th>
                                <th>Photo</th>
                                <th>Phone</th>
                                <th>Update</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    ${userInfo.userID}
                                    <input type="hidden" name="txtUserID" 
                                           value="${userInfo.userID}" />
                                </td>
                                <td>
                                    ${userInfo.roleName}       
                                </td>
                                <td>
                                    <input type="text" name="txtUsername" value="${userInfo.username}" required/>
                                </td>
                                <td>
                                    <c:set var="inputPassword" value="${'inputPassword'}"/>
                                    <input id="${inputPassword}" placeholder="Input new password if you want to change" size="40" 
                                           type="password" value="" onchange="return changePassword('${inputPassword}')"/>
                                </td>
                                <td>
                                    <input type="text" name="txtEmail" value="${userInfo.email}" required/>
                                </td>
                                <td>
                                    <img src="data:image/jpeg;base64,${userInfo.photo}" alt="None Image" width="150" height="150"/>
                                    <br/>
                                    <input type="file" name="fileUser" value="" />
                                </td>
                                <td>
                                    <input type="text" name="txtPhone" value="${userInfo.phone}" required/>
                                </td>
                                <td>
                                    <input id="changedPasswordAlready" type="hidden" name="txtPassword" value="" />
                                    <input type="submit" value="Update" name="btnAction" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </c:if>
        </c:if>
    </c:if>
    <c:if test="${empty sessionScope.USERNAME}">
        <c:redirect url="/404.html"/>
    </c:if>
</body>
</html>
