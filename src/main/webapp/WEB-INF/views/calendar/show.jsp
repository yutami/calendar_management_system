<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>カレンダー表示画面</title>
<link rel="stylesheet" href="<c:url value='/css/calendar.css' />">
</head>
<body>
    <div class="calendar-container">
        <h1><c:out value="${year}" />/ <c:out value="${month}" /></h1>
                 <div class="outer">
                 <div class="inner">
                 <div id="next-prev-button">
                 <form method="GET" action="<c:url value='/calendar/show' />">
                   <button id="prev" >‹</button>
                   </form>
                   <form method="GET" action="<c:url value='/calendar/show' />">
                   <button id="next" >›</button>
                   </form>
                   </div>
                   </div>
                </div>
        <table class="calendar">
            <tr>
                <th>SUN</th>
                <th>MON</th>
                <th>TUE</th>
                <th>WED</th>
                <th>THU</th>
                <th>FRI</th>
                <th>SAT</th>
            </tr>

            <c:forEach var="week" items="${weekList}">
                <tr class="day">
                    <td>
                        <c:out value="${week.sunDate}" />
                        <c:forEach var="receive" items="${week.sunDateReceive}">
                            【ID：<c:out value="${reserve.id}" />】
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.monDate}" />
                        <c:forEach var="receive" items="${week.monDateReceive}">
                            【ID：<c:out value="${resrve.id}" />】
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.tueDate}" />
                        <c:forEach var="receive" items="${week.tueDateReceive}">
                            【ID：<c:out value="${reserve.id}" />】
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.webDate}" />
                        <c:forEach var="receive" items="${week.webDateReceive}">
                            【ID：<c:out value="${reserve.id}" />】
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.thuDate}" />
                        <c:forEach var="receive" items="${week.thuDateReceive}">
                            【ID：<c:out value="${reserve.id}" />】
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.friDate}" />
                        <c:forEach var="receive" items="${week.friDateReceive}">
                            【ID：<c:out value="${reserve.id}" />】
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.satDate}" />
                        <c:forEach var="receive" items="${week.satDateReceive}">
                            【ID：<c:out value="${reserve.id}" />】

                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>