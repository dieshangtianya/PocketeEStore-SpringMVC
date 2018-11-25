<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../globaldata.jsp" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>

<!----以下是AJAX布局内容---->
<layout:layoutContent name="styleBlock">
</layout:layoutContent>

<layout:layoutContent name="contentBlock">
    <table id="orderTable" style="width:100%">
    </table>
</layout:layoutContent>

<layout:layoutContent name="scriptBlock">
    <script type="text/javascript" src="<%=staticBEScriptPath%>/order-list.js"></script>
</layout:layoutContent>

<jsp:include page="common/ajaxContentLayout.jsp"/>


