<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="../../globaldata.jsp" %>

<layout:layoutContent name="title">
    WiWi商城管理系统
</layout:layoutContent>

<layout:layoutContent name="styleBlock">
    <layout:layoutBlock name="libraryStyleBlock"></layout:layoutBlock>
    <link rel="stylesheet" href="<%=staticBECssPath%>/pes-bootstrap-ui.css" type="text/css"/>
    <link rel="stylesheet" href="<%=staticBECssPath%>/layout.css" type="text/css"/>
    <link rel="stylesheet" href="<%=staticBECssPath%>/common.css" type="text/css"/>
    <link rel="stylesheet" href="<%=staticLibraryPath%>/bootstrap-tagsinput/bootstrap-tagsinput.css" type="text/css">
    <layout:layoutBlock name="pageStyleBlock"></layout:layoutBlock>
</layout:layoutContent>

<layout:layoutContent name="bodyBlock">
    <div class="app">
        <section class="app-header">
            <jsp:include page="header.jsp"/>
        </section>
        <section class="app-content">
            <div class="drawer-panel" id="drawer-panel">
            </div>
            <div class="right-panel">
                <div class="bread-crumb" id="bread-crumb">
                </div>
                <div class="main-content-wrapper">
                    <div class="main-content" id="main-content">
                        <layout:layoutBlock name="mainContentBlock"></layout:layoutBlock>
                    </div>
                </div>
            </div>
        </section>
        <section class="app-footer"></section>
    </div>
</layout:layoutContent>

<layout:layoutContent name="footerScriptBlock">
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootstrap-notify/bootstrap-notify.min.js"></script>
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootbox/bootbox.min.js"></script>
    <layout:layoutBlock name="libraryScriptBlock"></layout:layoutBlock>
    <script type="text/javascript" src="<%=staticCommonScriptPath%>/contentViewManager.js"></script>
    <script type="text/javascript" src="<%=staticCommonScriptPath%>/htmlHelper.js"></script>
    <script type="text/javascript" src="<%=staticCommonScriptPath%>/models.js"></script>
    <script type="text/javascript" src="<%=staticBEScriptPath%>/app.js"></script>
    <script type="text/javascript" src="<%=staticBEScriptPath%>/header.js"></script>
    <script type="text/javascript" src="<%=staticBEScriptPath%>/menu.js"></script>
    <layout:layoutBlock name="pageScriptBlock"></layout:layoutBlock>
</layout:layoutContent>

<jsp:include page="../../layout.jsp"/>
