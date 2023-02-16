<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />

<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
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
        <h1>
            <c:out value="${year}" />
            /
            <c:out value="${month}" />
        </h1>
        <div class="outer">
            <div class="inner">
                <div id="next-prev-button">
                    <form method="GET" action="<c:url value=''/>">
                        <input type="hidden" name="${AttributeConst.TOP_ACTION.getValue()}" value="<c:out value='${actTop}' />"/>
                        <input type="hidden" name="${AttributeConst.TOP_COMMAND.getValue()}" value="<c:out value='${commIdx}' />"/>
                        <input type="hidden" name="${AttributeConst.TOP_YEAR.getValue()}" value="<c:out value='${previousYear}' />"/>
                        <input type="hidden" name="${AttributeConst.TOP_MONTH.getValue()}" value="<c:out value='${previousMonth}' />" />
                        <button type="submit" id="prev">‹</button>
                    </form>
                    <form method="GET" action="<c:url value=''/>">
                        <input type="hidden" name="${AttributeConst.TOP_ACTION.getValue()}" value="<c:out value='${actTop}' />"/>
                        <input type="hidden" name="${AttributeConst.TOP_COMMAND.getValue()}" value="<c:out value='${commIdx}' />"/>
                        <input type="hidden" name="${AttributeConst.TOP_YEAR.getValue()}" value="<c:out value='${nextYear}' />"/>
                        <input type="hidden" name="${AttributeConst.TOP_MONTH.getValue()}" value="<c:out value='${nextMonth}' />" />
                        <button type="submit" id="next">›</button>
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
                        <br>
                        <c:forEach var="receive" items="${week.sunDateReceive}">
                            <fmt:parseDate value="${receive.reserve_day}"
                                pattern="yyyy-MM-dd'T'HH:mm" var="reserveDate" type="date" />
                            <a
                                href="<c:url value='?action=${actRep}&command=${commShow}&id=${receive.id}' />">
                                <fmt:formatDate value='${reserveDate}' pattern='HH:mm' />
                            </a>
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.monDate}" />
                        <c:forEach var="receive" items="${week.monDateReceive}">
                            <br>
                            <fmt:parseDate value="${receive.reserve_day}"
                                pattern="yyyy-MM-dd'T'HH:mm" var="reserveDate" type="date" />
                            <a
                                href="<c:url value='?action=${actRep}&command=${commShow}&id=${receive.id}' />">
                                <fmt:formatDate value='${reserveDate}' pattern='HH:mm' />
                            </a>
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.tueDate}" />
                        <c:forEach var="receive" items="${week.tueDateReceive}">
                            <br>
                            <fmt:parseDate value="${receive.reserve_day}"
                                pattern="yyyy-MM-dd'T'HH:mm" var="reserveDate" type="date" />
                            <a
                                href="<c:url value='?action=${actRep}&command=${commShow}&id=${receive.id}' />">
                                <fmt:formatDate value='${reserveDate}' pattern='HH:mm' />
                            </a>
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.webDate}" />
                        <c:forEach var="receive" items="${week.webDateReceive}">
                            <br>
                            <fmt:parseDate value="${receive.reserve_day}"
                                pattern="yyyy-MM-dd'T'HH:mm" var="reserveDate" type="date" />
                            <a
                                href="<c:url value='?action=${actRep}&command=${commShow}&id=${receive.id}' />">
                                <fmt:formatDate value='${reserveDate}' pattern='HH:mm' />
                            </a>
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.thuDate}" />
                        <c:forEach var="receive" items="${week.thuDateReceive}">
                            <br>
                            <fmt:parseDate value="${receive.reserve_day}"
                                pattern="yyyy-MM-dd'T'HH:mm" var="reserveDate" type="date" />
                            <a
                                href="<c:url value='?action=${actRep}&command=${commShow}&id=${receive.id}' />">
                                <fmt:formatDate value='${reserveDate}' pattern='HH:mm' />
                            </a>
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.friDate}" />
                        <c:forEach var="receive" items="${week.friDateReceive}">
                            <br>
                            <fmt:parseDate value="${receive.reserve_day}"
                                pattern="yyyy-MM-dd'T'HH:mm" var="reserveDate" type="date" />
                            <a
                                href="<c:url value='?action=${actRep}&command=${commShow}&id=${receive.id}' />">
                                <fmt:formatDate value='${reserveDate}' pattern='HH:mm' />
                            </a>
                        </c:forEach>
                    </td>
                    <td>
                        <c:out value="${week.satDate}" />
                        <c:forEach var="receive" items="${week.satDateReceive}">
                            <br>
                            <fmt:parseDate value="${receive.reserve_day}"
                                pattern="yyyy-MM-dd'T'HH:mm" var="reserveDate" type="date" />
                            <a
                                href="<c:url value='?action=${actRep}&command=${commShow}&id=${receive.id}' />">
                                <fmt:formatDate value='${reserveDate}' pattern='HH:mm' />
                            </a>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
        </html>
    </c:param>
</c:import>