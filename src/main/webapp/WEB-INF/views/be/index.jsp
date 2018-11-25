<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="../globaldata.jsp" %>

<layout:layoutContent name="libraryStyleBlock">
    <link rel="stylesheet" href="<%=staticLibraryPath%>/bootstrap-table/bootstrap-table.min.css" type="text/css"/>
</layout:layoutContent>

<layout:layoutContent name="libraryScriptBlock">
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootstrap-table/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
</layout:layoutContent>
<jsp:include page="common/beLayout.jsp"/>
