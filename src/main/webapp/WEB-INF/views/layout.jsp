<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="globaldata.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <layout:layoutBlock name="title">WiWi购物商城</layout:layoutBlock>
    </title>
    <link rel="shortcut icon" href="/favicon.ico">
    <link rel="stylesheet" href="<%=staticCommonCssPath%>/iconfont.css" type="text/css"/>
    <link rel="stylesheet" href="<%=staticLibraryPath%>/bootstrap/css/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="<%=staticLibraryPath%>/animate/animate.min.css" type="text/css"/>
    <link rel="stylesheet" href="<%=staticCommonCssPath%>/common.css" type="text/css"/>
    <layout:layoutBlock name="styleBlock"></layout:layoutBlock>
</head>
<body>
<layout:layoutBlock name="bodyBlock"></layout:layoutBlock>
<script type="text/javascript" src="<%=staticLibraryPath%>/underscore/underscore.min.js"></script>
<script type="text/javascript" src="<%=staticLibraryPath%>/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=staticLibraryPath%>/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=staticCommonScriptPath%>/utils.js"></script>
<script type="text/javascript" src="<%=staticCommonScriptPath%>/appHelper.js"></script>
<layout:layoutBlock name="footerScriptBlock"></layout:layoutBlock>
</body>
</html>
