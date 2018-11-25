<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="../../globaldata.jsp"%>

<layout:layoutContent name="styleBlock">
    <layout:layoutBlock name="librayStyleBlock"></layout:layoutBlock>
    <link rel="stylesheet" href="<%=staticFECssPath%>/fe-layout.css" type="text/css"/>
    <link rel="stylesheet" href="<%=staticFECssPath%>/category-panel.css" type="text/css"/>
    <layout:layoutBlock name="styleBlock"></layout:layoutBlock>
</layout:layoutContent>

<layout:layoutContent name="bodyBlock">
    <div class="app">
        <%@ include file="header.jsp" %>
        <section class="app-content">
            <div class="left-panel">
                <jsp:include page="category_panel.jsp"/>
            </div>
            <div class="right-panel">
                <layout:layoutBlock name="mainContent"/>
            </div>
        </section>
        <jsp:include page="footer.jsp"/>
    </div>

</layout:layoutContent>

<layout:layoutContent name="footerScriptBlock">
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootstrap-notify/bootstrap-notify.min.js"></script>
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootbox/bootbox.min.js"></script>
    <layout:layoutBlock name="libraryScriptBlock"></layout:layoutBlock>
    <script type="text/javascript" src="<%=staticCommonScriptPath%>/htmlHelper.js"></script>
    <script type="text/javascript" src="<%=staticCommonScriptPath%>/models.js"></script>
    <script type="text/javascript" src="<%=staticFEScriptPath%>/app.js"></script>
    <script type="text/javascript" src="<%=staticFEScriptPath%>/layout.js"></script>
    <layout:layoutBlock name="pageScriptBlock"></layout:layoutBlock>
</layout:layoutContent>

<jsp:include page="../../layout.jsp"/>
