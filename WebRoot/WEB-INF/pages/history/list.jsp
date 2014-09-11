<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Admin Console</title>
<meta name="menu" content="history" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/styles/tablesorter/style.css'/>" />
<script type="text/javascript"
	src="<c:url value='/scripts/jquery.tablesorter.js'/>"></script>
</head>

<body>

	<h1>推送记录</h1>

	<table id="historyList" class="tablesorter" cellspacing="1">
		<thead>
			<tr>
				<th>用户名</th>
				<th>发送时间</th>
				<th>消息ID</th>
				<th>标题</th>
				<th>内容</th>
				<th>uri</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="history" items="${historyList}">
				<tr>
					<td><c:out value="${history.username}" /></td>
					<td align="center"><fmt:formatDate
							pattern="yyyy-MM-dd HH:mm:ss" value="${history.createTime}" /></td>
					<td align="center"><c:out value="${history.messageId}" /></td>
					<td><c:out value="${history.title}" /></td>
					<td><c:choose>
							<c:when test="${history.message.length() > 50}">
						${history.message.substring(0,50)}...
							</c:when>
							<c:otherwise>
						${history.message}
							</c:otherwise>
						</c:choose></td>
					<td><c:out value="${history.uri}" /></td>
					<td><c:out value="${history.status}" /></td>
					
				</tr>
			</c:forEach>
			
		</tbody>
	${historyPageList.htmlPage}	
	</table>

	<script type="text/javascript">
//<![CDATA[
$(function() {
	$('#tableList').tablesorter();
	//$('#tableList').tablesorter( {sortList: [[0,0], [1,0]]} );
	//$('table tr:nth-child(odd)').addClass('odd');
	$('table tr:nth-child(even)').addClass('even');	 
});
//]]>
</script>

</body>
</html>
