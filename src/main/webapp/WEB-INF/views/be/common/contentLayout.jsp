<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="../../globaldata.jsp" %>

<!----该布局页的主要功能是在beLayout布局页的基础上，引进了以下五块占位区
------(1)libraryStyleBlock
------(2)pageStyleBlock
------(3)mainContentBlock
------(4)libraryScriptBlock
------(5)pageScriptBlock
------这5部分内容是根据先后顺序而来的，在不同引用该布局的页面都可以通过这5块内容按照顺序注入样式和脚本文件
------这种布局方式在多页面应用，且不使用AJAX请求页面进行局部更新时有较大的利用空间。
------>

<layout:layoutContent name="title">
    WiWi商城管理系统
</layout:layoutContent>

<layout:layoutContent name="styleBlock">
    <layout:layoutBlock name="libraryStyleBlock"></layout:layoutBlock>
    <link rel="stylesheet" href="<%=staticBECssPath%>/pes-bootstrap-ui.css" type="text/css"/>
    <link rel="stylesheet" href="<%=staticBECssPath%>/common.css" type="text/css"/>
    <layout:layoutBlock name="pageStyleBlock"></layout:layoutBlock>
</layout:layoutContent>

<layout:layoutContent name="bodyBlock">
   <div class="page-content">
       <layout:layoutBlock name="mainContentBlock"></layout:layoutBlock>
   </div>
</layout:layoutContent>

<layout:layoutContent name="footerScriptBlock">
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootstrap-notify/bootstrap-notify.min.js"></script>
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootbox/bootbox.min.js"></script>
    <layout:layoutBlock name="libraryScriptBlock"></layout:layoutBlock>
    <script type="text/javascript" src="<%=staticCommonScriptPath%>/htmlHelper.js"></script>
    <script type="text/javascript" src="<%=staticBEScriptPath%>/app.js"></script>
    <script type="text/javascript" src="<%=staticBEScriptPath%>/header.js"></script>
    <script type="text/javascript" src="<%=staticBEScriptPath%>/menu.js"></script>
    <layout:layoutBlock name="pageScriptBlock"></layout:layoutBlock>
</layout:layoutContent>

<jsp:include page="../../layout.jsp"/>
