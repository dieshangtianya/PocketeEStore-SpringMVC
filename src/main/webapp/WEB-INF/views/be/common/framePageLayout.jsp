<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="../../globaldata.jsp" %>

<!----该布局页的主要功能是用于首页使用IFrame进行布局的分页，由于使用IFrame，在前端相当于生成了独立的嵌套页面。
------因此该布局会使用contentLayout布局来组织其具体的布局。在contentLayout布局之上，主要的关注点就是页面使用的
------第三方库的内容，以及和业务相关的样式和脚本，而在contentLayout里有业务具体的Block定义，这里则只关注一些特定的内容。
------该布局只是定义了两个占位区：
------(1)libraryStyleBlock
------(2)libraryScriptBlock
------>

<layout:layoutContent name="libraryStyleBlock">
    <link rel="stylesheet" href="<%=staticLibraryPath%>/bootstrap-table/bootstrap-table.min.css" type="text/css"/>
    <layout:layoutBlock name="libraryStyleBlock"></layout:layoutBlock>
</layout:layoutContent>

<layout:layoutContent name="libraryScriptBlock">
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootstrap-table/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="<%=staticLibraryPath%>/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
    <layout:layoutBlock name="libraryScriptBlock"></layout:layoutBlock>
</layout:layoutContent>


<jsp:include page="contentLayout.jsp"/>
