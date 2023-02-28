<%-- 
    Document   : promotionPage
    Created on : Sep 19, 2022, 12:07:38 PM
    Author     : MSI
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/common.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/common.js"></script>
        <title>Promotion Page</title>
    </head>
    <body>
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
            <h1>Welcome to Promotion Page</h1>
            <a href="search.jsp">Click here to go back Search Page</a> <br/>
            <a href="insertAccount.jsp">Click here to go back Insert Account Page</a>

            <c:set var="result" value="${sessionScope.NORMALACCOUNTS}"/>
            <h1> This is list of users's account in the system</h1>
            <div class="promotionPage">
                <c:if test="${not empty result}">
                    <c:set var="addResult" value="${requestScope.MESSAGE}"/>
                    <c:if test="${not empty addResult}">
                        <h2>
                            <font color="red">
                            ${addResult}
                            </font>
                        </h2>
                    </c:if>
                    <form action="MainController" method="GET">
                        <br/>
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>UserID</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="dtoUser" items="${result}" varStatus="counter">
                                    <tr>
                                        <td>
                                            ${dtoUser.userID}           
                                            <c:set var="txtUser" value="${dtoUser.userID}"/>
                                        </td>
                                        <td> 
                                            <input id="confirm" type="button" value="Add" name="" onclick="popupConfirmWindow('confirmWindow', '${txtUser}')"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div id="warningMessage">
                            <div id="warningMessage_content">
                                <div id="confirmWindow">
                                    <h2>Click confirm to add selected item.</h2> <br/>
                                    <div>
                                        <input id="selectedUserID" type="hidden" name="txtUserID" value="" />
                                        <input class="cancellation" type="button" name="" value="Cancel" onclick="closeConfirmWindow('confirmWindow')">
                                        <input class="confirm" type="submit" name="btnAction" value="AddPromotion">
                                    </div>
                                </div>
                            </div>
                        </div> 
                    </form>
                </c:if> 
                <c:if test="${empty result}">
                    <h2>
                        <font color="red">
                        Account list does not have any normal accounts (Not Admin Role)
                        </font>
                    </h2>
                </c:if>
                <br/>
                <c:set var="promotionResult" value="${sessionScope.PROMOTIONRESULT}"/>
                <h1> This is list of promotion in the system</h1> <br/>
                <c:if test="${not empty promotionResult}">
                    <c:set var="deleteResult" value="${requestScope.DELETEDMESSAGE}"/>
                    <c:if test="${not empty deleteResult}">
                        <h2>
                            <font color="red">
                            ${deleteResult}
                            </font>
                        </h2>
                    </c:if>
                    <c:set var="updateResult" value="${requestScope.UPDATEDMESSAGE}"/>
                    <c:if test="${not empty updateResult}">
                        <h2>
                            <font color="red">
                            ${updateResult}
                            </font>
                        </h2>
                    </c:if>
                    <form action="MainController" method="POST">                   
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>PromotionID</th>
                                    <th>UserID</th>
                                    <th>Rank</th>
                                    <th>Delete</th>
                                    <th>Update</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="promotionDTO" items="${promotionResult}">
                                    <tr>
                                        <td>
                                            ${promotionDTO.promotionID} 
                                            <c:set var="txtPromotion" value="${promotionDTO.promotionID}"/>
                                        </td>
                                        <td>
                                            ${promotionDTO.userID} 
                                            <c:set var="txtUser" value="${promotionDTO.userID}"/>
                                        </td>
                                        <td>
                                            ${promotionDTO.rank}
                                            <c:set var="rankUser" value="${promotionDTO.rank}"/>
                                        </td>
                                        <td>
                                            <input id="delete" type="button" value="Delete" name="" onclick="popupDeleteWindow('deleteWindow', '${txtUser}', '${txtPromotion}')"/>
                                        </td>
                                        <td>
                                            <input id="update" type="button" value="Update" name="" onclick="popupUpdateWindow('updateWindow', '${txtUser}', '${txtPromotion}', '${rankUser}')"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div id="warningDeleteMessage">
                            <div id="warningDeleteMessage_content">
                                <div id="deleteWindow">
                                    <h2>Click confirm to delete selected item.</h2> <br/>
                                    <div>
                                        <input id="userIDPromotion" type="hidden" name="txtUserID" value="" />
                                        <input id="promotionID" type="hidden" name="txtPromotionID" value="" />
                                        <input class="cancellation" type="button" name="" value="Cancel" onclick="closeDeleteWindow('deleteWindow')"/>
                                        <input class="delete" type="submit" name="btnAction" value="DeletePromotion"/>
                                    </div>
                                </div>
                            </div>
                        </div> 
                        <div id="warningUpdateMessage">
                            <div id="warningUpdateMessage_content">
                                <div id="updateWindow">
                                    <h2>Click confirm to update selected item.</h2> <br/>
                                    <input type="number" id="fillInRank"
                                           pattern="^[0-9]+$" 
                                           value="${rankUser}"
                                           name="txtRank"
                                           min="1"
                                           max="10"
                                           maxlength="2"
                                           title="Only number and the range is 1-10"
                                           required="required"/>

                                    <div>
                                        <input id="userIDOfPromotion" type="hidden" name="selectedUserID" value="" />
                                        <input id="promotionIDOfUser" type="hidden" name="selectedPromotionID" value="" />
                                        <input class="cancellation" type="button" name="" value="Cancel" onclick="closeUpdateWindow('updateWindow')"/>
                                        <input class="update" type="submit" name="btnAction" value="UpdatePromotion"/>
                                    </div>
                                </div>
                            </div>
                        </div> 
                    </form>
                </c:if>
                <c:if test="${empty promotionResult}">
                    <h2>
                        <font color="red">
                        Promotion list is empty
                        </font>
                    </h2>
                </c:if>
            </div>
        </c:if>
        <c:if test="${empty admin}">
            <c:redirect url="/search.jsp"/>
        </c:if>
    </body>
</html>
