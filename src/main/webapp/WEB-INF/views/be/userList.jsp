<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../globaldata.jsp" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>

<!----以下是AJAX布局内容---->

<layout:layoutContent name="contentBlock">
    <div id="toolbar">
        <button type="button" class="btn btn-primary btn-operation">新增功能</button>
    </div>
    <table id="userTable">
    </table>
</layout:layoutContent>

<layout:layoutContent name="scriptBlock">
    <script src="<%=staticBEScriptPath%>/user-list.js" type="text/javascript"></script>
</layout:layoutContent>

<jsp:include page="common/ajaxContentLayout.jsp"/>
