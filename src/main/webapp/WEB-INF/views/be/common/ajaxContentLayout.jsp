<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" uri="/WEB-INF/layoutTag.tld" %>
<%@include file="../../globaldata.jsp" %>

<!----该布局页的主要功能是用于生成内容区HTML. 后端框架使用AJAX进行局部更新时，所请求的页面应该使用该类布局。
------该布局只是实现三个占位区：
------(1)styleBlock
------(2)contentBlock
------(3)scriptBlock
------该类布局不引用其他主页框架布局，它只是用于组织内容区的页面形式
------>

<layout:layoutBlock name="styleBlock">
</layout:layoutBlock>

<layout:layoutBlock name="contentBlock">
</layout:layoutBlock>

<layout:layoutBlock name="scriptBlock">
</layout:layoutBlock>