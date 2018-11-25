<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="../globaldata.jsp" %>

<layout:layoutContent name="styleBlock">
    <link rel="stylesheet" href="<%=staticLibraryPath%>/pagination/simplePagination.css" type="text/css"/>
    <link rel="stylesheet" href="<%=staticFECssPath%>/index.css" type="text/css"/>
</layout:layoutContent>

<layout:layoutContent name="pageScriptBlock">
    <script type="text/javascript" src="<%=staticLibraryPath%>/pagination/jquery.simplePagination.js"></script>
    <script type="text/javascript" src="<%=staticFEScriptPath%>/cart.js"></script>
    <script type="text/javascript" src="<%=staticFEScriptPath%>/index.js"></script>
</layout:layoutContent>

<layout:layoutContent name="mainContent">
    <div class="content-section">
        <div class="operation-bar"></div>
        <div id="product-list" class="product-list">

        </div>
        <div id="product-pagination" class="product-pagination"></div>
    </div>
</layout:layoutContent>

<jsp:include page="common/feLayout.jsp"/>